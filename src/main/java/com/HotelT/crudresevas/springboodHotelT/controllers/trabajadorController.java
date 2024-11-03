package com.HotelT.crudresevas.springboodHotelT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HotelT.crudresevas.springboodHotelT.models.Cliente;
import com.HotelT.crudresevas.springboodHotelT.models.ClienteDto;
import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.services.ClientesRepository;
import com.HotelT.crudresevas.springboodHotelT.services.HabitacionesRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("trabajadores")
public class trabajadorController {

	@Autowired
	private HabitacionesRepository repoHabitaciones;
	@Autowired
	private ClientesRepository repoClientes;
	
	@GetMapping({"","/"})
	public String vistaTrabajador(Model model, HttpSession session) {
		Cliente trabajador = (Cliente)session.getAttribute("trabajadorLogeado");
		ClienteDto trabajadorDto= new ClienteDto();
		trabajadorDto.setNombre(trabajador.getNombre());
		trabajadorDto.setCedula(trabajador.getCedula());
		trabajadorDto.setCorreo(trabajador.getCorreo());
		trabajadorDto.setContrasena(trabajador.getPassword());
		model.addAttribute("trabajadorDto", trabajadorDto);
		List<Habitacion> habitaciones=repoHabitaciones.findAll();
		model.addAttribute("habitaciones",habitaciones);
		
		return "trabajadores/index";
	}
	
	@GetMapping({ "/cerrarSesion" })
	public String cerrarSesion(Model model, HttpSession session) {
		session.removeAttribute("trabajadorLogeado");
		return "redirect:/clientes";
	}
	
	@GetMapping({"/editarHabitacion"})
	public String vistaEditarHabitacion(Model model, HttpSession session,@RequestParam int idHabitacion) {
		Cliente trabajador = (Cliente)session.getAttribute("trabajadorLogeado");
		ClienteDto trabajadorDto= new ClienteDto();
		trabajadorDto.setNombre(trabajador.getNombre());
		trabajadorDto.setCedula(trabajador.getCedula());
		trabajadorDto.setCorreo(trabajador.getCorreo());
		trabajadorDto.setContrasena(trabajador.getPassword());
		model.addAttribute("trabajadorDto", trabajadorDto);
		
		//Trayendo la info de la habitacion a editar
		Habitacion habitacion = repoHabitaciones.findById(idHabitacion);
		model.addAttribute("habitacion",habitacion);
		return "trabajadores/editarHabitacion";
	}
	
	@PostMapping({"/editarHabitacion"})
	public String editarHabitacion(Model model, @RequestParam int numeroCamas, @RequestParam int numeroBanos, @RequestParam String disponibilidad,
			@RequestParam int idHabitacion, RedirectAttributes redirectAttributes) {
		Habitacion habitacion= repoHabitaciones.findById(idHabitacion);
		habitacion.setDisponibilidad(disponibilidad);
		habitacion.setNumeroBanos(numeroBanos);
		habitacion.setNumeroCamas(numeroCamas);
		repoHabitaciones.save(habitacion);
		
		redirectAttributes.addFlashAttribute("error","habitacion editada correctamente");
		return "redirect:/trabajadores";
	}
}
