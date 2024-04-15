package hr.tomislavplaninic.demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.adapter.GenreAdapter;
import hr.tomislavplaninic.demo.genre.Genre;
import hr.tomislavplaninic.demo.genre.GenreClickListener;
import hr.tomislavplaninic.demo.model.GridSpacingItemDecoration;

import androidx.lifecycle.ViewModelProvider;
import hr.tomislavplaninic.demo.databinding.GenreSelctionActivityBinding;

import hr.tomislavplaninic.demo.view_model.GenreSelectionViewModel;

/**
 * Activity for selecting genres.
 */
public class GenreSelectionActivity extends AppCompatActivity {
    /**
     * View model
     */

    private GenreSelectionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GenreSelctionActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.genre_selction_activity);
        viewModel = new ViewModelProvider(this).get(GenreSelectionViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Handle click on continue button
        binding.fabContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to next activity
                viewModel.navigateToVideoGameActivity(GenreSelectionActivity.this);
            }
        });

        // Set up genre adapter
        GenreAdapter genreAdapter = new GenreAdapter(this, new GenreClickListener() {
            @Override
            public void onClicked(List<Genre> selectedGenres) {
                // Set user selected genres in ViewModel
                viewModel.setUserSelectedGenres(selectedGenres);
            }
        });

        // Observe genre list changes
        viewModel.getGenreList().observe(this, genres -> {
            // Update genre adapter
            genreAdapter.setGenres(genres);
            // Set up RecyclerView layout manager and item decoration
            binding.genreRecyclerView.setLayoutManager(new GridLayoutManager(this, findTileRowSize(genres.size())));
            int spacing = 5; // spacing between grid items
            binding.genreRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spacing, 4, Color.BLACK)); // Add spacing and grid decoration
            binding.genreRecyclerView.setAdapter(genreAdapter);
        });

        // Observe FAB enabling
        viewModel.isFabEnabled().observe(this, isEnabled -> {
            // Update FAB background tint based on enabling state
            binding.fabContinue.setBackgroundTintList(isEnabled ?
                    ColorStateList.valueOf(getResources().getColor(R.color.app_spring_green)) :
                    ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        });

        // Fetch genres from source
        viewModel.fetchGenres();
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
