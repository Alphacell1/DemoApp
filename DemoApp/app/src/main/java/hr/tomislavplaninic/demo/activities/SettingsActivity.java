package hr.tomislavplaninic.demo.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.adapter.GenreAdapter;
import hr.tomislavplaninic.demo.genre.Genre;
import hr.tomislavplaninic.demo.genre.GenreClickListener;
import hr.tomislavplaninic.demo.model.GridSpacingItemDecoration;
import hr.tomislavplaninic.demo.view_model.SettingsViewModel;

/**
 * Activity for settings.
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * View model
     */
    private SettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // Set up click listener for video game tab
        LinearLayout videoGameTab = findViewById(R.id.settings_game_tab);
        videoGameTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to video game activity
                startActivity(new Intent(SettingsActivity.this, VideoGameActivity.class));
                overridePendingTransition(0, 0); // Disable transition animation
                finish(); // Finish current activity
            }
        });

        // Handle back press to navigate to video game activity
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(SettingsActivity.this, VideoGameActivity.class));
                overridePendingTransition(0, 0); // Disable transition animation
                finish(); // Finish current activity
            }
        });

        // Set up genre RecyclerView
        RecyclerView genreView = findViewById(R.id.settings_genre_select);
        GenreAdapter genreAdapter = new GenreAdapter(this, new GenreClickListener() {
            @Override
            public void onClicked(List<Genre> selectedGenres) {
                // Update selected genres in ViewModel
                viewModel.updateSelectedGenres(selectedGenres);
            }
        });

        // Observe genres from ViewModel
        viewModel.getGenres(this).observe(this, genres -> {
            // Update genre adapter
            genreAdapter.setGenres(genres);
            int spacing = 5; // spacing between grid items
            genreView.addItemDecoration(new GridSpacingItemDecoration(spacing, 4, Color.BLACK)); // Add spacing and grid decoration
            genreView.setAdapter(genreAdapter);
            genreView.setLayoutManager(new GridLayoutManager(SettingsActivity.this, findTileRowSize(genres.size()))); // Set grid layout manager
        });
    }

    /**
     * Find appropriate row size for grid layout based on the number of genres.
     *
     * @param size Number of genres
     * @return Row size for grid layout
     */
    private int findTileRowSize(int size) {
        if (size < 4) {
            return size;
        }
        // Iterate to find the best row size
        for (int i = 2; i < 4; i++) {
            if (size % i == 0) {
                return i;
            }
        }
        return 3; // Default row size
    }
}
