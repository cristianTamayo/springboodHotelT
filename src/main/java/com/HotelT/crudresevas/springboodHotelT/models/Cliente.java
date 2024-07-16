package com.HotelT.crudresevas.springboodHotelT.models;

import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name="clientes")
public class Cliente {
	
	@Id
	private String cedula;
	@Column(columnDefinition = "Text")
	private String nombre;   
    private String correo;
    private String password;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente",cascade= CascadeType.ALL)
   private List<Reserva> reservas;

    public Cliente() {
    }
    
    
    
    public Cliente(String nombre, String cedula, String correo, String password) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.password = password;
    }

    
    
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



	public List<Reserva> getReservas() {
		return reservas;
	}



	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
    
}
