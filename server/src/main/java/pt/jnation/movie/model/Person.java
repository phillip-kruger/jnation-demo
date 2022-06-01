package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person extends PanacheEntityBase {
    @Id
    public String id;
    public String name;  
    
}
