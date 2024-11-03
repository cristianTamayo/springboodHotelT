package com.HotelT.crudresevas.springboodHotelT.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name="registroLimpieza")
public class registroLimpieza {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date fecha;
	String descripcion;
	String novedades;
	int idabitacion;
	int cedulaEmpleado;
	
}
