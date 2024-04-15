package hr.tomislavplaninic.demo.view_model;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hr.tomislavplaninic.demo.activities.VideoGameActivity;
import hr.tomislavplaninic.demo.communication.CommunicationHandler;
import hr.tomislavplaninic.demo.communication.GenreListener;
import hr.tomislavplaninic.demo.database.GenreRepository;
import hr.tomislavplaninic.demo.genre.Genre;
import hr.tomislavplaninic.demo.model.GridSpacingItemDecoration;

/**
 * ViewModel for managing genre selection and navigation.
 */
public class GenreSelectionViewModel extends ViewModel {

    private final MutableLiveData<List<Genre>> genreList = new MutableLiveData<>();
    private final MutableLiveData<List<Genre>> userSelectedGenres = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFabEnabled = new MutableLiveData<>(false);

    /**
     * Get live data for available genres.
     *
     * @return LiveData object containing the list of available genres.
     */
    public LiveData<List<Genre>> getGenreList() {
        return genreList;
    }

    /**
     * Get live data for enabling/disabling FAB.
     *
     * @return LiveData object indicating whether the FAB is enabled or disabled.
     */
    public LiveData<Boolean> isFabEnabled() {
        return isFabEnabled;
    }

    /**
     * Fetch available genres from the API.
     */
    public void fetchGenres() {
        CommunicationHandler communicationHandler = CommunicationHandler.getInstance("https://api.rawg.io");
        communicationHandler.fetchGenres(new GenreListener() {
            @Override
            public void onGenresReceived(List<Genre> genres) {
                genreList.setValue(genres);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }

    /**
     * Set user-selected genres and update FAB state accordingly.
     *
     * @param selectedGenres List of user-selected genres.
     */
    public void setUserSelectedGenres(List<Genre> selectedGenres) {
        userSelectedGenres.setValue(selectedGenres);
        isFabEnabled.setValue(!selectedGenres.isEmpty());
    }

    /**
     * Navigate to the VideoGameActivity.
     *
     * @param context Context of the calling activity.
     */
    public void navigateToVideoGameActivity(Context context) {
        GenreRepository genreRepository = new GenreRepository(context);
        genreRepository.insertGenres(userSelectedGenres.getValue());
        Intent intent = new Intent(context, VideoGameActivity.class);
        context.startActivity(intent);
    }
}
