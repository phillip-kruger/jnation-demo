package pt.jnation.movie.service.storage;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import pt.jnation.movie.config.Storage;
import pt.jnation.movie.model.Actor;
import pt.jnation.movie.model.CastMembers;
import pt.jnation.movie.model.Company;
import pt.jnation.movie.model.Movie;
import pt.jnation.movie.model.MovieRatings;
import pt.jnation.movie.model.MovieReferences;

@ApplicationScoped
@MovieStorage(Storage.db)
public class MovieDB implements MovieStorable {
    
    @Override
    public MovieReferences searchMovies(String keyword) {
        return MovieReferences.findById(keyword);
    }
    
    @Override
    public Movie getMovieById(String id){
        return Movie.findById(id);
    }
    
    @Override
    public CastMembers getCastMembers(String id){
        return CastMembers.findById(id);
    }
    
    @Override
    public MovieRatings getMovieRatings(String id){
        return MovieRatings.findById(id);
    }

    @Transactional
    @Override
    public MovieReferences newMovieReferences(MovieReferences movieReferences) {
        if(movieReferences!=null){
            movieReferences.persist();
        }
        return movieReferences;
    }

    @Transactional
    @Override
    public Movie newMovie(Movie movie) {
        Set<Company> emCompanyList = new HashSet<>();
        for(Company c:movie.companyList){
            Company existingCompany = Company.findById(c.id);
            if(existingCompany==null){
                c.persist();
            }else{
                c = existingCompany;
            }
            emCompanyList.add(c);
        }
        movie.companyList = emCompanyList;
        
        movie.persist();
        return movie;
    }

    @Transactional
    @Override
    public CastMembers newCastMembers(CastMembers castMembers) {
        if(castMembers!=null){
            
            LinkedHashSet<Actor> emActors = new LinkedHashSet<>();
            for(Actor a:castMembers.actors){
                Actor existingActor = Actor.findById(a.id);
                if(existingActor==null){
                    a.persist();
                }else{
                    a = existingActor;
                }
                emActors.add(a);
            }
            castMembers.actors = emActors.toArray(new Actor[]{});
            
            castMembers.persist();
        }
        return castMembers;
    }

    @Transactional
    @Override
    public MovieRatings newMovieRatings(MovieRatings movieRatings) {
        if(movieRatings!=null){
            movieRatings.persist();
        }
        return movieRatings;
    }

    @Override
    public MovieRatings updateMovieRatings(MovieRatings movieRatings) {
        if(movieRatings!=null){
            movieRatings.persist();
        }
        return movieRatings;
    }
}
