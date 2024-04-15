package hr.tomislavplaninic.demo.view_model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.tomislavplaninic.demo.communication.CommunicationHandler;
import hr.tomislavplaninic.demo.database.GenreRepository;
import hr.tomislavplaninic.demo.dialog.GameOverviewDialog;
import hr.tomislavplaninic.demo.game.Game;
import hr.tomislavplaninic.demo.game.GameDetails;
import hr.tomislavplaninic.demo.game.GameDetailsListener;
import hr.tomislavplaninic.demo.game.GameListener;
import hr.tomislavplaninic.demo.game.Platform;
import hr.tomislavplaninic.demo.game.Requirements;
import hr.tomislavplaninic.demo.genre.Genre;

/**
 * ViewModel for managing video game-related data and logic.
 */
public class VideoGameViewModel extends ViewModel {
    private static final String TAG = VideoGameViewModel.class.getSimpleName();
    private CommunicationHandler communicationHandler;
    private GenreRepository genreRepository;

    private MutableLiveData<List<Genre>> genres;
    private MutableLiveData<List<Game>> games;
    private MutableLiveData<String> paginationText;

    private List<String> favoriteGameNames = new ArrayList<>();

    private int currentPage;
    private int totalPages;
    private static final int GAME_PER_PAGE_COUNT = 20;
    private String currentFilter;
    private String allGenresFilter;

    /**
     * Initializes the ViewModel.
     *
     * @param context Context of the calling activity.
     */
    public void init(Context context) {
        communicationHandler = CommunicationHandler.getInstance("https://api.rawg.io");
        genreRepository = new GenreRepository(context);

        genres = new MutableLiveData<>();
        games = new MutableLiveData<>();
        paginationText = new MutableLiveData<>();

        fetchGenres();
        currentPage = 1;
        fetchGamesWithFilter(allGenresFilter);
    }

    /**
     * Sets the list of favorite game names.
     *
     * @param favoriteGameNames List of favorite game names.
     */
    public void setFavoriteGameNames(List<String> favoriteGameNames) {
        this.favoriteGameNames = favoriteGameNames;
    }

    /**
     * Gets the LiveData object containing the list of genres.
     *
     * @return LiveData object containing the list of genres.
     */
    public LiveData<List<Genre>> getGenres() {
        return genres;
    }

    /**
     * Gets the LiveData object containing the list of games.
     *
     * @return LiveData object containing the list of games.
     */
    public LiveData<List<Game>> getGames() {
        return games;
    }

    /**
     * Gets the LiveData object containing pagination information.
     *
     * @return LiveData object containing pagination information.
     */
    public LiveData<String> getPaginationText() {
        return paginationText;
    }

    /**
     * Handles the click event for the previous button.
     */
    public void onPrevButtonClick() {
        if (!(currentPage > 1)) {
            return;
        }
        currentPage--;
        paginationText.setValue(String.format("Page %d of %d", currentPage, totalPages));
        fetchGamesWithFilter(currentFilter);
    }

    /**
     * Handles the click event for the next button.
     */
    public void onNextButtonClick() {
        if (!(currentPage < totalPages)) {
            return;
        }
        currentPage++;
        fetchGamesWithFilter(currentFilter);
    }

    /**
     * Handles the selection of a genre.
     *
     * @param position Position of the selected genre.
     */
    public void onGenreSelected(int position) {
        List<Genre> allGenres = genreRepository.getAllUserSelectedGenres();
        allGenresFilter = allGenres.stream().map(x -> x.getName().toLowerCase()).collect(Collectors.joining(","));
        currentFilter = allGenresFilter;

        if (position == 1) {
            fetchFavoriteGames(favoriteGameNames);
            return;
        } else if (position > 1) {
            currentFilter = allGenres.get(position - 2).getName().toLowerCase();
        }
        fetchGamesWithFilter(currentFilter);
    }

    /**
     * Fetches favorite games based on their names.
     *
     * @param gameNames List of favorite game names.
     */
    private void fetchFavoriteGames(List<String> gameNames) {
        communicationHandler.fetchSpecificGames(gameNames, new GameListener() {
            @Override
            public void onGamesReceived(List<Game> gameList, int count) {
                totalPages = (int) Math.ceil((double) count / GAME_PER_PAGE_COUNT);
                paginationText.setValue(String.format("Page %d of %d", currentPage, totalPages));
                gameList.forEach(x -> x.setFavorite(true));
                games.setValue(gameList);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, message);
            }
        });
    }

    /**
     * Handles the click event for displaying game information.
     *
     * @param game    Selected game.
     * @param context Context of the calling activity.
     */
    public void handleGameInfoClick(Game game, Context context) {
        communicationHandler.fetchGameDetails(String.valueOf(game.getId()), new GameDetailsListener() {
            @Override
            public void onReceived(GameDetails gameDetails) {
                Requirements wantedRequirements = null;
                for (Platform platform : gameDetails.getPlatforms()) {
                    Requirements requirements = platform.getRequirements();
                    if (platform.getRequirements().getMinimum() != null) {
                        wantedRequirements = requirements;
                    }
                }

                if (wantedRequirements == null) {
                    wantedRequirements = new Requirements("Not known", "Not known");
                }
                gameDetails.setRequirements(wantedRequirements);
                GameOverviewDialog gameOverviewDialog = new GameOverviewDialog(gameDetails);
                gameOverviewDialog.show(context);

            }

            @Override
            public void onError(String message) {
                Log.e(TAG, message);
            }
        });
    }

    /**
     * Fetches the list of genres.
     */
    private void fetchGenres() {
        genres.setValue(genreRepository.getAllUserSelectedGenres());
    }

    /**
     * Fetches games based on the provided filter.
     *
     * @param genreFilter Filter for fetching games.
     */
    private void fetchGamesWithFilter(String genreFilter) {
        communicationHandler.fetchGames(genreFilter, currentPage, GAME_PER_PAGE_COUNT, new GameListener() {
            @Override
            public void onGamesReceived(List<Game> gameList, int count) {
                totalPages = (int) Math.ceil((double) count / GAME_PER_PAGE_COUNT);
                paginationText.setValue(String.format("Page %d of %d", currentPage, totalPages));
                for (String name : favoriteGameNames) {
                    for (Game game : gameList) {
                        if (name.equals(game.getName())) {
                            game.setFavorite(!game.isFavorite());
                        }
                    }
                }
                games.setValue(gameList);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, message);
            }
        });
    }
}
