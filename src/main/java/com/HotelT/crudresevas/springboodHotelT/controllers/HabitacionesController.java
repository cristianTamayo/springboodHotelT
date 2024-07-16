package com.HotelT.crudresevas.springboodHotelT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.services.HabitacionesRepository;


@Controller
@RequestMapping("habitaciones")
public class HabitacionesController {
	@Autowired
	private HabitacionesRepository repo;
	
	@GetMapping({"","/"})
	public String mostrarListaHabitaciones(Model model) {
		List<Habitacion> habitaciones=repo.findAll();
		
		model.addAttribute("habitaciones",habitaciones);
		
		return "habitaciones/index";
	}
}