package pt.jnation.movie.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.net.URL;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Movie extends PanacheEntityBase {

    @Id
    public String id;
    public String title;
    public String fullTitle;
    public int year;
    public URL image;
    public Date releaseDate;
    public int runtimeMins;
    public String runtimeStr;
    @Column(length = 2550)
    public String plot;
    public String awards;
    public String genres;
    @OneToMany(fetch = FetchType.EAGER)
    public Set<Company> companyList;
    public String countries;
    public String languages;
    public String contentRating;
    public Double imDbRating;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public BoxOffice boxOffice;
    public String tagline;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection(targetClass=String.class)
    public Set<String> keywordList;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<MovieReference> similars;
    @OneToMany(fetch = FetchType.EAGER)
    public Set<Actor> starList;
    
}
