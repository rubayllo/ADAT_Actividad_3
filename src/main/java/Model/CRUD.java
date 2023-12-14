package Model;

import entity.Autor;
import entity.Libro;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CRUD {

    private static Session session;

    public static void iniciarSession() {
        session = HibernateUtil.getSession();
    }

    public static void cerrarSession() {
        HibernateUtil.closeSession(session);
    }

    public static void mostrarRegistros(@NotNull String tabla) {
        if (tabla.equals("autor")) {
            List<Autor> dataList = session.createQuery("FROM Autor ", Autor.class).list();

            if (dataList.isEmpty()) {
                System.out.println("La tabla Autor está vacía");
            }

            for (Autor ev : dataList) {
                System.out.println("ID: " + ev.getId() + ", Nombre: " + ev.getNombre());
            }

        } else {
            List<Libro> dataList = session.createQuery("FROM Libro ", Libro.class).list();

            if (dataList.isEmpty()) {
                System.out.println("La tabla Libro está vacía");
            }

            for (Libro u : dataList) {
                System.out.println("ID: " + u.getId() + ", Título: " + u.getTitulo() + "   ***   Autor: " + u.getAutorByAutorId().getNombre());
            }
        }
    }

    public static int buscarRegistro(@NotNull String tabla, String registro) {
        Session session = HibernateUtil.getSession();

        if (tabla.matches("autor")) {
            // Definir la consulta
            String hql = "FROM Autor WHERE nombre = :nombre";

            // Ejecutar la consulta
            Autor autor = session.createQuery(hql, Autor.class)
                    .setParameter("nombre", registro)
                    .uniqueResult();

            if (autor != null) {
                System.out.println("ID: " + autor.getId() + ", Nombre: " + autor.getNombre() + "\n");
                return autor.getId();
            } else {
                System.out.println("No se encontró el autor con el nombre: " + registro + "\n");
            }

        } else {
            // Definir la consulta
            String hql = "FROM Libro WHERE titulo = :titulo";

            // Ejecutar la consulta
            Libro libro = session.createQuery(hql, Libro.class)
                    .setParameter("titulo", registro)
                    .uniqueResult();

            if (libro != null) {
                System.out.println("ID: " + libro.getId() + ", Título: " + libro.getTitulo() + "\n");
                return 1;
            } else {
                System.out.println("No se encontró el libro con el titulo: " + registro + "\n");
            }
        }
        return 0;
    }

    public static void crearRegistro(@NotNull String tabla, String nombre, String titulo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        if (tabla.matches("autor")) {
            if (buscarRegistro(tabla, nombre) == 0) {
                registrarAutor(nombre, session);
            } else System.out.println("El NOMBRE de AUTOR ya estaba registrado");

        } else {
            if (buscarRegistro(tabla, titulo) == 0) {
                registrarLibro(nombre, titulo, session);
            } else System.out.println("El TITULO del LIBRO ya estaba registrado");
        }
        session.getTransaction().commit();
    }

    private static void registrarLibro(String nombre, String titulo, @NotNull Session session) {

        int autorID = buscarRegistro("autor", nombre);

        if (autorID == 0) {
            autorID = registrarAutor(nombre, session);
        }

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutorId(autorID);
        session.persist(libro);

        System.out.println("LIBRO registrado correctamente");
    }

    private static int registrarAutor(String nombre, @NotNull Session session) {
        Autor autor = new Autor();
        autor.setNombre(nombre);
        session.persist(autor);
        System.out.println("AUTOR registrado correctamente");
        return autor.getId();
    }

    public static void eliminar(String id, String tabla) {
        try {
            if (tabla.equals("autor")) {
                // Cargar la entidad que deseas eliminar
                Autor autor = session.get(Autor.class, id);

                if (autor != null) {
                    // Iniciar la transacción y eliminar la entidad
                    session.beginTransaction();
                    session.remove(autor);
                    session.getTransaction().commit();

                    System.out.println("AUTOR eliminado correctamente.");
                } else {
                    System.out.println("No se encontró el autor con el ID: " + id);
                }
            } else {
                // Cargar la entidad que deseas eliminar
                Libro libro = session.get(Libro.class, id);

                if (libro != null) {
                    // Iniciar la transacción y eliminar la entidad
                    session.beginTransaction();
                    session.remove(libro);
                    session.getTransaction().commit();

                    System.out.println("LIBRO eliminado correctamente.");
                } else {
                    System.out.println("No se encontró el libro con el ID: " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actualizarRegistro(@NotNull String tabla, String idRegistro, String registro) {

        // Cargar la entidad que deseas actualuzar
        if (tabla.equals("autor")) {

            Autor autor = session.get(Autor.class, idRegistro);

            if (autor != null) {
                // Actualizamos la propiedad que querramos
                autor.setNombre(registro);

                // Iniciar la transacción y eliminar la entidad
                session.beginTransaction();
                session.merge(autor);
                session.getTransaction().commit();

                System.out.println("AUTOR actualizado correctamente.");
            } else {
                System.out.println("No se encontró el AUTOR con el ID: " + idRegistro);
            }
        } else {

            Libro libro = session.get(Libro.class, idRegistro);

            if (libro != null) {
                // Actualizamos la propiedad que querramos
                libro.setTitulo(registro);

                // Iniciar la transacción y eliminar la entidad
                session.beginTransaction();
                session.merge(libro);
                session.getTransaction().commit();

                System.out.println("LIBRO actualizado correctamente.");
            } else {
                System.out.println("No se encontró el LIBRO con el ID: " + idRegistro);
            }
        }

    }
}
