package hr.tomislavplaninic.demo.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Represents a game.
 */
public class Game implements Serializable {
    @SerializedName("id")
    private int id;

    private boolean isFavorite;

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    @SerializedName("released")
    private Date released;

    @SerializedName("background_image")
    private String backgroundImage;

    @SerializedName("rating")
    private double rating;

    @SerializedName("platforms")
    private List<Platform> platforms;

    /**
     * Get the slug of the game.
     *
     * @return The slug of the game.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Get the name of the game.
     *
     * @return The name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the release date of the game.
     *
     * @return The release date of the game.
     */
    public Date getReleased() {
        return released;
    }

    /**
     * Get the URL of the background image of the game.
     *
     * @return The URL of the background image.
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Get the rating of the game.
     *
     * @return The rating of the game.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Set whether the game is favorite.
     *
     * @param favorite True if the game is favorite, false otherwise.
     */
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    /**
     * Get the ID of the game.
     *
     * @return The ID of the game.
     */
    public int getId() {
        return id;
    }

    /**
     * Check if the game is favorite.
     *
     * @return True if the game is favorite, false otherwise.
     */
    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Get the platforms supported by the game.
     *
     * @return The list of platforms supported by the game.
     */
    public List<Platform> getPlatforms() {
        return platforms;
    }

}
