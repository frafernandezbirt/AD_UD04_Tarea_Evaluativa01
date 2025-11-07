package eus.birt.dam.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import eus.birt.dam.domain.Conductor;
import eus.birt.dam.domain.Pasajero;
import eus.birt.dam.domain.Reserva;
import eus.birt.dam.domain.Viaje;
import eus.birt.dam.service.ConductorService;
import eus.birt.dam.service.PasajeroService;
import eus.birt.dam.service.ReservaService;
import eus.birt.dam.service.ViajesService;

public class MainApp {

    public static final String CREAR_CONDUCTOR = "1";
    public static final String CREAR_VIAJE = "2";
    public static final String BUSCAR_VIAJES_DISPONIBLES = "3";
    public static final String CREAR_PASAJERO = "4";
    public static final String CREAR_RESERVA = "5";
    public static final String CANCELAR_RESERVA = "6";
    public static final String LISTAR_VIAJES = "7";
    public static final String SALIR = "8";
    public static final String[] opcionesMenu = {
            "Crear conductor", "Crear viaje", "Buscar viajes disponibles", "Crear pasajero",
            "Crear Reserva", "Cancelar reserva", "Lista viajes", "Salir"
    };

    public static void main(String[] args) {

        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Conductor.class)
                .addAnnotatedClass(Viaje.class)
                .addAnnotatedClass(Pasajero.class)
                .addAnnotatedClass(Reserva.class)
                .getMetadataBuilder()
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        Scanner leerTeclado = new Scanner(System.in);

        try {
            String opcion = elegirOpcion(leerTeclado);
            while (!opcion.equals(SALIR)) {
                switch (opcion) {
                    case CREAR_CONDUCTOR:
                        System.out.println("Creando Conductor");
                        crearConductor(leerTeclado, session);
                        break;
                    case CREAR_VIAJE:
                        System.out.println("Creando Viaje");
                        crearViaje(leerTeclado, session);
                        break;
                    case BUSCAR_VIAJES_DISPONIBLES:
                        System.out.println("Apartado no realizado");
                        break;
                    case CREAR_PASAJERO:
                        System.out.println("Creando pasajero");
                        crearPasajero(leerTeclado, session);
                        break;
                    case CREAR_RESERVA:
                        System.out.println("Creando Reserva");
                        crearReserva(leerTeclado, session);
                        break;
                    case CANCELAR_RESERVA:
                        System.out.println("Cancelando Resrva");
                        cancelarReserva(leerTeclado, session);
                        break;
                    case LISTAR_VIAJES:
                        System.out.println("Listando Viajes: ");
                        listarViajes(leerTeclado, session);
                        break;
                    default:
                        System.out.println("Opcion incorrecta");
                        break;
                }
                opcion = elegirOpcion(leerTeclado);
            }
        } catch (Exception e) {
            System.out.println("Error en la aplicación:");
            e.printStackTrace();
        } finally {
            // cerrar recursos
            if (session != null && session.isOpen()) session.close();
            if (sessionFactory != null && !sessionFactory.isClosed()) sessionFactory.close();
            leerTeclado.close();
        }

        System.out.println("Fin del programa");
    }


	public static String elegirOpcion(Scanner teclado) {
        System.out.println("=== Menú de Gestión de Viaes Compartidos ===");
        for (int i = 0; i < opcionesMenu.length; i++) {
            System.out.println((i + 1) + ". " + opcionesMenu[i]);
        }
        System.out.print("Elige tu opcion (8 para acabar): ");
        String opcion = teclado.next();
        return opcion;
    }

    public static void crearConductor(Scanner teclado, Session sesion) {
        teclado.nextLine(); // limpiar salto pendiente
        System.out.println("Introduce nombre del conductor: ");
        String nombre = teclado.nextLine();

        System.out.println("Introduce vehículo: ");
        String vehiculo = teclado.nextLine();

        Conductor nuevo = new Conductor(nombre, vehiculo);
        ConductorService servicio = new ConductorService(sesion);
        servicio.guardarConductor(nuevo);

        System.out.println("Conductor guardado: " + nuevo);
    }

    public static void crearViaje(Scanner teclado, Session sesion) {
        teclado.nextLine(); // limpiar salto pendiente
        System.out.println("Introduce nombre de la ciudad de origen: ");
        String ciudadOrigen = teclado.nextLine();

        System.out.println("Introduce nombre de la ciudad de destino: ");
        String ciudadDestino = teclado.nextLine();

        System.out.println("Introduce la fecha del viaje (YYYY-MM-DD): ");
        String fechaSalida = teclado.nextLine();

        System.out.println("Introduce la hora de salida (HH:mm) o (HH:mm:ss): ");
        String horaSalida = teclado.nextLine();

        // Construir LocalDateTime a partir de fecha + hora con formato ISO (ej: 2025-11-05 20:30)
        LocalDateTime fechaHora;
        try {
            // Intentar parseo ISO local: yyyy-MM-dd'T'HH:mm[:ss]
            // Construimos string "yyyy-MM-ddTHH:mm" o "yyyy-MM-ddTHH:mm:ss"
            String fechaHoraStr = fechaSalida + "T" + horaSalida;
            // Intentar parsear directamente (acepta ambos formatos si hora tiene segundos opcional)
            fechaHora = LocalDateTime.parse(fechaHoraStr);
        } catch (Exception e) {
            // Si no cumple el formato ISO, intentar con un formatter flexible, por ejemplo "yyyy-MM-dd HH:mm"
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                fechaHora = LocalDateTime.parse(fechaSalida + " " + horaSalida, fmt);
            } catch (Exception ex) {
                System.out.println("Formato de fecha/hora inválido. Usa YYYY-MM-DD y HH:mm o HH:mm:ss. Operación cancelada.");
                return;
            }
        }

        System.out.println("Introduce el numero de plazas del viaje: ");
        int plazasDisponibles;
        try {
            plazasDisponibles = Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Número de plazas inválido. Operación cancelada.");
            return;
        }

        System.out.println("Introduce el id del conductor del viaje: ");
        int id_conductor;
        try {
            id_conductor = Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Id de conductor inválido. Operación cancelada.");
            return;
        }

        Conductor conductor = sesion.get(Conductor.class, id_conductor);
        if (conductor == null) {
            System.out.println("No existe conductor con id " + id_conductor + ". Operación cancelada.");
            return;
        }

        Viaje nuevoViaje = new Viaje(ciudadOrigen, ciudadDestino, fechaHora, plazasDisponibles, conductor);
        ViajesService servicio = new ViajesService(sesion);
        servicio.guardarViaje(nuevoViaje);

        System.out.println("Viaje guardado: " + nuevoViaje);
    }

    public static void crearPasajero(Scanner teclado, Session sesion) {
        teclado.nextLine(); // limpiar salto pendiente
        System.out.println("Introduce nombre del pasajero: ");
        String nombre = teclado.nextLine();

        System.out.println("Introduce email: ");
        String email = teclado.nextLine();

        Pasajero nuevo = new Pasajero(nombre, email);
        PasajeroService servicio = new PasajeroService(sesion);
        servicio.guardarPasajero(nuevo);

        System.out.println("Pasajero guardado: " + nuevo);
    }

    private static void crearReserva(Scanner teclado, Session session) {
        teclado.nextLine(); // limpiar salto pendiente

        try {
            System.out.println("Introduce la fecha de la reserva (YYYY-MM-DD): ");
            String fechaReservaStr = teclado.nextLine();
            LocalDate fechaReserva = LocalDate.parse(fechaReservaStr);
            //teclado.nextLine(); // limpiar salto pendiente

            System.out.println("Introduce el número de plazas de la reserva: ");
            int numeroPlazas = teclado.nextInt();
            teclado.nextLine(); // limpiar salto pendiente

            System.out.println("Introduce el ID del pasajero: ");
            int pasajeroId = teclado.nextInt();
            Pasajero pasajero = session.find(Pasajero.class, pasajeroId);
            if (pasajero == null) {
                System.out.println("Pasajero no encontrado.");
                return;
            }

            System.out.println("Introduce el ID del viaje: ");
            int viajeId = teclado.nextInt();
            Viaje viaje = session.find(Viaje.class, viajeId);
            if (viaje == null) {
                System.out.println("Viaje no encontrado.");
                return;
            }

            // Crear la reserva
            Reserva nuevaReserva = new Reserva(fechaReserva, numeroPlazas, pasajero, viaje);
            ReservaService reservaService = new ReservaService(session);
            reservaService.guardarReserva(nuevaReserva);

            System.out.println("Reserva creada con éxito: " + nuevaReserva);

        } catch (Exception e) {
            System.out.println("Error al crear la reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }


	private static void listarViajes(Scanner leerTeclado, Session session) {
	    try {
	        List<Viaje> viajes = session.createQuery("FROM Viaje", Viaje.class).getResultList();

	        if (viajes.isEmpty()) {
	            System.out.println("No hay viajes registrados.");
	            return;
	        }

	        System.out.println("Lista de viajes disponibles:");
	        for (Viaje viaje : viajes) {
	            System.out.println("----------------------------------");
	            System.out.println("ID: " + viaje.getIdViaje());
	            System.out.println("Origen: " + viaje.getCiudadOrigen());
	            System.out.println("Destino: " + viaje.getCiudadDestino());
	            System.out.println("Fecha y hora: " + viaje.getFechaHora());
	            System.out.println("Plazas disponibles: " + viaje.getPlazasDisponibles());

	            Conductor conductor = viaje.getConductor();
	            if (conductor != null) {
	                System.out.println("Conductor: " + conductor.getNombreConductor() + " (" + conductor.getVehiculo() + ")");
	            } else {
	                System.out.println("Conductor: no asignado");
	            }
	        }
	        System.out.println("----------------------------------");

	    } catch (Exception e) {
	        System.out.println("Error al listar los viajes: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	private static void cancelarReserva(Scanner teclado, Session session) {
		teclado.nextLine(); // limpiar salto pendiente
        try {
            System.out.println("Introduce el ID de la reserva que deseas cancelar:");
            int idReserva = teclado.nextInt();

            if (idReserva == 0) {
                System.out.println("El ID no puede ser cero.");
                return;
            }

            Reserva reserva = session.get(Reserva.class, idReserva);

            if (reserva == null) {
                System.out.println("No existe ninguna reserva con ese ID.");
                return;
            }

            Transaction tx = session.beginTransaction();
            ReservaService reservaService = new ReservaService(session);
			reservaService.eliminarReserva(idReserva);
            tx.commit();

            System.out.println("Reserva cancelada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número válido. No puede cotener letras");
        } catch (Exception e) {
            System.err.println("Error al cancelar la reserva: " + e.getMessage());
        }
    }
}