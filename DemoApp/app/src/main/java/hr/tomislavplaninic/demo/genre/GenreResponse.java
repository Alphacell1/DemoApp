package hr.tomislavplaninic.demo.genre;

import java.util.List;

/**
 * Represents the response containing genre data.
 */
public class GenreResponse {
    private int count;
    private String next;
    private String previous;
    private List<Genre> results;

    /**
     * Constructs a GenreResponse object with the specified parameters.
     *
     * @param count    The total count of genres.
     * @param next     The URL for the next page of genres.
     * @param previous The URL for the previous page of genres.
     * @param results  The list of genres.
     */
    public GenreResponse(int count, String next, String previous, List<Genre> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    /**
     * Gets the total count of genres.
     *
     * @return The total count of genres.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the total count of genres.
     *
     * @param count The total count of genres.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the URL for the next page of genres.
     *
     * @return The URL for the next page of genres.
     */
    public String getNext() {
        return next;
    }

    /**
     * Sets the URL for the next page of genres.
     *
     * @param next The URL for the next page of genres.
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * Gets the URL for the previous page of genres.
     *
     * @return The URL for the previous page of genres.
     */
    public String getPrevious() {
        return previous;
    }

    /**
     * Sets the URL for the previous page of genres.
     *
     * @param previous The URL for the previous page of genres.
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    /**
     * Gets the list of genres.
     *
     * @return The list of genres.
     */
    public List<Genre> getResults() {
        return results;
    }

    /**
     * Sets the list of genres.
     *
     * @param results The list of genres.
     */
    public void setResults(List<Genre> results) {
        this.results = results;
    }
}
