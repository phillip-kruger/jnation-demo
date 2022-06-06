package pt.jnation.blockbuster.command;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine;
import pt.jnation.blockbuster.Utils;
import pt.jnation.blockbuster.jaxrs.MovieRestClient;
import pt.jnation.blockbuster.model.MovieReference;

import javax.inject.Inject;
import java.util.List;

@CommandLine.Command(name = "mprest", description = "Use the Microprofile REST Client")
public class RunWithMPRestClient implements Runnable {
    @Inject
    @RestClient
    MovieRestClient client;

    @CommandLine.Option(names = "-o", description = "The operation to execute")
    String operation;

    @Override
    public void run() {
        if (operation.equals("search")) {
            List<MovieReference> searchMovies = client.searchMovies("The Godfather");
            Utils.print(searchMovies);
        }
        if (operation.equals("get")) {
            Utils.print(client.getMovie("Top Gun: Maverick"));
        }
        if (operation.equals("mainActors")) {
            Utils.print(client.getCastMembers("Top Gun: Maverick").getActors().stream().limit(3).toList());
        }
    }

}