package hr.tomislavplaninic.demo.genre;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a genre.
 */
@Entity(tableName = "genre")
public class Genre {

    @PrimaryKey
    private int id;
    private boolean isSelected = false;
    private String name;
    private String slug;
    private int games_count;
    private String image_background;

    /**
     * Default constructor.
     */
    public Genre() {
    }

    /**
     * Constructor with name parameter.
     *
     * @param name The name of the genre.
     */
    public Genre(String name) {
        this.name = name;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id               The ID of the genre.
     * @param name             The name of the genre.
     * @param slug             The slug of the genre.
     * @param games_count      The count of games associated with the genre.
     * @param image_background The image background of the genre.
     */
    public Genre(int id, String name, String slug, int games_count, String image_background) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.games_count = games_count;
        this.image_background = image_background;
    }

    /**
     * Get the ID of the genre.
     *
     * @return The ID of the genre.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the genre.
     *
     * @param id The ID of the genre.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Check if the genre is selected.
     *
     * @return True if the genre is selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Set whether the genre is selected.
     *
     * @param selected True to mark the genre as selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Get the name of the genre.
     *
     * @return The name of the genre.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the genre.
     *
     * @param name The name of the genre.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the slug of the genre.
     *
     * @return The slug of the genre.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Set the slug of the genre.
     *
     * @param slug The slug of the genre.
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Get the count of games associated with the genre.
     *
     * @return The count of games associated with the genre.
     */
    public int getGames_count() {
        return games_count;
    }

    /**
     * Set the count of games associated with the genre.
     *
     * @param games_count The count of games associated with the genre.
     */
    public void setGames_count(int games_count) {
        this.games_count = games_count;
    }

    /**
     * Get the image background of the genre.
     *
     * @return The image background of the genre.
     */
    public String getImage_background() {
        return image_background;
    }

    /**
     * Set the image background of the genre.
     *
     * @param image_background The image background of the genre.
     */
    public void setImage_background(String image_background) {
        this.image_background = image_background;
    }
}
