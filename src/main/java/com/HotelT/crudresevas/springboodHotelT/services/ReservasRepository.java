package com.HotelT.crudresevas.springboodHotelT.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.models.Reserva;

public interface ReservasRepository extends JpaRepository<Reserva, Integer>{
	Reserva findById(int id);
	List<Reserva> findAllByHabitacion(Habitacion habitacion);
}
