package pt.jnation.blockbuster.model;

import java.util.Objects;

public class MovieSearchResult {

    public String id;
    public String title;
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final MovieSearchResult other = (MovieSearchResult) obj;
        return Objects.equals(this.id, other.id);
    }
}
