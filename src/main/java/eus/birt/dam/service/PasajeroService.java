package eus.birt.dam.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import eus.birt.dam.domain.Pasajero;
import java.util.List;

public class PasajeroService {

    private final Session session;

    public PasajeroService(Session session) {
        this.session = session;
    }

    public void guardarPasajero(Pasajero pasajero) {
        Transaction tx = session.beginTransaction();
        try {
            session.persist(pasajero);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    public List<Pasajero> listarPasajeros() {
        return session.createQuery("FROM Pasajero", Pasajero.class).list();
    }

    public Pasajero buscarPorId(int id) {
        return session.get(Pasajero.class, id);
    }

    public void eliminarPasajero(int id) {
        Pasajero p = session.get(Pasajero.class, id);
        if (p != null) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(p);
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }
}