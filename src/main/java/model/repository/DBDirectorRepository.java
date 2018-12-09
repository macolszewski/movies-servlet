package model.repository;

import model.HibernateContext;
import model.entity.Actor;
import model.entity.Director;
import model.entity.Human;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DBDirectorRepository implements Repository<Director> {
    private SessionFactory sessionFactory;

    public DBDirectorRepository() {
        this.sessionFactory = HibernateContext.getSessionFactory();
    }

    @Override
    public void add(Director director) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(director);
        transaction.commit();
        session.close();

    }

    @Override
    public List<Director> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Director> directorEntities = session.createQuery("FROM Director").list();
        transaction.commit();
        session.close();
        return directorEntities;

    }

    @Override
    public void delete(String ID) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Director director = (Director) session.get(Director.class, ID);
            session.delete(director);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void update(String ID, Director new_director) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Director director = (Director) session.get(Director.class, ID);
            director.setHuman(new_director.getHuman());
            director.setMovies(new_director.getMovies());
            session.update(director);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
