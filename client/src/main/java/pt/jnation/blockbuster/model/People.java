package pt.jnation.blockbuster.model;

import java.util.List;

public abstract class People {
    public List<Person> items;
    
    public People() {
    }

    public People(List<Person> items) {
        this.items = items;
    }

    public List<Person> getItems() {
        return items;
    }

    public void setItems(List<Person> items) {
        this.items = items;
    }
}
