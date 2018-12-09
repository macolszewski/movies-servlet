package model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "director")
@AttributeOverride(name = "id", column = @Column(name="director_id"))
public class Director extends BaseEntitiy{

    @Embedded
    private Human human;
    @OneToMany(mappedBy= "director")
    private Collection<Movie> movies = new ArrayList<>();

    public Director() {
    }

    public Director(Human human) {
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
        return "Director{" +
                "human=" + human +
                "} " + super.toString();
    }
}
