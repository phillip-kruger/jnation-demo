package pt.jnation.blockbuster.model;

import java.net.URL;

public class Actor extends Person {
    private String asCharacter;

    public Actor() {
    }

    public Actor(String id, String name, URL image, String asCharacter) {
        super(id, name);
        this.asCharacter = asCharacter;
    }
    
    public String getAsCharacter() {
        return asCharacter;
    }

    public void setAsCharacter(String asCharacter) {
        this.asCharacter = asCharacter;
    }
}
