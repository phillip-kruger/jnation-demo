package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MovieRatings extends PanacheEntityBase {
    @Id
    public String id;
    public Double imDb;
    public Double metacritic;
    public Double theMovieDb;
    public Double rottenTomatoes;
    public Double filmAffinity;
    public Double quarkus;
}
