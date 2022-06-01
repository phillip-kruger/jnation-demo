package pt.jnation.movie.service;

import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieReference;
import pt.jnation.movie.model.MovieReferences;
import pt.jnation.movie.model.Rating;
import pt.jnation.movie.model.Reviewer;
import pt.jnation.movie.model.Reviews;

@ApplicationScoped
public class MovieService {

    private final Client imdbClient = ClientBuilder.newBuilder().build();
    
    @ConfigProperty(name = "imdb.api.url")
    String imdbUrl;
    
    @ConfigProperty(name = "imdb.api.key")
    String imdbKey;
    
    @Inject 
    MovieService blockbusterService;
    
    @RestClient 
    ReviewService reviewService;
    
    BroadcastProcessor<Rating> ratingChangedBroadcaster = BroadcastProcessor.create();
    
    @Transactional
    public Movie getMovie(String title){
        MovieReferences movieReferences = blockbusterService.searchMovies(title);
        if(movieReferences.hasResults()){
            MovieReference movieReference = movieReferences.getResult();
            
            // First check the Database, else fallback to external IMDb
            Movie dbMovie = Movie.findById(movieReference.id);
            if(dbMovie!=null){
                return dbMovie;
            }else {
                Movie imdbMovie = imdbClient.target(imdbUrl)
                        .path("Title")
                        .path(imdbKey)
                        .path(movieReference.id)
                        .request()
                        .get(Movie.class);
                imdbMovie.persistAndFlush();
                return imdbMovie;
            }
        }
        return null;
    }

    @Transactional
    public MovieReferences searchMovies(String keyword) {
        // First check the Database, else fallback to external IMDb
        MovieReferences dbMovieReferences = MovieReferences.findById(keyword);
        if(dbMovieReferences!=null){
            return dbMovieReferences;
        }else {
            MovieReferences imdbMovieReferences = imdbClient.target(imdbUrl)
                    .path("SearchTitle")
                    .path(imdbKey)
                    .path(keyword)
                    .request()
                    .get(MovieReferences.class);
            
            if(imdbMovieReferences!=null && imdbMovieReferences.hasResults()){
                imdbMovieReferences.keyword = keyword;
                imdbMovieReferences.persistAndFlush();
            }
            return imdbMovieReferences;
        }
    }
    
    @Transactional
    public CastMembers getCastMembers(String id){
        // First check the Database, else fallback to external IMDb
        CastMembers dbCastMembers = CastMembers.findById(id);
        if(dbCastMembers!=null){
            return dbCastMembers;
        }else {
            CastMembers imdbCastMembers = imdbClient.target(imdbUrl)
                .path("FullCast")
                .path(imdbKey)
                .path(id)
                .request()
                .get(CastMembers.class);
            
            if(imdbCastMembers!=null){
                imdbCastMembers.persistAndFlush();
            }
            return imdbCastMembers;
        }
    }
    
    public Map<Reviewer,Double> getMovieRatings(String id){
        
        RatingResponse r = imdbClient.target(imdbUrl)
                .path("Ratings")
                .path(imdbKey)
                .path(id)
                .request()
                .get(RatingResponse.class);
        
        Map<Reviewer,Double> ratings = new HashMap<>();
        ratings.put(Reviewer.imDb, r.imDb);
        ratings.put(Reviewer.filmAffinity, r.filmAffinity);
        ratings.put(Reviewer.metacritic, r.metacritic);
        ratings.put(Reviewer.rottenTomatoes, r.rottenTomatoes);
        ratings.put(Reviewer.theMovieDb, r.theMovieDb);
        
        // Also add our own rating
        Rating rating = Rating.findById(id);
        if(rating!=null){
            ratings.put(Reviewer.quarkus, rating.rating);
        }
        return ratings;
    }

    @Transactional
    public Double rate(String id, Double r){
        // If existing, do update
        Rating existing = Rating.findById(id);
        if(existing!=null){
            existing.rating = r;
            existing.persistAndFlush();
            ratingChangedBroadcaster.onNext(existing);
        }else{
            Rating rating = new Rating();
            rating.id = id;
            rating.rating = r;
            rating.persistAndFlush();
            ratingChangedBroadcaster.onNext(rating);
        }
        
        return r;
    }
    
    public Multi<Rating> ratingChangedListener(){
        return ratingChangedBroadcaster;
    }
    
    @CacheResult(cacheName = "review")
    public Uni<Reviews> getReviews(String id){
        return reviewService.getReviews(imdbKey, id);
    }
    
    private static class RatingResponse {
        private Double imDb;
        private Double metacritic;
        private Double theMovieDb;
        private Double rottenTomatoes;
        private Double filmAffinity;
        
        public RatingResponse() {
        }

        public RatingResponse(Double imDb, Double metacritic, Double theMovieDb, Double rottenTomatoes, Double filmAffinity) {
            this.imDb = imDb;
            this.metacritic = metacritic;
            this.theMovieDb = theMovieDb;
            this.rottenTomatoes = rottenTomatoes;
            this.filmAffinity = filmAffinity;
        }

        public Double getImDb() {
            return imDb;
        }

        public void setImDb(Double imDb) {
            this.imDb = imDb;
        }

        public Double getMetacritic() {
            return metacritic;
        }

        public void setMetacritic(Double metacritic) {
            this.metacritic = metacritic;
        }

        public Double getTheMovieDb() {
            return theMovieDb;
        }

        public void setTheMovieDb(Double theMovieDb) {
            this.theMovieDb = theMovieDb;
        }

        public Double getRottenTomatoes() {
            return rottenTomatoes;
        }

        public void setRottenTomatoes(Double rottenTomatoes) {
            this.rottenTomatoes = rottenTomatoes;
        }

        public Double getFilmAffinity() {
            return filmAffinity;
        }

        public void setFilmAffinity(Double filmAffinity) {
            this.filmAffinity = filmAffinity;
        }
    }
}
