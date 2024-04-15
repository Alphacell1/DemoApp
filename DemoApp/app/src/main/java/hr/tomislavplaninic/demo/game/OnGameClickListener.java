package hr.tomislavplaninic.demo.game;

/**
 * Interface definition for a callback to be invoked when a game item is clicked or when
 * the favorite game button is clicked.
 */
public interface OnGameClickListener {
    /**
     * Called when a game item is clicked.
     *
     * @param game The game that was clicked.
     */
    void gameClicked(Game game);

    /**
     * Called when the favorite game button is clicked.
     *
     * @param game The game for which the favorite button was clicked.
     */
    void favoriteGameButtonClicked(Game game);
}
