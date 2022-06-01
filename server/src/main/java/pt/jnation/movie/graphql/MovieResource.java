package pt.jnation.movie.graphql;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import pt.jnation.movie.model.Actor;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieReference;
import pt.jnation.movie.model.Rating;
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
        System.out.println(">>>>>>> MOVIE :" + Thread.currentThread().getName());
        return movieService.getMovie(title);
    }
    
    public CastMembers getCastMembers(@Source Movie movie){
        return movieService.getCastMembers(movie.id);
    }
    
    public List<Actor> getMainActors(@Source CastMembers castMembers, int limit){
        return castMembers.actors.subList(0, limit);
    }
    
    public Map<Reviewer,Double> getRatings(@Source Movie movie){
        return movieService.getMovieRatings(movie.id);
    }
    
    @Mutation
    @RolesAllowed("admin")
    public Map<Reviewer,Double> rate(String id, Double rating){
        movieService.rate(id, rating);
        return movieService.getMovieRatings(id);
    }
    
    @Subscription
    public Multi<Rating> listenForRateChanges(){
        return movieService.ratingChangedListener();
    }
    
    public Uni<Reviews> getReviews(@Source Movie movie){
        System.out.println(">>>>>>> REVIEW :" + Thread.currentThread().getName());
        return movieService.getReviews(movie.id);
    }
}
