package pt.jnation.blockbuster.graphql;

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
import pt.jnation.blockbuster.model.Actor;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieSearchResult;
import pt.jnation.blockbuster.model.Rating;
import pt.jnation.blockbuster.model.Reviewer;
import pt.jnation.blockbuster.model.Reviews;
import pt.jnation.blockbuster.service.BlockbusterService;

@GraphQLApi
public class MovieResource {

    @Inject
    BlockbusterService movieService;
    
    @Query
    public List<MovieSearchResult> searchMovies(String keyword) {
        return movieService.searchMovies(keyword).getResults();
    }
    
    @Query
    public Movie getMovie(String title) {
        System.out.println(">>>>>>> MOVIE :" + Thread.currentThread().getName());
        return movieService.getMovie(title);
    }
    
    public CastMembers getCastMembers(@Source Movie movie){
        return movieService.getCastMembers(movie.getId());
    }
    
    public List<Actor> getMainActors(@Source CastMembers castMembers, int limit){
        return castMembers.getActors().subList(0, limit);
    }
    
    public Map<Reviewer,Double> getRatings(@Source Movie movie){
        return movieService.getMovieRatings(movie.getId());
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
        return movieService.getReviews(movie.getId());
    }
}
