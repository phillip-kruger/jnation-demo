package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class CastMembers extends PanacheEntityBase {
    @Id
    public String imDbId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Directors directors;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Writers writers;
    @OneToMany(fetch = FetchType.EAGER)
    public Set<Actor> actors;
    
}
