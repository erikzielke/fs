package dk.erikzielke.falconsocialassignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MovieSearchResults {
    private int page;
    private int totalPages;
    private int totalResults;

    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
