package util;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.Stoppable;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void stopConnectionProvider() {
        final SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) sessionFactory;
        ConnectionProvider connectionProvider = sessionFactoryImplementor.getConnectionProvider();
        if (Stoppable.class.isInstance(connectionProvider)) {
            ((Stoppable) connectionProvider).stop();
        }
    }

}
