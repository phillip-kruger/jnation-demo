package pt.jnation.movie.service.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieRatings;
import pt.jnation.movie.model.MovieReferences;

public interface MovieStorable {
    public MovieReferences searchMovies(String keyword);
    public Movie getMovieById(String id);
    public CastMembers getCastMembers(String id);
    public MovieRatings getMovieRatings(String id);
    public MovieReferences newMovieReferences(MovieReferences movieReferences);
    public Movie newMovie(Movie movie);
    public CastMembers newCastMembers(CastMembers castMembers);
    public MovieRatings newMovieRatings(MovieRatings movieRatings);
    public MovieRatings updateMovieRatings(MovieRatings movieRatings);
    
    default String toString(MovieReferences movieReferences) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(movieReferences);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default MovieReferences toMovieReferences(String json){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, MovieReferences.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default String toString(Movie movie){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(movie);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default Movie toMovie(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Movie.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default String toString(CastMembers castMembers){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(castMembers);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default CastMembers toCastMembers(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, CastMembers.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default String toString(MovieRatings movieRatings){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(movieRatings);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default MovieRatings toMovieRatings(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, MovieRatings.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default String getMovieReferencesKey(MovieReferences movieReferences){
        return getMovieReferencesKey(movieReferences.keyword);
    }
    
    default String getMovieReferencesKey(String keyword){
        return "MovieReferences_" + keyword.hashCode();
    }
    
    default String getMovieKey(Movie movie){
        return getMovieKey(movie.id);
    }
    
    default String getMovieKey(String id){
        return "Movie_" + id;
    }
    
    default String getCastMembersKey(CastMembers castMembers){
        return getCastMembersKey(castMembers.imDbId);
    }
    
    default String getCastMembersKey(String id){
        return "CastMembers_" + id;
    }
    
    default String getMovieRatingsKey(MovieRatings movieRatings){
        return getMovieRatingsKey(movieRatings.id);
    }
    
    default String getMovieRatingsKey(String id){
        return "MovieRatings_" + id;
    }

    
 
}
