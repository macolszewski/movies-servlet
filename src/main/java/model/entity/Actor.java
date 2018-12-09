package model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "actor")
@AttributeOverride(name = "id", column = @Column(name="actor_id"))
public class Actor extends BaseEntitiy{

    @Embedded
    private Human human;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Movie> movies = new ArrayList<>();

    public Actor() {
    }

    public Actor(Human human) {
        this.human = human;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public Collection<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Collection<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "human=" + human +
                "} " + super.toString();
    }
}
