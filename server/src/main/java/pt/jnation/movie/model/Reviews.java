package pt.jnation.movie.model;

import java.util.List;
import java.util.Objects;

public class Reviews {

    private String imDbId;
    private List<Review> items;

    public Reviews() {
    }

    public Reviews(String imDbId, List<Review> items) {
        this.imDbId = imDbId;
        this.items = items;
    }

    public String getImDbId() {
        return imDbId;
    }

    public void setImDbId(String imDbId) {
        this.imDbId = imDbId;
    }

    public List<Review> getItems() {
        return items;
    }

    public void setItems(List<Review> items) {
        this.items = items;
    }

    public Review getReview(){
        if(this.items!=null && !this.items.isEmpty()){
            return this.items.get(0);
        }
        return null;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.imDbId);
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
        final Reviews other = (Reviews) obj;
        return Objects.equals(this.imDbId, other.imDbId);
    }

}
