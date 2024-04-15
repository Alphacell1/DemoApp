package hr.tomislavplaninic.demo.game;

import java.util.List;

/**
 * Interface definition for a callback to be invoked when game data is received or an error occurs.
 */
public interface GameListener {
    /**
     * Called when the game data is received successfully.
     *
     * @param gameList The list of games received.
     * @param count     The count of games received.
     */
    void onGamesReceived(List<Game> gameList, int count);

    /**
     * Called when an error occurs while fetching game data.
     *
     * @param message The error message describing the issue.
     */
    void onError(String message);
}
