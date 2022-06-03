package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
//import java.util.Set;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import org.eclipse.microprofile.graphql.Ignore;

@Entity
public class Company extends PanacheEntityBase {
    
    @Id
    public String id;
    public String name;
    
    //@Ignore
    //@ManyToMany(mappedBy = "companyList")
    //public Set<Movie> movies;
}
