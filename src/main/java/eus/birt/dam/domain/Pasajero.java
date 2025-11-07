package eus.birt.dam.domain;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "pasajero")
public class Pasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombrePasajero;

    @Column(name = "email")
    private String emailPasajero;

    @OneToMany(mappedBy = "pasajero")
    private List<Reserva> reservas;


    public Pasajero() {}

    public Pasajero(String nombrePasajero, String emailPasajero) {
        this.nombrePasajero = nombrePasajero;
        this.emailPasajero = emailPasajero;
    }

    public int getIdPasajero() {
        return id;
    }

    public void setIdPasajero(int idPasajero) {
        this.id = idPasajero;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getEmailPasajero() {
        return emailPasajero;
    }

    public void setEmailPasajero(String emailPasajero) {
        this.emailPasajero = emailPasajero;
    }


    @Override
    public String toString() {
        return "Pasajero [id=" + id + ", nombre=" + nombrePasajero + ", email=" + emailPasajero ;
    }
}