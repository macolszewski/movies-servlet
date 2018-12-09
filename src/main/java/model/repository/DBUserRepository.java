package model.repository;

import model.HibernateContext;
import model.entity.Actor;
import model.entity.Human;
import model.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DBUserRepository implements Repository<User> {
    private SessionFactory sessionFactory;

    public DBUserRepository() {
        this.sessionFactory = HibernateContext.getSessionFactory();
    }

    @Override
    public void add(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();

    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> userEntities = session.createQuery("FROM User ").list();
        transaction.commit();
        session.close();
        return userEntities;

    }

    @Override
    public void delete(String ID) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, ID);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void update(String ID, User new_user) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, ID);
            user.setLogin(new_user.getLogin());
            user.setPassword(new_user.getPassword());
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
