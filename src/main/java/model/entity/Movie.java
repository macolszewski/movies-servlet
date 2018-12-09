package model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "movie")
@AttributeOverride(name = "id", column = @Column(name="movie_id"))
public class Movie extends BaseEntitiy {
    @Column(name = "tilte")
    private String title;
    @Column(name = "release_year")
    private String releaseyear;
    @Column(name = "rating")
    private String rating;
    @Column(name = "plot")
    private String plot;
    @Column(name = "movie_lenght")
    private String movieLength;
    @ManyToMany
    private Collection<Actor> actors = new ArrayList<>();
    @ManyToOne
    private Director director;
    @ManyToOne
    private MovieGenere movieGenere;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseyear() {
        return releaseyear;
    }

    public void setReleaseyear(String releaseyear) {
        this.releaseyear = releaseyear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(String movieLength) {
        this.movieLength = movieLength;
    }

    public Collection<Actor> getActors() {
        return actors;
    }

    public void setActors(Collection<Actor> actors) {
        this.actors = actors;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public MovieGenere getMovieGenere() {
        return movieGenere;
    }

    public void setMovieGenere(MovieGenere movieGenere) {
        this.movieGenere = movieGenere;
    }

}
