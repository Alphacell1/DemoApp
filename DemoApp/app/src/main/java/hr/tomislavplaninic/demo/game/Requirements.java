package hr.tomislavplaninic.demo.game;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Represents the requirements for a game.
 */
public class Requirements implements Serializable {

    @SerializedName("minimum")
    private String minimum;

    @SerializedName("recommended")
    private String recommended;

    /**
     * Constructs a new Requirements object with minimum and recommended requirements.
     *
     * @param minimum     The minimum requirements.
     * @param recommended The recommended requirements.
     */
    public Requirements(String minimum, String recommended) {
        this.minimum = minimum;
        this.recommended = recommended;
    }

    /**
     * Get the minimum requirements.
     *
     * @return The minimum requirements.
     */
    public String getMinimum() {
        return minimum;
    }

    /**
     * Get the recommended requirements.
     *
     * @return The recommended requirements.
     */
    public String getRecommended() {
        return recommended;
    }

}
