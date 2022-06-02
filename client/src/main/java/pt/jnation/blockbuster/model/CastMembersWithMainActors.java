package pt.jnation.blockbuster.model;

import java.util.List;
import java.util.Objects;

public class CastMembersWithMainActors {
    private String imDbId;
    private Directors directors;
    private Writers writers;
    private List<Actor> actors;

    // !!! this is a source field on the server side
    private List<Actor> mainActors;

    public CastMembersWithMainActors() {
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

    public List<Actor> getMainActors() {
        return mainActors;
    }

    public void setMainActors(List<Actor> mainActors) {
        this.mainActors = mainActors;
    }

}
