package com.HotelT.crudresevas.springboodHotelT.models;

import java.util.ArrayList;
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
    private String rol;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente",cascade= CascadeType.ALL)
   private List<Reserva> reservas = new ArrayList<Reserva>();

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

	

	public String getRol() {
		return rol;
	}



	public void setRol(String rol) {
		this.rol = rol;
	}



	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public long totalReservas() {
		long total=0;
		for(Reserva reserva :this.reservas) {
			total+=reserva.valorReserva();
		    }
		
		return total;
	}
    
}
