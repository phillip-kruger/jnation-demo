package pt.jnation.blockbuster;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import pt.jnation.blockbuster.jaxrs.MovieRestClient;
import pt.jnation.blockbuster.model.Actor;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
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
    public List<MovieSearchResult> searchMovies(@PathParam("keyword") String keyword) {
        return client.searchMovies(keyword);
    }

    /* Equivalent GraphQL query:
    {
       movie(title: "$title") {
          year
          image
          // ...
       }
    }
     */
    @GET
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovie(@PathParam("title") String title) {
        return client.getMovie(title);
    }

    /* Equivalent GraphQL query:
{
  movie(title:"$title") {
    castMembers {
      mainActors(limit: $limit) {
        name
      }
    }
  }
}
     */
    @GET
    @Path("/mainactors/{title}/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actor> getMainActors(@PathParam("title") String title, @PathParam("limit") Integer limit) {
        return client.getCastMembers(title).getActors().stream().limit(limit).toList();
    }


}
