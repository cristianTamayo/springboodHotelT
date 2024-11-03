package com.HotelT.crudresevas.springboodHotelT.models;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

//Clase para transferencia y manejo de datos de los clientes
public class ClienteDto {
	@NotEmpty(message = "El nombre es obligatorio")
	private String nombre;
	
	@NotEmpty(message = "El correo es obligatorio")
	private String correo;
	
	@NotEmpty(message = "La cedula es obligatoria")
	private String cedula;
	
	@NotEmpty(message = "La contraseña es obligatoria")
	private String contrasena;

	private String rol;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contraseña) {
		this.contrasena = contraseña;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}
