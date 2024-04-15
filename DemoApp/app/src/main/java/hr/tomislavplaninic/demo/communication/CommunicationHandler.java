package hr.tomislavplaninic.demo.communication;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import hr.tomislavplaninic.demo.BuildConfig;
import hr.tomislavplaninic.demo.game.Game;
import hr.tomislavplaninic.demo.game.GameDetails;
import hr.tomislavplaninic.demo.game.GameDetailsListener;
import hr.tomislavplaninic.demo.game.GameResponse;
import hr.tomislavplaninic.demo.game.GameListener;
import hr.tomislavplaninic.demo.genre.Genre;
import hr.tomislavplaninic.demo.genre.GenreResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Handles communication with the API.
 */
public class CommunicationHandler {
    private static final String TAG = CommunicationHandler.class.getSimpleName();
    private static CommunicationHandler instance;
    private final String apiKey = BuildConfig.RAWG_API_KEY;
    private final CommunicationApiService apiService;


    private CommunicationHandler(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RetryInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(CommunicationApiService.class);
    }

    /**
     * Retrieves the singleton instance of CommunicationHandler.
     *
     * @param baseUrl The base URL of the API.
     * @return The CommunicationHandler instance.
     */
    public static synchronized CommunicationHandler getInstance(String baseUrl) {
        if (instance == null) {
            instance = new CommunicationHandler(baseUrl);
        }
        return instance;
    }

    /**
     * Fetches specific games from the API based on given game names.
     *
     * @param gameNames    The list of game names to fetch.
     * @param gameListener The listener for game retrieval events.
     */
    public void fetchSpecificGames(List<String> gameNames, GameListener gameListener) {
        List<Game> favoritedGames = new ArrayList<>();

        AtomicInteger requestCounter = new AtomicInteger(0);

        for (String gameName : gameNames) {
            Call<GameResponse> call = apiService.getSpecificGames(apiKey, gameName, true);
            call.enqueue(new Callback<GameResponse>() {
                @Override
                public void onResponse(@NonNull Call<GameResponse> call, @NonNull Response<GameResponse> response) {
                    if (response.isSuccessful()) {
                        GameResponse gameResponse = response.body();
                        if (gameResponse == null) {
                            Log.e(TAG, "Game response is null");
                            return;
                        }
                        if (gameResponse.getResults() == null) {
                            Log.e(TAG, "Game results is null");
                            return;
                        }
                        List<Game> games = gameResponse.getResults();

                        if (games != null) {
                            favoritedGames.add(games.get(0));
                        }
                    }

                    if (requestCounter.incrementAndGet() == gameNames.size()) {
                        gameListener.onGamesReceived(favoritedGames, favoritedGames.size());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GameResponse> call, @NonNull Throwable throwable) {
                    Log.e(TAG, Objects.requireNonNull(throwable.getMessage()));
                    if (requestCounter.incrementAndGet() == gameNames.size()) {
                        gameListener.onGamesReceived(favoritedGames, favoritedGames.size());
                    }
                }
            });
        }
    }

    /**
     * Fetches games from the API based on genres.
     *
     * @param genres       The genres to filter the games.
     * @param currentPage  The current page of the results.
     * @param pageSize     The size of each page.
     * @param gameListener The listener for game retrieval events.
     */
    public void fetchGames(String genres, int currentPage, int pageSize, GameListener gameListener) {
        Call<GameResponse> call = apiService.getGamesByGenres(genres, apiKey, currentPage, pageSize);
        call.enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(@NonNull Call<GameResponse> call, @NonNull Response<GameResponse> response) {
                if (!response.isSuccessful()) {
                    gameListener.onError("Couldn't fetch games " + response.toString());
                    return;
                }
                GameResponse gameResponse = response.body();

                List<Game> games = gameResponse.getResults();

                if (games == null) {
                    gameListener.onError("Games list is null");
                    return;
                }
                gameListener.onGamesReceived(games, gameResponse.getCount());
            }

            @Override
            public void onFailure(@NonNull Call<GameResponse> call, @NonNull Throwable throwable) {
                Log.e(TAG, Objects.requireNonNull(throwable.getMessage()));
                gameListener.onError(throwable.getMessage());
            }
        });
    }

    /**
     * Fetches details of a game from the API.
     *
     * @param id                  The ID of the game to fetch details for.
     * @param gameDetailsListener The listener for game details retrieval events.
     */
    public void fetchGameDetails(String id, GameDetailsListener gameDetailsListener) {
        Call<GameDetails> call = apiService.getGameDetails(id, apiKey);
        call.enqueue(new Callback<GameDetails>() {
            @Override
            public void onResponse(@NonNull Call<GameDetails> call, @NonNull Response<GameDetails> response) {
                if (!response.isSuccessful()) {
                    gameDetailsListener.onError("Couldn't fetch games " + response.toString());
                    return;
                }
                GameDetails details = response.body();

                if (details == null) {
                    gameDetailsListener.onError("Games list is null");
                    return;
                }
                gameDetailsListener.onReceived(details);
            }

            @Override
            public void onFailure(@NonNull Call<GameDetails> call, @NonNull Throwable throwable) {
                Log.e(TAG, Objects.requireNonNull(throwable.getMessage()));
                gameDetailsListener.onError(throwable.getMessage());
            }
        });
    }

    /**
     * Fetches genres from the API.
     *
     * @param genreListener The listener for genre retrieval events.
     */
    public void fetchGenres(GenreListener genreListener) {
        Call<GenreResponse> call = apiService.getGenres(apiKey);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (!response.isSuccessful()) {
                    genreListener.onError("Response isn't successful");
                    return;
                }
                GenreResponse genreResponse = response.body();
                if (genreResponse == null) {
                    genreListener.onError("Genre response is null");
                    return;
                }
                genreListener.onGenresReceived(genreResponse.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable throwable) {
                genreListener.onError(throwable.getMessage());
            }
        });
    }
}
