package eus.birt.dam.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import eus.birt.dam.domain.Conductor;
import eus.birt.dam.domain.Pasajero;

public class ConductorService {

    private Session session;

    public ConductorService(Session session) {
        this.session = session;
    }

    public void guardarConductor(Conductor conductor) {
        Transaction tx = session.beginTransaction();
        try {
            session.persist(conductor);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public List<Conductor> listarConductores() {
        return session.createQuery("FROM Conductor", Conductor.class).list();
    }

    public Conductor buscarPorId(int id) {
        return session.get(Conductor.class, id);
    }

    public void eliminarConductor(int id) {
        Conductor conductor = session.get(Conductor.class, id);
        if (conductor != null) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(conductor);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

	public void guardarPasajero(Pasajero nuevo) {
		// TODO Auto-generated method stub

	}
}