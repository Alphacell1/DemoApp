package hr.tomislavplaninic.demo.game;

import com.google.gson.annotations.SerializedName;

/**
 * Represents information about a platform.
 */
public class PlatformInfo {
    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    /**
     * Get the ID of the platform.
     *
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the slug of the platform.
     *
     * @return The slug.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Get the name of the platform.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }
}
