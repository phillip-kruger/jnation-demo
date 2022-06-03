package pt.jnation.movie.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import pt.jnation.movie.graphql.config.Storage;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieRatings;
import pt.jnation.movie.model.MovieReferences;

@ApplicationScoped
@MovieStorage(Storage.file)
public class MovieFile implements MovieStorable {
    private static final String DATA = "data";
    
    @PostConstruct
    public void init(){
        try {
            Files.createDirectories(Paths.get(DATA));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public MovieReferences searchMovies(String keyword) {
        String name = getMovieReferencesKey(keyword) + ".json";
        Path path = Paths.get(DATA,name);
        if(Files.exists(path)){
            try {
                String json = Files.readString(path);
                return toMovieReferences(json);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
    
    @Override
    public Movie getMovieById(String id){
        String name = getMovieKey(id) + ".json";
        Path path = Paths.get(DATA,name);
        if(Files.exists(path)){
            try {
                String json = Files.readString(path);
                return toMovie(json);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
    
    @Override
    public CastMembers getCastMembers(String id){
        String name = getCastMembersKey(id) + ".json";
        Path path = Paths.get(DATA,name);
        if(Files.exists(path)){
            try {
                String json = Files.readString(path);
                return toCastMembers(json);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
    
    @Override
    public MovieRatings getMovieRatings(String id){
        String name = getMovieRatingsKey(id) + ".json";
        Path path = Paths.get(DATA,name);
        if(Files.exists(path)){
            try {
                String json = Files.readString(path);
                return toMovieRatings(json);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    @Override
    public MovieReferences newMovieReferences(MovieReferences movieReferences) {
        String name = getMovieReferencesKey(movieReferences) + ".json";
        Path path = Paths.get(DATA, name);
        try {
            String json = toString(movieReferences);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.writeString(path, json);
            return movieReferences;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Movie newMovie(Movie movie) {
        String name = getMovieKey(movie) + ".json";
        Path path = Paths.get(DATA, name);
        try {
            String json = toString(movie);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.writeString(path, json);
            return movie;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public CastMembers newCastMembers(CastMembers castMembers) {
        String name = getCastMembersKey(castMembers) + ".json";
        Path path = Paths.get(DATA, name);
        try {
            String json = toString(castMembers);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.writeString(path, json);
            return castMembers;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public MovieRatings newMovieRatings(MovieRatings movieRatings) {
        String name = getMovieRatingsKey(movieRatings) + ".json";
        Path path = Paths.get(DATA, name);
        try {
            String json = toString(movieRatings);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.writeString(path, json);
            return movieRatings;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }   

    @Override
    public MovieRatings updateMovieRatings(MovieRatings movieRatings) {
        String name = getMovieRatingsKey(movieRatings) + ".json";
        Path path = Paths.get(DATA, name);
        try {
            String json = toString(movieRatings);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.writeString(path, json);
            return movieRatings;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}