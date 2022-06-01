package pt.jnation.movie.model;

import java.net.URL;
import javax.persistence.Entity;

@Entity
public class Actor extends Person {
    public URL image;
    public String asCharacter;
}
