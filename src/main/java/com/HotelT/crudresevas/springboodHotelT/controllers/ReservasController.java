package com.HotelT.crudresevas.springboodHotelT.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HotelT.crudresevas.springboodHotelT.models.Cliente;
import com.HotelT.crudresevas.springboodHotelT.models.ClienteDto;
import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.models.Reserva;
import com.HotelT.crudresevas.springboodHotelT.services.ClientesRepository;
import com.HotelT.crudresevas.springboodHotelT.services.HabitacionesRepository;
import com.HotelT.crudresevas.springboodHotelT.services.ReservasRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("reservas")
public class ReservasController {
	@Autowired
	private HabitacionesRepository repoHabitaciones;
	@Autowired
	private ClientesRepository repoClientes;
	@Autowired
	private ReservasRepository repoReservas;
	

	@GetMapping({"","/"})
	public String mostrarListaHabitaciones(Model model, HttpSession session) {
		List<Habitacion> habitaciones=repoHabitaciones.findAllByDisponibilidad("Disponible");
		Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
		Cliente cliente = repoClientes.findByCedula(clienteLogeado.getCedula());
		ClienteDto clienteDto= new ClienteDto();
		clienteDto.setNombre(cliente.getNombre());
		clienteDto.setCedula(cliente.getCedula());
		clienteDto.setCorreo(cliente.getCorreo());
		clienteDto.setContrasena(cliente.getPassword());
		model.addAttribute("clienteDto", clienteDto);
		model.addAttribute("habitaciones",habitaciones);
		List<Reserva> reservas= cliente.getReservas();
		model.addAttribute("reservas",reservas);
		
		return "reservas/index";
	}
	
	@GetMapping("/crear")
	public String showEditPage(Model model, @RequestParam int id, RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
			System.out.println("cedula "+clienteLogeado.getCedula());
			Cliente cliente = repoClientes.findByCedula(clienteLogeado.getCedula());
			ClienteDto clienteDto= new ClienteDto();
			clienteDto.setNombre(cliente.getNombre());
			clienteDto.setCedula(cliente.getCedula());
			clienteDto.setCorreo(cliente.getCorreo());
			clienteDto.setContrasena(cliente.getPassword());
			model.addAttribute("clienteDto", clienteDto);
			//System.out.println(cliente.getNombre());
			Habitacion habitacion = repoHabitaciones.findById(id);
			model.addAttribute("habitacion", habitacion);


		} catch (Exception ex) {
			System.out.println("Excepión al Editar: " + ex.getMessage());
			return "redirect:/reservas";
		}

		return "/reservas/crear";
	}
	
	@PostMapping("/crear")
	public String crearReserva(Model model, @RequestParam String fechaInicio,@RequestParam int diasTotales, RedirectAttributes redirectAttributes,
			@ModelAttribute Habitacion habitacion,HttpSession session) throws ParseException {
		Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date inicio= formato.parse(fechaInicio);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inicio);
		calendar.add(Calendar.DAY_OF_YEAR, diasTotales);
		
		Date fin= calendar.getTime();
		Cliente cliente= new Cliente();
		cliente.setNombre(clienteLogeado.getNombre());
		cliente.setCedula(clienteLogeado.getCedula());
		cliente.setCorreo(clienteLogeado.getCorreo());
		cliente.setPassword(clienteLogeado.getPassword());
		Reserva reserva= new Reserva();
		reserva.setCliente(cliente);
		reserva.setFechaFin(fin);
		reserva.setFechaInicio(inicio);
		reserva.setHabitacion(habitacion);
		
		//Manejo de solicitud de reservas en tiempos ya reservados
		List<Reserva> reservas = repoReservas.findAllByHabitacion(habitacion);
		
		for(Reserva r :reservas) {
			
			if( (reserva.getFechaInicio().getTime()<=r.getFechaFin().getTime()) && (r.getFechaInicio().getTime()<=reserva.getFechaFin().getTime()) ){
				System.err.println(r.getId());
				System.err.println("se solapa");
				redirectAttributes.addFlashAttribute("error", "No se puede crear una reserva de esa habitacion en esas fechas");
				return "redirect:/reservas";
			}
			
			
		    }
		
		
		
		List<Reserva> listaReservas= new ArrayList<Reserva>();
		List<Reserva> listaReservasCliente= cliente.getReservas();
		if(listaReservasCliente==null) {
			listaReservas.add(reserva);
			cliente.setReservas(listaReservas);
		}else {
			listaReservasCliente.add(reserva);
			cliente.setReservas(listaReservasCliente);
		}
		
		repoReservas.save(reserva);
		
		
		return "redirect:/reservas";
		
	}
	
	@GetMapping("/eliminar")
	public String vistaEliminarReserva(Model model, @RequestParam int idHabitacion, RedirectAttributes redirectAttributes,HttpSession session, @RequestParam int idReserva) {
		try {
			Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
			//System.out.println("cedula "+clienteLogeado.getCedula());
			Cliente cliente = repoClientes.findByCedula(clienteLogeado.getCedula());
			ClienteDto clienteDto= new ClienteDto();
			clienteDto.setNombre(cliente.getNombre());
			clienteDto.setCedula(cliente.getCedula());
			clienteDto.setCorreo(cliente.getCorreo());
			clienteDto.setContrasena(cliente.getPassword());
			model.addAttribute("clienteDto", clienteDto);
			//System.out.println(cliente.getNombre());
			
			Habitacion habitacion = repoHabitaciones.findById(idHabitacion);
			model.addAttribute("habitacion", habitacion);
			Reserva reserva = repoReservas.findById(idReserva);
			
			//Control de reservas que no pueden ser canceladas
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaInicio = Calendar.getInstance();
			Date actual= fechaActual.getTime();
			fechaInicio.setTime(reserva.getFechaInicio());
			fechaInicio.add(Calendar.DAY_OF_YEAR, -3);
			Date fechaValida= fechaInicio.getTime();
			if(actual.after(fechaValida)) {
				redirectAttributes.addFlashAttribute("error", "No se puede cancelar la reserva, ya que debe hacerce con 72 horas de anticipacion");
				return "redirect:/reservas";
			}
			
			model.addAttribute("reserva", reserva);

		} catch (Exception ex) {
			System.out.println("Excepión al Editar: " + ex.getMessage());
			return "redirect:/reservas";
		}

		return "/reservas/eliminar";
	}
	
	@PostMapping("/eliminar")
	public String eliminarReserva(Model model, HttpSession session, RedirectAttributes redirectAttributes,@RequestParam int idReserva ) {
		Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
		repoReservas.deleteById(idReserva);
		redirectAttributes.addFlashAttribute("error", "Reserva eliminada");
		return "redirect:/reservas";
		
	}
	
	@GetMapping("/editar")
	public String vistaEditarReserva(Model model, @RequestParam int idHabitacion, RedirectAttributes redirectAttributes,HttpSession session, @RequestParam int idReserva) {
		try {
			Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
			Cliente cliente = repoClientes.findByCedula(clienteLogeado.getCedula());
			ClienteDto clienteDto= new ClienteDto();
			clienteDto.setNombre(cliente.getNombre());
			clienteDto.setCedula(cliente.getCedula());
			clienteDto.setCorreo(cliente.getCorreo());
			clienteDto.setContrasena(cliente.getPassword());
			model.addAttribute("clienteDto", clienteDto);
			
			Habitacion habitacion = repoHabitaciones.findById(idHabitacion);
			model.addAttribute("habitacion", habitacion);
			Reserva reserva = repoReservas.findById(idReserva);
			
			//Control de reservas que no pueden ser canceladas
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaInicio = Calendar.getInstance();
			Date actual= fechaActual.getTime();
			fechaInicio.setTime(reserva.getFechaInicio());
			fechaInicio.add(Calendar.DAY_OF_YEAR, -3);
			Date fechaValida= fechaInicio.getTime();
			
			if(actual.after(fechaValida)) {
				redirectAttributes.addFlashAttribute("error", "No se puede editar la reserva, ya que debe hacerce con 72 horas de anticipacion");
				return "redirect:/reservas";
			}
			
			
			model.addAttribute("reserva", reserva);

		} catch (Exception ex) {
			System.out.println("Excepión al Editar: " + ex.getMessage());
			return "redirect:/reservas";
		}

		return "/reservas/editar";
	}
	
	@PostMapping("/editar")
	public String editarReserva(Model model, HttpSession session, RedirectAttributes redirectAttributes,@RequestParam int idReserva, @RequestParam String fechaInicio,@RequestParam int diasTotales ) throws ParseException {
		Cliente clienteLogeado = (Cliente)session.getAttribute("usuarioLogeado");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date inicio= formato.parse(fechaInicio);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inicio);
		calendar.add(Calendar.DAY_OF_YEAR, diasTotales);
		Date fin= calendar.getTime();
		Reserva reserva= repoReservas.findById(idReserva);
		reserva.setFechaInicio(inicio);
		reserva.setFechaFin(fin);
		repoReservas.save(reserva);
		
		redirectAttributes.addFlashAttribute("error", "Reserva modificada");
		return "redirect:/reservas";
		
	}
	
	
	
}
