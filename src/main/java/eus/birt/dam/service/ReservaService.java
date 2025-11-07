package eus.birt.dam.service;


import org.hibernate.Session;
import org.hibernate.Transaction;

import eus.birt.dam.domain.Reserva;

public class ReservaService {

    private final Session session;

    public ReservaService(Session session) {
        this.session = session;
    }

    public void guardarReserva(Reserva reserva) {
        Transaction tx = session.beginTransaction();
        try {
            session.persist(reserva);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
