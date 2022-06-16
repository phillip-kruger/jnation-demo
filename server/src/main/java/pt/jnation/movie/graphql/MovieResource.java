package pt.jnation.movie.graphql;

import pt.jnation.movie.ReviewSystemUnavailableException;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import pt.jnation.movie.model.Actor;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieRatings;
import pt.jnation.movie.model.MovieReference;
import pt.jnation.movie.model.Reviewer;
import pt.jnation.movie.model.Reviews;
import pt.jnation.movie.service.MovieService;

@GraphQLApi
public class MovieResource {

    @Inject
    MovieService movieService;
    
    @Query
    public List<MovieReference> searchMovies(String keyword) {
        return movieService.searchMovies(keyword).results;
    }
    
    @Query
    public Movie getMovie(String title) {
        //System.out.println(">>>>>>> MOVIE :" + Thread.currentThread().getName());
        return movieService.getMovie(title);
    }
    
    @Query
    public Movie getMovieById(String id) {
        return movieService.getMovieById(id);
    }
    
    public CastMembers getCastMembers(@Source Movie movie){
        return movieService.getCastMembers(movie.id);
    }
    
    public Set<Actor> getMainActors(@Source CastMembers castMembers, long limit){
        List<Actor> listOfActors = Arrays.asList(castMembers.actors);
        return listOfActors.stream().limit(limit)
                                .collect(Collectors.toSet());
    }
    
    public Map<Reviewer,Double> getRatings(@Source Movie movie){
        return movieService.getMovieRatings(movie.id);
    }
    
    @Mutation
    //@RolesAllowed("admin")
    public Map<Reviewer,Double> rate(String id, 
            @Min(value = 0, message = "Rating too low")
            @Max(value = 10, message = "Rating too high")
            Double rating){
        return movieService.addRating(id, rating);
    }
    
    @Subscription
    public Multi<MovieRatings> listenForRateChanges(){
        return movieService.ratingChangedListener();
    }
    
    //@RolesAllowed("user")
    public Uni<Reviews> getReviews(@Source Movie movie) throws ReviewSystemUnavailableException{
        //System.out.println(">>>>>>> REVIEW :" + Thread.currentThread().getName());
        //throw new ReviewSystemUnavailableException("The Review system seem to be down");
        return movieService.getReviews(movie.id);
    }
    
}
