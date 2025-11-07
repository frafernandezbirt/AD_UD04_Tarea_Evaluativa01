package eus.birt.dam.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "viaje")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ciudadOrigen")
    private String ciudadOrigen;

    @Column(name = "ciudadDestino")
    private String ciudadDestino;

    @Column(name = "fechaHora")
    private LocalDateTime fechaHora; // cambiado a LocalDateTime

    @Column(name = "plazasDisponibles")
    private int plazasDisponibles;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    public Viaje() {}

    public Viaje(String ciudadOrigen, String ciudadDestino, LocalDateTime fechaHora, int plazasDisponibles, Conductor conductor) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.fechaHora = fechaHora;
        this.plazasDisponibles = plazasDisponibles;
        this.conductor = conductor;
    }

    public int getIdViaje() {
    	return id;
    }

    public void setIdViaje(int idViaje) {
    	this.id = idViaje;
    }

    public String getCiudadOrigen() {
    	return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
    	this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
    	return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
    	this.ciudadDestino = ciudadDestino;
    }

    public LocalDateTime getFechaHora() {
    	return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
    	this.fechaHora = fechaHora;
    }

    public int getPlazasDisponibles() {
    	return plazasDisponibles;
    }

    public void setPlazasDisponibles(int plazasDisponibles) {
    	this.plazasDisponibles = plazasDisponibles;
    }

    public Conductor getConductor() {
    	return conductor;
    }
    public void setConductor(Conductor conductor) {
    	this.conductor = conductor;
    }

    @Override
    public String toString() {
        return "Viaje [idViaje=" + id + ", ciudadOrigen=" + ciudadOrigen + ", ciudadDestino=" + ciudadDestino
                + ", fechaHora=" + fechaHora + ", plazasDisponibles=" + plazasDisponibles + ", conductor=" + conductor
                + "]";
    }
}