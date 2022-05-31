package pt.jnation.blockbuster.model;

import java.util.List;

public class MovieSearchResults {
    private List<MovieSearchResult> results;
    public MovieSearchResults() {
    }
    
    public MovieSearchResults(List<MovieSearchResult> results) {
        this.results = results;
    }

    public List<MovieSearchResult> getResults() {
        return results;
    }

    public void setResults(List<MovieSearchResult> results) {
        this.results = results;
    }

    public boolean hasResults(){
        return results!=null && !results.isEmpty();
    }
    
    public MovieSearchResult getResult(){
        if(hasResults()){
            return results.get(0);
        }
        return null;
    }
    
}
