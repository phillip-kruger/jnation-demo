package pt.jnation.movie.service;

import io.quarkus.cache.CacheInvalidateAll;
import pt.jnation.movie.service.storage.MovieIMDB;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieRatings;
import pt.jnation.movie.model.MovieReference;
import pt.jnation.movie.model.MovieReferences;
import pt.jnation.movie.model.Reviewer;
import pt.jnation.movie.model.Reviews;
import pt.jnation.movie.service.storage.MovieStorable;
import pt.jnation.movie.service.storage.MovieStorageFactory;

@ApplicationScoped
public class MovieService {

    BroadcastProcessor<MovieRatings> ratingChangedBroadcaster = BroadcastProcessor.create();
    
    @Inject
    MovieIMDB movieIMDB; 
    
    @Inject
    MovieStorageFactory movieStorageFactory;
    
    MovieStorable movieStorage; 
    
    @Inject
    MovieService movieService;
    
    @PostConstruct
    public void init(){
        this.movieStorage = movieStorageFactory.getMovieStorable();
    }
    
    @CacheResult(cacheName = "MovieReferences")
    public MovieReferences searchMovies(String keyword) {
        MovieReferences storedMovieReferences = movieStorage.searchMovies(keyword);
        if(storedMovieReferences!=null){
            return storedMovieReferences;
        }else {
            MovieReferences imdbMovieReferences = movieIMDB.searchMovies(keyword);    
            return movieStorage.newMovieReferences(imdbMovieReferences); 
        }
    }
    
    public Movie getMovie(String title){
        MovieReferences movieReferences = movieService.searchMovies(title);
        if(movieReferences.hasResults()){
            MovieReference movieReference = movieReferences.getResult();
            return movieService.getMovieById(movieReference.id);
        }
        return null;
    }

    @CacheResult(cacheName = "Movie")
    public Movie getMovieById(String id){
        Movie storedMovie = movieStorage.getMovieById(id);
        if(storedMovie!=null){
            System.out.println(">> Returning movie from storage");
            return storedMovie;
        }else {
            Movie imdbMovie = movieIMDB.getMovieById(id);
            System.out.println(">> Returning movie from IMDb");
            return movieStorage.newMovie(imdbMovie);
        }
    }
    
    @CacheResult(cacheName = "CastMembers")
    public CastMembers getCastMembers(String id){
        CastMembers storedCastMembers = movieStorage.getCastMembers(id);
        if(storedCastMembers!=null){
            return storedCastMembers;
        }else {
            CastMembers imdbCastMembers = movieIMDB.getCastMembers(id);
            return movieStorage.newCastMembers(imdbCastMembers);
        }
    }
    
    @CacheResult(cacheName = "MovieRatings")
    public Map<Reviewer,Double> getMovieRatings(String id){
        return toMap(fetchMovieRatings(id));
    }

    @CacheResult(cacheName = "Reviews")
    public Uni<Reviews> getReviews(String id) {
        return movieIMDB.getReviews(id);
    }
    
    @CacheInvalidateAll(cacheName = "MovieRatings")
    public Map<Reviewer,Double> addRating(String id, Double r){
        MovieRatings movieRatings = fetchMovieRatings(id);
        movieRatings.quarkus = r;
        
        movieRatings = movieStorage.updateMovieRatings(movieRatings);
        ratingChangedBroadcaster.onNext(movieRatings);
        return toMap(movieRatings);
    }
    
    public Multi<MovieRatings> ratingChangedListener(){
        return ratingChangedBroadcaster;
    }
    
    private MovieRatings fetchMovieRatings(String id){
        MovieRatings storedMovieRatings = movieStorage.getMovieRatings(id);
        if(storedMovieRatings!=null){
            return storedMovieRatings;
        }else{
            MovieRatings imdbMovieRatings = movieIMDB.getMovieRatings(id);
            return movieStorage.newMovieRatings(imdbMovieRatings);
        }
    }
    
    private Map<Reviewer,Double> toMap(MovieRatings r){
        Map<Reviewer,Double> ratings = new HashMap<>();
        ratings.put(Reviewer.imDb, r.imDb);
        ratings.put(Reviewer.filmAffinity, r.filmAffinity);
        ratings.put(Reviewer.metacritic, r.metacritic);
        ratings.put(Reviewer.rottenTomatoes, r.rottenTomatoes);
        ratings.put(Reviewer.theMovieDb, r.theMovieDb);
        ratings.put(Reviewer.quarkus, r.quarkus);
        return ratings;
    }
}
