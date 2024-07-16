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
	public String registrarCliente(@Valid @ModelAttribute ClienteDto clienteDto, BindingResult resultado) {
		if (resultado.hasErrors()) {
			return "clientes/registrarCliente";
		}
		// Registro en base de datos del nuevo registro.
		Cliente  cliente= new Cliente();
		cliente.setNombre(clienteDto.getNombre());
		cliente.setCedula(clienteDto.getCedula());
		cliente.setCorreo(clienteDto.getCorreo());
		cliente.setPassword(clienteDto.getContrasena());
		repo.save(cliente);

		
		return "redirect:/habitaciones";
	}
	
	@PostMapping({ "", "/" })
	public String inicioSesion(Model model,@ModelAttribute ClienteDto clienteDto, RedirectAttributes redirectAttributes){
		Cliente cliente = repo.findByCorreoAndPassword(clienteDto.getCorreo(), clienteDto.getContrasena());
		
		if(cliente!=null) {
			clienteDto.setNombre(cliente.getNombre());
			clienteDto.setCedula(cliente.getCedula());
			clienteDto.setCorreo(cliente.getCorreo());
			clienteDto.setContrasena(cliente.getPassword());
			model.addAttribute("cedula",clienteDto.getCedula());
			redirectAttributes.addAttribute("cedula",clienteDto.getCedula());
			
			return "redirect:/reservas";
		}
		
		return "redirect:/clientes/";
		
	}
}
