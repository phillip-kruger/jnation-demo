package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoxOffice extends PanacheEntityBase {

    @Id @GeneratedValue 
    public Long id;
    public String budget;
    public String openingWeekendUSA;
    public String grossUSA;
    public String cumulativeWorldwideGross;

}
