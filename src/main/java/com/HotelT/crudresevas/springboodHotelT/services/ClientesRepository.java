package com.HotelT.crudresevas.springboodHotelT.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelT.crudresevas.springboodHotelT.models.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Integer>{
	Cliente findByCorreoAndPassword(String correo, String password);
	Cliente findByCorreo(String correo);
	Cliente findByCedula(String cedula);
	List<Cliente> findAllByRol(String rol);
	
}
