package eus.birt.dam.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "conductor")
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idConductor;

    @Column(name = "nombre")
    private String nombreConductor;

    @Column(name = "vehiculo")
    private String vehiculo;

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes = new ArrayList<>();

    public Conductor() {}

    public Conductor(String nombreConductor, String vehiculo) {
        this.nombreConductor = nombreConductor;
        this.vehiculo = vehiculo;
    }

    public int getIdConductor() {
    	return idConductor;
    }

    public void setIdConductor(int idConductor) {
    	this.idConductor = idConductor;
    }

    public String getNombreConductor() {
    	return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
    	this.nombreConductor = nombreConductor;
    }

    public String getVehiculo() {
    	return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
    	this.vehiculo = vehiculo;
    }

    public List<Viaje> getViajes() {
    	return viajes;
    }
    public void setViajes(List<Viaje> viajes) {
    	this.viajes = viajes;
    }

    public void addViaje(Viaje v) {
        viajes.add(v);
        v.setConductor(this);
    }

    public void removeViaje(Viaje v) {
        viajes.remove(v);
        v.setConductor(null);
    }

    @Override
    public String toString() {
        return "Conductor [id= " + idConductor + ", Nombre Conductor = " + nombreConductor + ", Vehiculo = " + vehiculo + "]";
    }
}