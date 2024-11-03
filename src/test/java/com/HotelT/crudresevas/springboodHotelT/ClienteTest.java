package com.HotelT.crudresevas.springboodHotelT;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.HotelT.crudresevas.springboodHotelT.models.Cliente;
import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.models.Reserva;

class ClienteTest {
	
	Cliente cliente;
	Habitacion habitacion;
	Reserva reserva1;
	Reserva reserva2;
	SimpleDateFormat formato;
	@BeforeEach
	public void iniciar() {
		try {
			cliente= new Cliente("prueba","1111","prueba@","1234");
			habitacion=new Habitacion();
			habitacion.setId(0);
			habitacion.setDisponibilidad("disponible");
			habitacion.setNombreArchivoImagen("prueba.jpg");
			habitacion.setNumeroBanos(0);
			habitacion.setNumeroCamas(0);
			//Creacion de la primera reserva
			reserva1= new Reserva();
			reserva1.setCliente(cliente);
			reserva1.setHabitacion(habitacion);
			reserva1.setId(0);
			formato = new SimpleDateFormat("dd/MM/yyyy");
			Date inicio = formato.parse("01/09/2000");
			Date fin = formato.parse("02/09/2000");
			reserva1.setFechaInicio(inicio);
			reserva1.setFechaFin(fin);
			//Creacio de la segunda reserva
			reserva2= new Reserva();
			reserva2.setCliente(cliente);
			reserva2.setHabitacion(habitacion);
			reserva2.setId(0);
			Date inicio2 = formato.parse("02/09/2000");
			Date fin2 = formato.parse("04/09/2000");
			reserva2.setFechaInicio(inicio2);
			reserva2.setFechaFin(fin2);
			//enlace de las reservas al cliente
			cliente.addReserva(reserva1);
			System.out.println(reserva2.valorReserva());
			cliente.addReserva(reserva2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Test
	public void totalReservasTest() {
		//prueba valor de las dos reservas
		assertEquals(2500000, cliente.totalReservas());
	}
}
