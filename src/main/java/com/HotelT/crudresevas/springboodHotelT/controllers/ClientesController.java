package com.HotelT.crudresevas.springboodHotelT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HotelT.crudresevas.springboodHotelT.models.*;
import com.HotelT.crudresevas.springboodHotelT.services.ClientesRepository;
import com.HotelT.crudresevas.springboodHotelT.services.HabitacionesRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("clientes")
public class ClientesController {
	@Autowired
	private ClientesRepository repo;
	
	@GetMapping({ "", "/" })
	public String clienteInicio(Model model) {
		
		/*
		 * List<Cliente> clientes=repo.findAll();
		 * 
		 * model.addAttribute("clientes",clientes);
		 */
		 
		ClienteDto clienteDto = new ClienteDto();
		model.addAttribute("clienteDto", clienteDto);
		return "clientes/index";
	}

	@GetMapping({ "/formularioRegistro" })
	public String showRegistrarCliente(Model model) {
		ClienteDto clienteDto = new ClienteDto();
		model.addAttribute("clienteDto", clienteDto);
		return "clientes/registrarCliente";
	}

	@PostMapping("/formularioRegistro")
	public String registrarCliente(@Valid @ModelAttribute ClienteDto clienteDto, BindingResult resultado,RedirectAttributes redirectAttributes) {
		if (resultado.hasErrors()) {
			return "clientes/registrarCliente";
		}
		
		//Buscar si cliente ya existe
		Cliente clienteIdentificado = repo.findByCorreo(clienteDto.getCorreo());
		if(clienteIdentificado==null) {
			// Registro en base de datos del nuevo registro.
			Cliente  cliente= new Cliente();
			cliente.setNombre(clienteDto.getNombre());
			cliente.setCedula(clienteDto.getCedula());
			cliente.setCorreo(clienteDto.getCorreo());
			cliente.setPassword(clienteDto.getContrasena());
			cliente.setRol("Cliente");
			repo.save(cliente);

			redirectAttributes.addFlashAttribute("error", "Usuario creado correctamente");
			return "redirect:/clientes";
			
		}
		System.err.println("el correo ya esta registrado");
		redirectAttributes.addFlashAttribute("error", "El correo ya esta registrado");
		return "redirect:/clientes";
		
	}
	
	
	@PostMapping({ "/inicioSesion" })
	public String inicioSesion(Cliente cliente, HttpSession session,Model model, RedirectAttributes redirectAttributes) {
		Cliente clienteIdentificado = repo.findByCorreoAndPassword(cliente.getCorreo(), cliente.getPassword());
		if(clienteIdentificado!=null) {
			if(clienteIdentificado.getRol().equals("Administrador")){
				session.setAttribute("adminLogeado", clienteIdentificado);	
				return "redirect:/administracion";
			}
			if(clienteIdentificado.getRol().equals("Trabajador")){
				session.setAttribute("trabajadorLogeado", clienteIdentificado);	
				return "redirect:/trabajadores/";
			}
			session.setAttribute("usuarioLogeado", clienteIdentificado);	
			return "redirect:/reservas";
		}else {
			System.out.println("usuario no encontrado");
			redirectAttributes.addFlashAttribute("error", "Correo o contraseña equivocado");
		}
		
		return "redirect:/clientes";
	}
	
	@GetMapping({ "/cerrarSesion" })
	public String cerrarSesion(Model model, HttpSession session) {
		session.removeAttribute("usuarioLogeado");
		return "redirect:/clientes";
	}
	
	
}
