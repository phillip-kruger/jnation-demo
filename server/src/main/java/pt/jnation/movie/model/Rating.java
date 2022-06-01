package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rating extends PanacheEntityBase {
    @Id
    public String id;
    public Double rating;
}