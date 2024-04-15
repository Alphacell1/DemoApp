package hr.tomislavplaninic.demo.communication;

import hr.tomislavplaninic.demo.game.GameDetails;
import hr.tomislavplaninic.demo.game.GameResponse;
import hr.tomislavplaninic.demo.genre.GenreResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for defining communication endpoints with the API.
 */
public interface CommunicationApiService {

    /**
     * Endpoint to retrieve genres from the API.
     * @param apiKey The API key for authorization.
     * @return A Call object for executing the API request.
     */
    @GET("/api/genres")
    Call<GenreResponse> getGenres(@Query("key") String apiKey);

    /**
     * Endpoint to retrieve games by genres from the API.
     * @param genres Comma-separated list of genre IDs.
     * @param apiKey The API key for authorization.
     * @param page The page number of the result set.
     * @param pageSize The number of results per page.
     * @return A Call object for executing the API request.
     */
    @GET("/api/games")
    Call<GameResponse> getGamesByGenres(
            @Query("genres") String genres,
            @Query("key") String apiKey,
            @Query("page") int page,
            @Query("page_size") int pageSize
    );

    /**
     * Endpoint to retrieve details of a specific game from the API.
     * @param id The ID of the game.
     * @param apiKey The API key for authorization.
     * @return A Call object for executing the API request.
     */
    @GET("/api/games/{id}")
    Call<GameDetails> getGameDetails(@Path("id") String id, @Query("key") String apiKey);

    /**
     * Endpoint to retrieve specific games from the API.
     * @param apiKey The API key for authorization.
     * @param gameNames The name of the game to search for.
     * @param searchPrecise Whether to perform a precise search.
     * @return A Call object for executing the API request.
     */
    @GET("/api/games")
    Call<GameResponse> getSpecificGames(
            @Query("key") String apiKey,
            @Query("search") String gameNames,
            @Query("search_precise") boolean searchPrecise
    );

}
