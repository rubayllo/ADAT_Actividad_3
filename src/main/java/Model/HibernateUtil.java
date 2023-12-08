package Model;

import entity.Autor;
import entity.Libro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

}