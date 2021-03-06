package pt.jnation.blockbuster.jaxrs;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieReference;
import pt.jnation.blockbuster.model.MovieSearchResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "movies")
@Path("/movie")
public interface MovieRestClient {

    @GET
    @Path("/search/{keyword}")
    List<MovieReference> searchMovies(@PathParam("keyword") String keyword);

    @GET
    @Path("/{title}")
    Movie getMovie(@PathParam("title") String title);

    @GET
    @Path("/cast/{id}")
    CastMembers getCastMembers(@PathParam("id") String id);

}