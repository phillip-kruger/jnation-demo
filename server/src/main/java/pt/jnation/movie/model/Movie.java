package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Movie extends PanacheEntityBase {

    @Id
    public String id;
    public String title;
    public String fullTitle;
    public int year;
    public URL image;
    public LocalDate releaseDate;
    public int runtimeMins;
    public String runtimeStr;
    public String plot;
    public String awards;
    public String genres;
    @OneToMany(mappedBy="id")
    public List<Company> companyList;
    public String countries;
    public String languages;
    public String contentRating;
    public Double imDbRating;
    @OneToOne(cascade = CascadeType.ALL)
    public BoxOffice boxOffice;
    public String tagline;
    @ElementCollection(targetClass=String.class)
    public List<String> keywordList;
    @OneToMany(mappedBy="id")
    public List<MovieReference> similars;
    @OneToMany(mappedBy="id")
    public List<Person> starList;
    
}
