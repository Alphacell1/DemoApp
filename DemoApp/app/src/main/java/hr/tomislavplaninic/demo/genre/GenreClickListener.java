package hr.tomislavplaninic.demo.genre;

import java.util.List;

/**
 * Interface definition for a callback to be invoked when a genre is clicked.
 */
public interface GenreClickListener {

    /**
     * Called when a genre is clicked.
     *
     * @param selectedGenres The list of selected genres.
     */
    void onClicked(List<Genre> selectedGenres);
}
