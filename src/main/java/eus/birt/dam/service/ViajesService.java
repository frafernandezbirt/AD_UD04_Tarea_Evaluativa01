package eus.birt.dam.service;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import eus.birt.dam.domain.Conductor;
import eus.birt.dam.domain.Viaje;

public class ViajesService {

    private Session session;

    public ViajesService(Session session) {
        this.session = session;
    }

    public void guardarViaje(Viaje viaje) {
        Transaction tx = session.beginTransaction();
        try {
            session.persist(viaje);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    public List<Viaje> listarViajes() {
        return session.createQuery("FROM Viaje", Viaje.class).list();
    }

    public Viaje buscarPorId(int id) {
        return session.get(Viaje.class, id);
    }

    public Viaje crearYGuardarViaje(String ciudadOrigen, String ciudadDestino, LocalDateTime fechaHora, int plazasDisponibles, int idConductor) {
        Transaction tx = session.beginTransaction();
        try {
            Conductor conductor = session.get(Conductor.class, idConductor);
            if (conductor == null) {
                tx.rollback();
                throw new IllegalArgumentException("Conductor con id " + idConductor + " no existe");
            }
            Viaje viaje = new Viaje(ciudadOrigen, ciudadDestino, fechaHora, plazasDisponibles, conductor);
            session.persist(viaje);
            conductor.getViajes().add(viaje); // mantener relación en memoria
            tx.commit();
            return viaje;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    public void eliminarViaje(int id) {
        Viaje viaje = session.get(Viaje.class, id);
        if (viaje != null) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(viaje);
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }
}