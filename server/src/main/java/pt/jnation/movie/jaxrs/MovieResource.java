package pt.jnation.movie.jaxrs;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieReference;
import pt.jnation.movie.service.MovieService;

@Path("/movie")
public class MovieResource {

    @Inject
    MovieService movieService;
    
    @GET
    @Path("/search/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovieReference> searchMovies(@PathParam("keyword") String keyword) {
        return movieService.searchMovies(keyword).results;
    }
    
    @GET
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovie(@PathParam("title") String title) {
        return movieService.getMovie(title);
    }
    
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovieById(@PathParam("id") String id) {
        return movieService.getMovieById(id);
    }
    
    @GET
    @Path("/cast/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CastMembers getCastMembers(@PathParam("id") String id){
        return movieService.getCastMembers(id);
    }
    
}
