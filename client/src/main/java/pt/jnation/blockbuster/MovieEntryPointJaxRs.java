package pt.jnation.blockbuster;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import pt.jnation.blockbuster.jaxrs.MovieRestClient;
import pt.jnation.blockbuster.model.MovieSearchResult;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jaxrs")
public class MovieEntryPointJaxRs {

    @Inject
    @RestClient
    MovieRestClient client;

    /* Equivalent GraphQL query:
     {
       searchMovies(keyword:"$keyword") {
         id
         title
       }
     }
    */
    @GET
    @Path("/search/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovieSearchResult> search(@PathParam("keyword") String keyword) {
        return client.searchMovies(keyword);
    }

}
