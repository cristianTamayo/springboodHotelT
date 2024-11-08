package com.HotelT.crudresevas.springboodHotelT.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HotelT.crudresevas.springboodHotelT.models.Cliente;
import com.HotelT.crudresevas.springboodHotelT.models.ClienteDto;
import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.models.RegistroLimpieza;
import com.HotelT.crudresevas.springboodHotelT.models.Reserva;
import com.HotelT.crudresevas.springboodHotelT.services.ClientesRepository;
import com.HotelT.crudresevas.springboodHotelT.services.HabitacionesRepository;
import com.HotelT.crudresevas.springboodHotelT.services.LimpiezaRepository;
import com.HotelT.crudresevas.springboodHotelT.services.ReservasRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("administracion")
public class AdminController {
	@Autowired
	private HabitacionesRepository repoHabitaciones;
	@Autowired
	private ClientesRepository repoClientes;
	@Autowired
	private ReservasRepository repoReservas;
	@Autowired
	private LimpiezaRepository repoLimpieza;
	
	@GetMapping({"","/"})
	public String mostrarListaHabitaciones(Model model, HttpSession session) {
		
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		//Cliente adminLogeado = repoClientes.findByCedula(admin.getCedula());
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		List<Cliente> empleados=repoClientes.findAllByRol("Trabajador");
		
		model.addAttribute("empleados",empleados);
		
		return "administracion/index";
	}
	
	@GetMapping({ "/cerrarSesion" })
	public String cerrarSesion(Model model, HttpSession session) {
		session.removeAttribute("adminLogeado");
		return "redirect:/clientes";
	}
	
	@GetMapping({"/verHabitaciones"})
	public String vistaHabitaciones(Model model, HttpSession session) {
		List<Habitacion> habitaciones=repoHabitaciones.findAll();
		model.addAttribute("habitaciones",habitaciones);
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		return "administracion/verHabitaciones";
	}
	
	@GetMapping({"/crearHabitacion"})
	public String vistaCrearHabitacion(Model model, HttpSession session) {
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		
		return "administracion/crearHabitacion";
	}
	
	@PostMapping({"/crearHabitacion"})
	public String crearHabitacion(Model model,HttpSession session,RedirectAttributes redirectAttributes,
			@RequestParam("file")  MultipartFile imagen,@RequestParam int numeroCamas,@RequestParam int numeroBanos,@RequestParam String disponibilidad) throws IOException {
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		//Almacenamiento de la imagen de la nueva habitacion
		Path directorioImagenes = Paths.get("public/images");
		String rutaAbsoluta=directorioImagenes.toFile().getAbsolutePath();
		byte[] bytesImg= imagen.getBytes();
		Path rutaCompleta=Paths.get(rutaAbsoluta+"//"+imagen.getOriginalFilename());
		Files.write(rutaCompleta, bytesImg);
		
		//Creacion de la nueva habitacion
		Habitacion nuevaHabitacion= new Habitacion();
		nuevaHabitacion.setDisponibilidad(disponibilidad);
		nuevaHabitacion.setNombreArchivoImagen(imagen.getOriginalFilename());
		nuevaHabitacion.setNumeroBanos(numeroBanos);
		nuevaHabitacion.setNumeroCamas(numeroCamas);
		
		//Almacenamiento de la nueva habitacion en la base de datos
		repoHabitaciones.save(nuevaHabitacion);
		
		redirectAttributes.addFlashAttribute("error","habitacion creada correctamente correctamente");
		return "redirect:/administracion/verHabitaciones";
	}
	
	@GetMapping({"/editarHabitacion"})
	public String vistaEditarHabitacion(Model model, HttpSession session,@RequestParam int idHabitacion) {
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		//Trayendo la info de la habitacion a editar
		Habitacion habitacion = repoHabitaciones.findById(idHabitacion);
		model.addAttribute("habitacion",habitacion);
		return "administracion/editarHabitacion";
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
			return "redirect:/administracion/verHabitaciones";
		}
	
	
	@GetMapping({"/verReservas"})
	public String vistaReservas(Model model, HttpSession session) {
		List<Reserva> reservas=repoReservas.findAll();
		model.addAttribute("reservas",reservas);
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		model.addAttribute("reservas",reservas);
		return "administracion/verReservas";
	}
	
	@GetMapping({"/crearEmpleado"})
	public String vistaCrearCliente(Model model, HttpSession session) {
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		return "administracion/crearEmpleado";
	}
	
	@PostMapping({"/crearEmpleado"})
	public String crearEmpleado(@RequestParam String cedulaEmpleado, @RequestParam String nombreEmpleado,@RequestParam String emailEmpleado,@RequestParam String passwordEmpleado,
			RedirectAttributes redirectAttributes) {
		
		Cliente empleado= new Cliente();
		empleado.setCedula(cedulaEmpleado);
		empleado.setCorreo(emailEmpleado);
		empleado.setNombre(nombreEmpleado);
		empleado.setPassword(passwordEmpleado);
		empleado.setRol("Trabajador");
		repoClientes.save(empleado);
		
		redirectAttributes.addFlashAttribute("error", "Empleado "+ empleado.getNombre() +" creado exitosamente");
		return "redirect:/administracion";
	}
	
	@GetMapping({"/editarEmpleado"})
	public String vistaEditarEmpleado(Model model, HttpSession session, @RequestParam String cedula) {
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		Cliente empleado= repoClientes.findByCedula(cedula);
		model.addAttribute("empleado",empleado);
		
		return "administracion/editarEmpleado";
	}
	
	@PostMapping({"/editarEmpleado"})
	public String editarEmpleado(@RequestParam String cedulaEmpleado, @RequestParam String nombre,@RequestParam String email,@RequestParam String password,RedirectAttributes redirectAttributes) {
		System.err.println(cedulaEmpleado);
		Cliente empleado= repoClientes.findByCorreo(email);
		empleado.setCedula(cedulaEmpleado);
		empleado.setCorreo(email);
		empleado.setNombre(nombre);
		empleado.setPassword(password);
		repoClientes.save(empleado);
		
		redirectAttributes.addFlashAttribute("error", "Empleado "+ empleado.getNombre() +" editado");
		return "redirect:/administracion";
	}
	
	@GetMapping({"/verRegistrosLimpieza"})
	public String vistaRegistrosLimpieza(Model model, HttpSession session,@RequestParam int idHabitacion) {
		Cliente admin = (Cliente)session.getAttribute("adminLogeado");
		ClienteDto adminDto= new ClienteDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setCedula(admin.getCedula());
		adminDto.setCorreo(admin.getCorreo());
		adminDto.setContrasena(admin.getPassword());
		model.addAttribute("adminDto", adminDto);
		
		//Trayendo la info de la habitacion a editar
		Habitacion habitacion = repoHabitaciones.findById(idHabitacion);
		model.addAttribute("habitacion",idHabitacion);
		List<RegistroLimpieza> registrosLimpieza = repoLimpieza.findAllByIdHabitacion(idHabitacion);
		model.addAttribute("registrosLimpieza",registrosLimpieza);
		return "administracion/registrosLimpieza";
	}
}
