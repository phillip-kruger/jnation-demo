package pt.jnation.blockbuster.graphql;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.graphql.client.typesafe.api.NestedParameter;
import org.eclipse.microprofile.graphql.Query;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieCastMembersOnly;

@GraphQLClientApi(configKey = "movies-typesafe")
public interface MovieGraphQLTypesafeClient {

    @Query
    Movie getMovie(String title);

    @Query("movie")
    MovieCastMembersOnly getMainActors(String title, @NestedParameter({"castMembers.mainActors"}) int limit);

}
