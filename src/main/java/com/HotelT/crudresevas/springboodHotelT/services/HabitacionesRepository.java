package com.HotelT.crudresevas.springboodHotelT.services;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;

//Para leer las habitaciones desde la base de datos
public interface HabitacionesRepository extends JpaRepository<Habitacion, Integer> {
	List<Habitacion> findAllByDisponibilidad(String disponibilidad);
	Habitacion findById(int id);
	
}
