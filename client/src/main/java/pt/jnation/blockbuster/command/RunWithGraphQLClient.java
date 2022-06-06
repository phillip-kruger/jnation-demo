package pt.jnation.blockbuster.command;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import picocli.CommandLine;
import pt.jnation.blockbuster.Utils;
import pt.jnation.blockbuster.graphql.MovieGraphQLTypesafeClient;

import javax.inject.Inject;

import java.io.IOException;

import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@CommandLine.Command(name = "graphql", description = "Use the JAXRS REST Client")
public class RunWithGraphQLClient implements Runnable {

    @CommandLine.Option(names = "-o", description = "The operation to execute")
    String operation;

    @Inject
    @GraphQLClient("movies-typesafe")
    MovieGraphQLTypesafeClient typesafeClient;

    @Inject
    @GraphQLClient("movies-dynamic")
    DynamicGraphQLClient dynamicClient;

    @Override
    public void run() {
        try {
                /*  {
                        searchMovies(keyword:"$keyword") {
                            id
                            title
                        }
                    }
                    */
            if (operation.equals("search")) {
                Document document = document(
                    operation(
                        field("searchMovies", args(arg("keyword", "The Godfather")),
                            field("id"),
                            field("title")
                        )
                    ));
                Response response = dynamicClient.executeSync(document);
                System.out.println(response.getData().toString());
            }
                /*  {
                        movie(title: "$title") {
                            year
                            image
                            // ...
                         }
                    }
                    */
            if (operation.equals("get")) {
                Utils.print(typesafeClient.getMovie("Top Gun: Maverick"));
            }
            /* {
                    movie(title:"$title") {
                        castMembers {
                            mainActors(limit: $limit) {
                                name
                            }
                        }
                    }
                }
            */
            if (operation.equals("mainActors")) {
                Utils.print(typesafeClient.getMainActors("Top Gun: Maverick", 3).getCastMembers().getMainActors());
            }
            /*  subscription {
                    listenForRateChanges {
                        id
                        quarkus
                        metacritic
                    }
                }
             */
            if (operation.equals("listen")) {
                Document sub = document(
                    operation(OperationType.SUBSCRIPTION,
                        field("listenForRateChanges",
                            field("id"),
                            field("quarkus"),
                            field("metacritic")
                        ))
                );
                dynamicClient.subscription(sub)
                    .subscribe()
                    .with(item -> {
                        System.out.println("NEW RATINGS: " + item.getData());
                    });
                waitForExit();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForExit() {
        try {
            System.in.read();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
