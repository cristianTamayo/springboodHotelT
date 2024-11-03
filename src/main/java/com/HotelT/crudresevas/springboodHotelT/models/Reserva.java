package com.HotelT.crudresevas.springboodHotelT.models;



import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Reservas")
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date fechaInicio;
	private Date fechaFin;

	@ManyToOne
	@JoinColumn(name = "cedula_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_habitacion", nullable = false)
	private Habitacion habitacion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
	public long diasTotales() {
		LocalDate inicio=this.fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fin=this.fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Duration total= Duration.between(inicio.atStartOfDay(), fin.atStartOfDay());
		if(total.toDays()<0) {
			return 0;
		}
		return total.toDays()+1;
	}
	
	public long valorReserva() {
		Long dias= diasTotales();
		return dias*500000;
		
	}

	
}
