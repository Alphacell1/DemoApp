package hr.tomislavplaninic.demo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import hr.tomislavplaninic.demo.genre.Genre;

/**
 * Room Database class for managing Genre entities.
 */
@Database(entities = {Genre.class}, version = 1)
public abstract class GenreDatabase extends RoomDatabase {
    public abstract GenreDao genreDao();

    private static volatile GenreDatabase INSTANCE;

    /**
     * Get an instance of the GenreDatabase.
     *
     * @param context The application context
     * @return An instance of GenreDatabase
     */
    public static synchronized GenreDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GenreDatabase.class, "genre_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
