package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MovieReferences extends PanacheEntityBase {
    @Id
    public String keyword;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<MovieReference> results;
    
    public boolean hasResults(){
        return this.results!=null && !this.results.isEmpty();
    }
    
    public MovieReference getResult(){
        if(hasResults()){
            return this.results.iterator().next();
        }
        return null;
    }
    
}
