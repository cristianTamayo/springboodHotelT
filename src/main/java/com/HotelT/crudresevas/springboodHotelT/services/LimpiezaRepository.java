package com.HotelT.crudresevas.springboodHotelT.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelT.crudresevas.springboodHotelT.models.RegistroLimpieza;

public interface LimpiezaRepository extends JpaRepository<RegistroLimpieza, Integer>{
	List<RegistroLimpieza> findAllByCedulaEmpleado(String cedulaEmpleado);
	List<RegistroLimpieza> findAllByIdHabitacion(int idHabitacion);
}
