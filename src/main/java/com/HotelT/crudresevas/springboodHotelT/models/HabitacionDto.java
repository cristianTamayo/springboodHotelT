package com.HotelT.crudresevas.springboodHotelT.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class HabitacionDto {
	@NotEmpty(message = "El numero de camas es obligatorio")
	private int numeroCamas;
	
	@NotEmpty(message = "El numero de baños es obligatorio")
	private int numeroBanos;
	
	@NotEmpty(message = "La disponibilidad es obligatoria")
	private String disponibilidad;
	
	
	@NotEmpty(message = "La contraseña es obligatoria")
	private MultipartFile archivoImagen;


	public int getNumeroCamas() {
		return numeroCamas;
	}


	public void setNumeroCamas(int numeroCamas) {
		this.numeroCamas = numeroCamas;
	}


	public int getNumeroBanos() {
		return numeroBanos;
	}


	public void setNumeroBanos(int numeroBanos) {
		this.numeroBanos = numeroBanos;
	}


	public String getDisponibilidad() {
		return disponibilidad;
	}


	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}


	public MultipartFile getArchivoImagen() {
		return archivoImagen;
	}


	public void setArchivoImagen(MultipartFile archivoImagen) {
		this.archivoImagen = archivoImagen;
	}
	
	
}
