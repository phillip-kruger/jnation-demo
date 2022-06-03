package pt.jnation.movie.service.storage;

import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieRatings;
import pt.jnation.movie.model.MovieReferences;
import pt.jnation.movie.model.Reviews;

@ApplicationScoped
public class MovieIMDB implements MovieStorable{

    private final Client imdbClient = ClientBuilder.newBuilder().build();
    
    @ConfigProperty(name = "imdb.api.url")
    String imdbUrl;
    
    @ConfigProperty(name = "imdb.api.key")
    String imdbKey;
    
    public MovieReferences searchMovies(String keyword) {
        
        MovieReferences imdbMovieReferences = imdbClient.target(imdbUrl)
                .path("SearchTitle")
                .path(imdbKey)
                .path(keyword)
                .request()
                .get(MovieReferences.class);
        if(imdbMovieReferences!=null){
            imdbMovieReferences.keyword = keyword;
        }
        return imdbMovieReferences;
    }
    
    public Movie getMovieById(String id){
        return imdbClient.target(imdbUrl)
                .path("Title")
                .path(imdbKey)
                .path(id)
                .request()
                .get(Movie.class);
    }
    
    public CastMembers getCastMembers(String id){
        return imdbClient.target(imdbUrl)
                .path("FullCast")
                .path(imdbKey)
                .path(id)
                .request()
                .get(CastMembers.class);
    }
    
    public MovieRatings getMovieRatings(String id){
        MovieRatings imdbMovieRatings = imdbClient.target(imdbUrl)
            .path("Ratings")
            .path(imdbKey)
            .path(id)
            .request()
            .get(MovieRatings.class);
        
        if(imdbMovieRatings!=null){
            imdbMovieRatings.id = id;
        }
        return imdbMovieRatings;
    }
    
    public Uni<Reviews> getReviews(String id){
        return reviewService.getReviews(imdbKey, id);
    }

    @Override
    public MovieReferences newMovieReferences(MovieReferences movieReferences) {
        return movieReferences;
    }

    @Override
    public Movie newMovie(Movie movie) {
        return movie;
    }

    @Override
    public CastMembers newCastMembers(CastMembers castMembers) {
        return castMembers;
    }

    @Override
    public MovieRatings newMovieRatings(MovieRatings movieRatings) {
        return movieRatings;
    }

    @Override
    public MovieRatings updateMovieRatings(MovieRatings movieRatings) {
        return movieRatings;
    }
    
    @RestClient 
    ReviewService reviewService;

    
    @Path("/Reviews")
    @RegisterRestClient(baseUri = "https://imdb-api.com/en/API")
    public interface ReviewService {
    
        @GET
        @Path("/{apiKey}/{id}")
        Uni<Reviews> getReviews(@PathParam("apiKey") String apiKey, @PathParam("id") String id);
    
    }
}
