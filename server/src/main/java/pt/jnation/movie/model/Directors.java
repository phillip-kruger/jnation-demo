package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Directors extends PanacheEntity {
    @OneToMany(mappedBy="id")
    public List<Person> items;
}
