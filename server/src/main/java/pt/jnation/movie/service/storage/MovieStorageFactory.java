package pt.jnation.movie.service.storage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.jnation.movie.Storage;

@ApplicationScoped
public class MovieStorageFactory {
    
    @ConfigProperty(defaultValue = "file")
    Storage storage;
    
    @Inject @Any
    Instance<MovieStorable> movieStorables;

    public MovieStorable getMovieStorable() {
        Instance<MovieStorable> instance = movieStorables.select(new MovieStorageLiteral(storage));
        if(!instance.isUnsatisfied()){
            MovieStorable provider = instance.get();
            return provider;
        }
        return new MovieIMDB(); // Default to read only imdb
    }
    
}
