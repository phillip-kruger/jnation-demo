package pt.jnation.blockbuster;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
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
    public List<MovieSearchResult> search(@PathParam("keyword") String keyword) throws Exception {
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

}
