package pt.jnation.movie.service.storage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import pt.jnation.movie.Storage;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MovieStorage {
    Storage value();
}

class MovieStorageLiteral extends AnnotationLiteral<MovieStorage> implements MovieStorage {

    private final Storage storage;

    public MovieStorageLiteral(Storage storage){
        this.storage = storage;
    }
    
    @Override
    public Storage value() {
        return this.storage;
    }
}