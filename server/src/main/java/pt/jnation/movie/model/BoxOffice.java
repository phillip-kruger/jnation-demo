package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class BoxOffice extends PanacheEntity {
    public String budget;
    public String openingWeekendUSA;
    public String grossUSA;
    public String cumulativeWorldwideGross;
}
