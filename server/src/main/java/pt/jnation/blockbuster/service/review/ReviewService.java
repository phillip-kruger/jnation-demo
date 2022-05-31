package pt.jnation.blockbuster.service.review;

import io.smallrye.mutiny.Uni;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pt.jnation.blockbuster.model.Reviews;

@Path("/Reviews")
@RegisterRestClient(baseUri = "https://imdb-api.com/en/API")
public interface ReviewService {
    
    @GET
    @Path("/{apiKey}/{id}")
    Uni<Reviews> getReviews(@PathParam("apiKey") String apiKey, @PathParam("id") String id);
    
}
