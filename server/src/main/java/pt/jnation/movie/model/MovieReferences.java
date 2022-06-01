package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MovieReferences extends PanacheEntityBase {
    @Id
    public String keyword;
    @OneToMany(mappedBy="id")
    public List<MovieReference> results;
    
    public boolean hasResults(){
        return results!=null && !results.isEmpty();
    }
    
    public MovieReference getResult(){
        if(hasResults()){
            return results.get(0);
        }
        return null;
    }
    
}
