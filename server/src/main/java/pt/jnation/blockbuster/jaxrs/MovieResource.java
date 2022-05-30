package pt.jnation.blockbuster.jaxrs;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieSearchResult;
import pt.jnation.blockbuster.model.MovieSearchResults;
import pt.jnation.blockbuster.service.BlockbusterService;

@Path("/movie")
public class MovieResource {

    @Inject
    BlockbusterService movieService;
    
    @GET
    @Path("/search/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovieSearchResult> searchMovies(@PathParam("keyword") String keyword) {
        return movieService.searchMovies(keyword).getResults();
    }
    
    @GET
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovie(@PathParam("title") String title) {
        return movieService.getMovie(title);
    }
    
    @GET
    @Path("/cast/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CastMembers getCastMembers(@PathParam("id") String id){
        return movieService.getCastMembers(id);
    }
    
}
