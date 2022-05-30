package pt.jnation.blockbuster.graphql;

import java.util.List;
import java.util.OptionalInt;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieSearchResult;
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
        return movieService.getMovie(title);
    }
    
    public CastMembers getCastMembers(@Source Movie movie){
        return movieService.getCastMembers(movie.getId());
    }
}
