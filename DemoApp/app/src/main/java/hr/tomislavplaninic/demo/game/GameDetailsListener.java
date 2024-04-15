package hr.tomislavplaninic.demo.game;

/**
 * Interface definition for a callback to be invoked when game details are received or an error occurs.
 */
public interface GameDetailsListener {
    /**
     * Called when the game details are received successfully.
     *
     * @param gameDetails The details of the game.
     */
    void onReceived(GameDetails gameDetails);

    /**
     * Called when an error occurs while fetching the game details.
     *
     * @param message The error message describing the issue.
     */
    void onError(String message);
}
