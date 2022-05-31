package pt.jnation.blockbuster.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class Rating extends PanacheEntity {
    public String id;
    public Double rating;
}