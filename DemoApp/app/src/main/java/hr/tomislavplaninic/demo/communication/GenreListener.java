package hr.tomislavplaninic.demo.communication;

import java.util.List;

import hr.tomislavplaninic.demo.genre.Genre;

/**
 * Interface for receiving genre data or error from an API call.
 */
public interface GenreListener {

    /**
     * Called when genre data is successfully received.
     * @param genreList The list of Genre objects received from the API.
     */
    void onGenresReceived(List<Genre> genreList);

    /**
     * Called when an error occurs during the API call.
     * @param error The error message describing the issue.
     */
    void onError(String error);
}
