package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class CastMembers extends PanacheEntityBase {
    @Id
    public String imDbId;
    @OneToOne(cascade = CascadeType.ALL)
    public Directors directors;
    @OneToOne(cascade = CascadeType.ALL)
    public Writers writers;
    @OneToMany(mappedBy="id")
    public List<Actor> actors;
    
}
