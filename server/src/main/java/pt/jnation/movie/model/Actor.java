package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.net.URL;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Actor extends PanacheEntityBase {
    @Id
    public String id;
    public String name;  
    public URL image;
    public String asCharacter;
}
