package model.repository;

import model.HibernateContext;
import model.entity.Actor;
import model.entity.Human;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DBActorRepository implements Repository<Actor> {
    private SessionFactory sessionFactory;

    public DBActorRepository() {
        this.sessionFactory = HibernateContext.getSessionFactory();
    }

    @Override
    public void add(Actor actor) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(actor);
        transaction.commit();
        session.close();

    }

    @Override
    public List<Actor> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Actor> actorEntities = session.createQuery("FROM Actor").list();
        transaction.commit();
        session.close();
        return actorEntities;

    }

    @Override
    public void delete(String ID) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Actor actor = (Actor) session.get(Actor.class, ID);
            session.delete(actor);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void update(String ID, Actor new_actor) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Actor actor = (Actor) session.get(Actor.class, ID);
            actor.setHuman(new_actor.getHuman());
            actor.setMovies(new_actor.getMovies());
            session.update(actor);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
