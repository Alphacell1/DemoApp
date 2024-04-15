package hr.tomislavplaninic.demo.game;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents the response containing a list of games.
 */
public class GameResponse {

    private int count;
    private String next;
    private String previous;

    @SerializedName("results")
    private List<Game> results;

    /**
     * Get the list of games.
     *
     * @return The list of games.
     */
    public List<Game> getResults() {
        return results;
    }

    /**
     * Get the total count of games in the response.
     *
     * @return The total count of games.
     */
    public int getCount() {
        return count;
    }

    /**
     * Set the list of games.
     *
     * @param results The list of games.
     */
    public void setResults(List<Game> results) {
        this.results = results;
    }

    /**
     * Get the URL for the next page of results.
     *
     * @return The URL for the next page.
     */
    public String getNext() {
        return next;
    }

    /**
     * Get the URL for the previous page of results.
     *
     * @return The URL for the previous page.
     */
    public String getPrevious() {
        return previous;
    }


}
