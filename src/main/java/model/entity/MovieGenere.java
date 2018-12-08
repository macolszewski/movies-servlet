package model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "movie_genere")
@AttributeOverride(name = "id", column = @Column(name = "genre_id"))
public class MovieGenere extends BaseEntitiy {
    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy= "movieGeneres")
    private Collection<Movie> movies = new ArrayList<>();

    public MovieGenere() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Collection<Movie> movies) {
        this.movies = movies;
    }
}
