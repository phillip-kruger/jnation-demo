package pt.jnation.blockbuster.model;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class Movie extends MovieSearchResult {

    private String fullTitle;
    private int year;
    private URL image;
    private LocalDate releaseDate;
    private int runtimeMins;
    private String runtimeStr;
    private String plot;
    private String awards;
    private String genres;
    private List<Company> companyList;
    private String countries;
    private String languages;
    private String contentRating;
    private Double imDbRating;
    private BoxOffice boxOffice;
    private String tagline;
    private List<String> keywordList;
    private List<MovieSearchResult> similars;
    private List<Person> starList;
    
    public Movie() {
    }

    public Movie(String fullTitle, int year, URL image, LocalDate releaseDate, int runtimeMins, String runtimeStr, String plot, String awards, String genres, List<Company> companyList, String countries, String languages, String contentRating, Double imDbRating, BoxOffice boxOffice, String tagline, List<String> keywordList, List<MovieSearchResult> similars, String id, String title, List<Person> starList) {
        super(id, title);
        this.fullTitle = fullTitle;
        this.year = year;
        this.image = image;
        this.releaseDate = releaseDate;
        this.runtimeMins = runtimeMins;
        this.runtimeStr = runtimeStr;
        this.plot = plot;
        this.awards = awards;
        this.genres = genres;
        this.companyList = companyList;
        this.countries = countries;
        this.languages = languages;
        this.contentRating = contentRating;
        this.imDbRating = imDbRating;
        this.boxOffice = boxOffice;
        this.tagline = tagline;
        this.keywordList = keywordList;
        this.similars = similars;
        this.starList = starList;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(int runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getRuntimeStr() {
        return runtimeStr;
    }

    public void setRuntimeStr(String runtimeStr) {
        this.runtimeStr = runtimeStr;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public Double getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(Double imDbRating) {
        this.imDbRating = imDbRating;
    }

    public BoxOffice getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOffice boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<String> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<String> keywordList) {
        this.keywordList = keywordList;
    }

    public List<MovieSearchResult> getSimilars() {
        return similars;
    }

    public void setSimilars(List<MovieSearchResult> similars) {
        this.similars = similars;
    }
    
    public List<Person> getStarList(){
        return this.starList;
    }
    
    public void setStarList(List<Person> starList){
        this.starList = starList;
    }
}
