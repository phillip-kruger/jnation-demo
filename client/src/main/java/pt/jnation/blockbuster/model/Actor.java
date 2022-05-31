package pt.jnation.blockbuster.model;

import java.net.URL;

public class Actor extends Person {
    private URL image;
    private String asCharacter;

    public Actor() {
    }

    public Actor(String id, String name, URL image, String asCharacter) {
        super(id, name);
        this.image = image;
        this.asCharacter = asCharacter;
    }
    
    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }
    
    public String getAsCharacter() {
        return asCharacter;
    }

    public void setAsCharacter(String asCharacter) {
        this.asCharacter = asCharacter;
    }
}
