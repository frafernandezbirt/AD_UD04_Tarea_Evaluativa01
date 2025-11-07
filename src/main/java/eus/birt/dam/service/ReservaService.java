package eus.birt.dam.service;


import org.hibernate.Session;
import org.hibernate.Transaction;

import eus.birt.dam.domain.Reserva;
import eus.birt.dam.domain.Viaje;

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

    public void eliminarReserva(int id) {
        Reserva reserva = session.get(Reserva.class, id);
        if (reserva != null) {
            session.remove(reserva); // No maneja transacción aquí
        } else {
            System.out.println("No se encontró la reserva con ID: " + id);
        }

    }
}
