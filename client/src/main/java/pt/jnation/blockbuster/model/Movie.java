package pt.jnation.blockbuster.model;

import org.eclipse.microprofile.graphql.DateFormat;
import org.eclipse.microprofile.graphql.Ignore;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Movie extends MovieSearchResult {

    public String fullTitle;
    public int year;
    public int runtimeMins;
    public String runtimeStr;
    public String plot;
    public String awards;
    public String genres;
    public List<Company> companyList;
    public String countries;
    public String languages;
    public String contentRating;
    public Double imDbRating;
    public BoxOffice boxOffice;
    public String tagline;
    public List<String> keywordList;
    public List<MovieSearchResult> similars;
    public List<Person> starList;

}
