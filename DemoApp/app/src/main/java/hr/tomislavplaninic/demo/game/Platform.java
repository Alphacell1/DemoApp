package hr.tomislavplaninic.demo.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Represents a platform on which a game is available.
 */
public class Platform implements Serializable {
    @SerializedName("platform")
    private PlatformInfo platformInfo;

    @SerializedName("released_at")
    private String releasedAt;

    @SerializedName("requirements")
    private Requirements requirements;

    /**
     * Get the platform information.
     *
     * @return The platform information.
     */
    public PlatformInfo getPlatformInfo() {
        return platformInfo;
    }

    /**
     * Get the release date of the game on this platform.
     *
     * @return The release date.
     */
    public String getReleasedAt() {
        return releasedAt;
    }

    /**
     * Get the requirements for the game on this platform.
     *
     * @return The requirements.
     */
    public Requirements getRequirements() {
        return requirements;
    }
}
