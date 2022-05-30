package pt.jnation.blockbuster.service;

import pt.jnation.blockbuster.model.MovieSearchResults;
import io.quarkus.cache.CacheResult;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.jnation.blockbuster.model.CastMembers;
import pt.jnation.blockbuster.model.Movie;
import pt.jnation.blockbuster.model.MovieSearchResult;

@ApplicationScoped
public class BlockbusterService {

    private final Client imdbClient = ClientBuilder.newBuilder().build();
    
    @ConfigProperty(name = "imdb.api.url")
    String imdbUrl;
    
    @ConfigProperty(name = "imdb.api.key")
    String imdbKey;
    
    @Inject 
    BlockbusterService blockbusterService;
    
    @CacheResult(cacheName = "movie")
    public Movie getMovie(String title){
        MovieSearchResults movieSearchResults = blockbusterService.searchMovies(title);
        if(movieSearchResults.hasResults()){
            MovieSearchResult movieSearchResult = movieSearchResults.getResult();
            return imdbClient.target(imdbUrl)
                .path("Title")
                .path(imdbKey)
                .path(movieSearchResult.getId())
                .request()
                .get(Movie.class);
        }
        return null;
    }

    @CacheResult(cacheName = "movies")
    public MovieSearchResults searchMovies(String keyword) {
        return imdbClient.target(imdbUrl)
                .path("SearchTitle")
                .path(imdbKey)
                .path(keyword)
                .request()
                .get(MovieSearchResults.class);
        
    }
    
    @CacheResult(cacheName = "cast")
    public CastMembers getCastMembers(String id){
        return imdbClient.target(imdbUrl)
                .path("FullCast")
                .path(imdbKey)
                .path(id)
                .request()
                .get(CastMembers.class);
    }
    
    
}
