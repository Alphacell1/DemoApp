package hr.tomislavplaninic.demo.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Represents details of a game.
 */
public class GameDetails implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("background_image")
    private String backgroundImage;

    @SerializedName("description")
    private String description;

    @SerializedName("requirements")
    private Requirements requirements;

    @SerializedName("platforms")
    private List<Platform> platforms;

    /**
     * Get the list of platforms.
     *
     * @return The list of platforms.
     */
    public List<Platform> getPlatforms() {
        return platforms;
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
     * Set the name of the game.
     *
     * @param name The name of the game.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the background image URL of the game.
     *
     * @return The background image URL.
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Get the description of the game.
     *
     * @return The description of the game.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the requirements of the game.
     *
     * @return The requirements of the game.
     */
    public Requirements getRequirements() {
        return requirements;
    }

    /**
     * Set the requirements of the game.
     *
     * @param requirements The requirements of the game.
     */
    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }
}

