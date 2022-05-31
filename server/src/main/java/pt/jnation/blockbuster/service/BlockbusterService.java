package pt.jnation.blockbuster.service;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import pt.jnation.blockbuster.model.MovieSearchResults;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieSearchResult;
import pt.jnation.blockbuster.model.Rating;
import pt.jnation.blockbuster.model.Reviewer;

@ApplicationScoped
public class BlockbusterService {

    private final Client imdbClient = ClientBuilder.newBuilder().build();
    
    @ConfigProperty(name = "imdb.api.url")
    String imdbUrl;
    
    @ConfigProperty(name = "imdb.api.key")
    String imdbKey;
    
    @Inject 
    BlockbusterService blockbusterService;
    
    BroadcastProcessor<Rating> ratingChangedBroadcaster = BroadcastProcessor.create();
    
    @CacheResult(cacheName = "movie")
    public Movie getMovie(String title){
        MovieSearchResults movieSearchResults = blockbusterService.searchMovies(title);
        if(movieSearchResults.hasResults()){
            MovieSearchResult movieSearchResult = movieSearchResults.getResult();
            return imdbClient.target(imdbUrl)
                .path("Title")
                .path(imdbKey)
                .path(movieSearchResult.getId())
                .request()
                .get(Movie.class);
        }
        return null;
    }

    @CacheResult(cacheName = "movies")
    public MovieSearchResults searchMovies(String keyword) {
        return imdbClient.target(imdbUrl)
                .path("SearchTitle")
                .path(imdbKey)
                .path(keyword)
                .request()
                .get(MovieSearchResults.class);
        
    }
    
    @CacheResult(cacheName = "cast")
    public CastMembers getCastMembers(String id){
        return imdbClient.target(imdbUrl)
                .path("FullCast")
                .path(imdbKey)
                .path(id)
                .request()
                .get(CastMembers.class);
    }
    
    @CacheResult(cacheName = "rating")
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
            ratings.put(Reviewer.blockbuster, rating.rating);
        }
        return ratings;
    }

    @CacheInvalidate(cacheName = "rating")
    @Transactional
    public Double rate(@CacheKey String id, Double r){
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
