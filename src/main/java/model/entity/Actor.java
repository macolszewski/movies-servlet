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
    @AttributeOverrides({@AttributeOverride(name = "name",column = @Column(name="imie")),
            @AttributeOverride(name = "surname",column = @Column(name="nazwisko")),
            @AttributeOverride(name = "birthDate",column = @Column(name = "data_urodzenia")),
            @AttributeOverride(name = "nationality",column = @Column(name = "narodowość"))})
    private Human human;
    @ManyToMany
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

    @Override
    public String toString() {
        return "Actor{" +
                "human=" + human +
                "} " + super.toString();
    }
}
