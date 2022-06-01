package pt.jnation.blockbuster;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import pt.jnation.blockbuster.graphql.MovieGraphQLTypesafeClient;
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

import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Path("/graphql")
public class MovieEntryPointGraphQL {

    @Inject
    @GraphQLClient("movies-dynamic")
    DynamicGraphQLClient dynamicClient;

    @Inject
    @GraphQLClient("movies-typesafe")
    MovieGraphQLTypesafeClient typesafeClient;


    /* Query:
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
    public List<MovieSearchResult> searchMovies(@PathParam("keyword") String keyword) throws Exception {
        Document document = document(
            operation(
                field("searchMovies", args(arg("keyword", keyword)),
                    field("id"),
                    field("title")
                )
            ));
        Response response = dynamicClient.executeSync(document);
        System.out.println(response.getData().toString());
        return response
            .getList(MovieSearchResult.class, "searchMovies");
    }

    /* Equivalent GraphQL query:
      movie(title: "$title") {
         year
         image
         // ...
      }
    */
    @GET
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovie(@PathParam("title") String title) {
        return typesafeClient.getMovie(title);
    }

    /* GraphQL query:
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
        return typesafeClient.getMainActors(title, limit).getCastMembers().getMainActors();
    }


}
