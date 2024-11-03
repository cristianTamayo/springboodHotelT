package com.HotelT.crudresevas.springboodHotelT;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.HotelT.crudresevas.springboodHotelT.models.Cliente;
import com.HotelT.crudresevas.springboodHotelT.models.Habitacion;
import com.HotelT.crudresevas.springboodHotelT.models.Reserva;

class ReservaTest {
	Cliente cliente;
	Habitacion habitacion;
	Reserva reserva;
	SimpleDateFormat formato;
	@BeforeEach
	public void iniciar() {
		cliente= new Cliente("prueba","1111","prueba@","1234");
		habitacion=new Habitacion();
		habitacion.setId(0);
		habitacion.setDisponibilidad("disponible");
		habitacion.setNombreArchivoImagen("prueba.jpg");
		habitacion.setNumeroBanos(0);
		habitacion.setNumeroCamas(0);
		reserva= new Reserva();
		reserva.setCliente(cliente);
		reserva.setHabitacion(habitacion);
		reserva.setId(0);
		formato = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Test
	public void testDiasTotales() {
		try {
			
			//prueba ingreso de dias de reserva correcto
			Date inicio = formato.parse("01/09/2000");
			Date fin = formato.parse("02/09/2000");
			reserva.setFechaInicio(inicio);
			reserva.setFechaFin(fin);	
			assertEquals(2, reserva.diasTotales(),"El metodo no cuenta los dias de una reserva");
			
			//prueba ingreso de dias de reserva incorrectos
			inicio = formato.parse("02/09/2000");
			fin = formato.parse("01/09/2000");
			reserva.setFechaInicio(inicio);
			reserva.setFechaFin(fin);
			assertEquals(0, reserva.diasTotales(),"El metodo no comprueba si se ingresa fechas incorrectas");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	@Test
	public void testValorReserva() {
		try {
			
			Date inicio = formato.parse("01/09/2000");
			Date fin = formato.parse("01/09/2000");
			reserva.setFechaInicio(inicio);
			reserva.setFechaFin(fin);
			
			//prueba valor reserva por 1 dia = 500000
			assertEquals(500000, reserva.valorReserva());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
