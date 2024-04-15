package hr.tomislavplaninic.demo.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hr.tomislavplaninic.demo.genre.Genre;

/**
 * Repository class for managing operations related to genres in the database.
 */
public class GenreRepository {
    private GenreDao genreDao;

    /**
     * Constructs a new GenreRepository instance.
     *
     * @param context The application context
     */
    public GenreRepository(Context context) {
        this.genreDao = GenreDatabase.getInstance(context).genreDao();
    }

    /**
     * Insert a list of genres into the database.
     *
     * @param genres The list of genres to insert
     */
    public void insertGenres(List<Genre> genres) {
        genreDao.insertGenres(genres);
    }

    /**
     * Insert a single genre into the database.
     *
     * @param genre The genre to insert
     */
    public void insertGenre(Genre genre) {
        genreDao.insertGenre(genre);
    }

    /**
     * Get all user-selected genres from the database.
     *
     * @return A list of user-selected genres
     */
    public List<Genre> getAllUserSelectedGenres() {
        return genreDao.getAllGenres();
    }

    /**
     * Update the genres in the database based on the provided list.
     *
     * @param genres The list of genres to update
     */
    public void updateGenres(List<Genre> genres) {
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName());
        }
        List<Genre> existingGenres = genreDao.getAllGenres();

        for (Genre existingGenre : existingGenres) {
            if (!genreNames.contains(existingGenre.getName())) {
                genreDao.deleteGenre(existingGenre);
            }
        }

        for (Genre genre : genres) {
            if (!existingGenres.contains(genre)) {
                genreDao.insertGenre(genre);
            }
        }
    }
}
