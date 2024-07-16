package com.HotelT.crudresevas.springboodHotelT.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.senaadso.crudinventario.springbootsenaadso.models.Producto;
import com.senaadso.crudinventario.springbootsenaadso.models.ProductoDto;

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
	public String mostrarListaHabitaciones(Model model, @RequestParam String cedula) {
		List<Habitacion> habitaciones=repoHabitaciones.findAll();
		Cliente cliente = repoClientes.findByCedula(cedula);
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
	public String showEditPage(Model model, @RequestParam int id,@RequestParam String cedula, RedirectAttributes redirectAttributes) {
		try {
			Cliente cliente = repoClientes.findByCedula(cedula);
			ClienteDto clienteDto= new ClienteDto();
			clienteDto.setNombre(cliente.getNombre());
			clienteDto.setCedula(cliente.getCedula());
			clienteDto.setCorreo(cliente.getCorreo());
			clienteDto.setContrasena(cliente.getPassword());
			model.addAttribute("clienteDto", clienteDto);
			//System.out.println(cliente.getNombre());
			Habitacion habitacion = repoHabitaciones.findById(id).get();
			model.addAttribute("habitacion", habitacion);


		} catch (Exception ex) {
			System.out.println("Excepión al Editar: " + ex.getMessage());
			return "redirect:/reservas";
		}

		return "/reservas/crear";
	}
	
	@PostMapping("/crear")
	public String crearReserva(Model model, @RequestParam String fechaInicio,@RequestParam String fechaFin, RedirectAttributes redirectAttributes,
			@ModelAttribute ClienteDto clienteDto,@ModelAttribute Habitacion habitacion) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date inicio= formato.parse(fechaInicio);
		Date fin= formato.parse(fechaFin);
		Cliente cliente= new Cliente();
		cliente.setNombre(clienteDto.getNombre());
		cliente.setCedula(clienteDto.getCedula());
		cliente.setCorreo(clienteDto.getCorreo());
		cliente.setPassword(clienteDto.getContrasena());
		Reserva reserva= new Reserva();
		reserva.setCliente(cliente);
		reserva.setFechaFin(fin);
		reserva.setFechaInicio(inicio);
		reserva.setHabitacion(habitacion);
		List<Reserva> listaReservas= new ArrayList<Reserva>();
		List<Reserva> listaReservasCliente= cliente.getReservas();
		if(listaReservasCliente==null) {
			listaReservas.add(reserva);
			cliente.setReservas(listaReservas);
		}else {
			listaReservasCliente.add(reserva);
			cliente.setReservas(listaReservasCliente);
		}
		
		/*List<Reserva> listaReservasHabitacion=habitacion.getReservas();
		if(listaReservasHabitacion==null) {
			listaReservas.add(reserva);
			habitacion.setReservas(listaReservas);
		}else {
			listaReservasHabitacion.add(reserva);
			habitacion.setReservas(listaReservasHabitacion);
		}*/
		
		/*
		 * repoHabitaciones.save(habitacion); repoClientes.save(cliente);
		 */
		repoReservas.save(reserva);
		
		
		
		
		redirectAttributes.addAttribute("cedula",clienteDto.getCedula());
		return "redirect:/reservas";
		
	}
	
}
