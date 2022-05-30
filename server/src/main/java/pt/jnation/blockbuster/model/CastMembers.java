package pt.jnation.blockbuster.model;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

public class CastMembers {
    private String imDbId;
    private Directors directors;
    private Writers writers;
    private List<Actor> actors;

    public CastMembers() {
    }

    public CastMembers(String imDbId, Directors directors, Writers writers, List<Actor> actors) {
        this.imDbId = imDbId;
        this.directors = directors;
        this.writers = writers;
        this.actors = actors;
    }

    public String getImDbId() {
        return imDbId;
    }

    public void setImDbId(String imDbId) {
        this.imDbId = imDbId;
    }

    public Directors getDirectors() {
        return directors;
    }

    public void setDirectors(Directors directors) {
        this.directors = directors;
    }

    public Writers getWriters() {
        return writers;
    }

    public void setWriters(Writers writers) {
        this.writers = writers;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.imDbId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CastMembers other = (CastMembers) obj;
        return Objects.equals(this.imDbId, other.imDbId);
    }
    
    
}
