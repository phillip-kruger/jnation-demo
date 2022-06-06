
package pt.jnation.client.rest;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
import io.smallrye.graphql.client.core.Document;
import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine;
import pt.jnation.blockbuster.graphql.MovieGraphQLTypesafeClient;
import pt.jnation.blockbuster.jaxrs.MovieRestClient;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieReference;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {TypesafeGrapQLClient.class, MPRestClient.class})
public class Client {
}

@CommandLine.Command(name = "mprest", description = "Use the Microprofile REST Client")
class MPRestClient implements Runnable {
    @Inject
    @RestClient
    MovieRestClient client;

    @Override
    public void run() {
        List<MovieReference> searchMovies = client.searchMovies("The Godfather");
        
        System.err.println(">>> " + searchMovies);
        
    }              
}

@CommandLine.Command(name = "typesafe", description = "Use the JAXRS REST Client")
class TypesafeGrapQLClient implements Runnable {
    @Inject
    @GraphQLClient("movies-typesafe")
    MovieGraphQLTypesafeClient typesafeClient;

    @Override
    public void run() {
        Movie movie = typesafeClient.getMovie("Top Gun: Maverick");
        System.out.println(">>> movie = " + movie);
    }              
}
