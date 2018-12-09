package model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HibernateContext implements ServletContextListener {

    private static SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sessionFactory = buildSessionFactory();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sessionFactory.close();
    }

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                Configuration configuration = new Configuration().configure();
                sessionFactory = configuration.buildSessionFactory();
            }
            return sessionFactory;
        } catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
