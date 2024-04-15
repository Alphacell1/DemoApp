package hr.tomislavplaninic.demo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import hr.tomislavplaninic.demo.genre.Genre;

/**
 * Data Access Object (DAO) for interacting with Genre entities in the database.
 */
@Dao
public interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenre(Genre genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenres(List<Genre> genres);

    @Query("SELECT * FROM genre")
    List<Genre> getAllGenres();

    @Delete
    void deleteGenre(Genre genre);
}
