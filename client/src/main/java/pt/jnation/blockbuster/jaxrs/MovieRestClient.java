package pt.jnation.blockbuster.jaxrs;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pt.jnation.blockbuster.model.MovieSearchResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "blockbuster")
@Path("/movie")
public interface MovieRestClient {

    @GET
    @Path("/search/{keyword}")
    List<MovieSearchResult> searchMovies(@PathParam("keyword") String keyword);

}