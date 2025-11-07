package eus.birt.dam.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva")

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fechaReserva")
    private LocalDate fechaReserva;

    @Column(name = "numeroPlazasReservadas")
    private int numeroPlazasReservadas;


    @ManyToOne
    @JoinColumn(name = "pasajero_id", nullable = false)
    private Pasajero pasajero;

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    // Constructores
    public Reserva() {}

    public Reserva(LocalDate fechaReserva, int numeroPlazasReservadas, Pasajero pasajero, Viaje viaje) {
        this.fechaReserva = fechaReserva;
        this.numeroPlazasReservadas = numeroPlazasReservadas;
        this.pasajero = pasajero;
        this.viaje = viaje;
    }

    // Getters y Setters
    //public Long getId() {
    //    return id;
    //}

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getNumeroPlazasReservadas() {
        return numeroPlazasReservadas;
    }

    public void setNumeroPlazasReservadas(int numeroPlazasReservadas) {
        this.numeroPlazasReservadas = numeroPlazasReservadas;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }





}
