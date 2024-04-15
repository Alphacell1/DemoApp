package hr.tomislavplaninic.demo.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hr.tomislavplaninic.demo.communication.CommunicationHandler;
import hr.tomislavplaninic.demo.communication.GenreListener;
import hr.tomislavplaninic.demo.database.GenreRepository;
import hr.tomislavplaninic.demo.genre.Genre;

/**
 * ViewModel for managing settings-related data and logic.
 */
public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<List<Genre>> genresLiveData = new MutableLiveData<>();
    private GenreRepository genreRepository;
    private CommunicationHandler communicationHandler;

    /**
     * Get the LiveData containing the list of genres.
     *
     * @param context Context of the calling activity.
     * @return LiveData<List<Genre>> representing the list of genres.
     */
    public LiveData<List<Genre>> getGenres(Context context) {
        if (genreRepository == null) {
            genreRepository = new GenreRepository(context);
        }
        if (genresLiveData.getValue() == null) {
            fetchGenres();
        }
        return genresLiveData;
    }

    /**
     * Fetches the list of genres from the API.
     */
    public void fetchGenres() {
        if (communicationHandler == null) {
            communicationHandler = CommunicationHandler.getInstance("https://api.rawg.io");
        }

        communicationHandler.fetchGenres(new GenreListener() {
            @Override
            public void onGenresReceived(List<Genre> genreList) {
                // Update the list with selected genres
                List<Genre> userSelectedGenres = genreRepository.getAllUserSelectedGenres();
                for (Genre userSelectedGenre : userSelectedGenres) {
                    for (Genre genre : genreList) {
                        if (userSelectedGenre.getName().equals(genre.getName())) {
                            genre.setSelected(true);
                            break;
                        }
                    }
                }
                genresLiveData.postValue(genreList);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }

    /**
     * Updates the list of selected genres in the database.
     *
     * @param selectedGenres The list of selected genres to be updated.
     */
    public void updateSelectedGenres(List<Genre> selectedGenres) {
        genreRepository.updateGenres(selectedGenres);
    }
}
