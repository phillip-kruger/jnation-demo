package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Directors extends PanacheEntity {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Director> items;
}
