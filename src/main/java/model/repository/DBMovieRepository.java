package model.repository;

import model.HibernateContext;
import model.entity.Actor;
import model.entity.Director;
import model.entity.Human;
import model.entity.Movie;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DBMovieRepository implements Repository<Movie> {
    private SessionFactory sessionFactory;

    public DBMovieRepository() {
        this.sessionFactory = HibernateContext.getSessionFactory();
    }

    @Override
    public void add(Movie movie) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(movie);
        transaction.commit();
        session.close();

    }

    @Override
    public List<Movie> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Movie> movieEntities = session.createQuery("FROM Movie").list();
        transaction.commit();
        session.close();
        return movieEntities;

    }

    @Override
    public void delete(String ID) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Movie movie = (Movie) session.get(Movie.class, ID);
            session.delete(movie);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void update(String ID, Movie new_movie) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Movie movie = (Movie) session.get(Movie.class, ID);
            movie.setActors(new_movie.getActors());
            movie.setDirector(new_movie.getDirector());
            movie.setMovieGenere(new_movie.getMovieGenere());
            movie.setMovieLength(new_movie.getMovieLength());
            movie.setPlot(new_movie.getPlot());
            movie.setRating(new_movie.getRating());
            session.update(movie);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
