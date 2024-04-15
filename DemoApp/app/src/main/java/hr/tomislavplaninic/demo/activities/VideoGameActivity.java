package hr.tomislavplaninic.demo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.adapter.GameAdapter;
import hr.tomislavplaninic.demo.database.FirebaseManager;
import hr.tomislavplaninic.demo.databinding.ActivityVideoGameBinding;
import hr.tomislavplaninic.demo.game.Game;
import hr.tomislavplaninic.demo.game.OnGameClickListener;
import hr.tomislavplaninic.demo.genre.Genre;
import hr.tomislavplaninic.demo.view_model.VideoGameViewModel;

/**
 * Activity for video game listing.
 */
public class VideoGameActivity extends AppCompatActivity {
    private static final String TAG = VideoGameActivity.class.getSimpleName();

    // ViewModel for the activity
    private VideoGameViewModel viewModel;
    // Data binding for the activity
    private ActivityVideoGameBinding binding;
    // Firebase manager for fetching favorite games
    private FirebaseManager firebaseManager;
    // List to hold favorite game names
    private List<String> favoriteGameNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_game);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(VideoGameViewModel.class);
        // Set ViewModel to data binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Initialize ViewModel and FirebaseManager
        viewModel.init(this);
        firebaseManager = FirebaseManager.getInstance();

        // Fetch favorite games from Firebase
        firebaseManager.fetchFavoriteGames(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteGameNames.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String gameName = child.getValue(String.class);
                    favoriteGameNames.add(gameName);
                }
                viewModel.setFavoriteGameNames(favoriteGameNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VideoGameActivity.this, "Failed getting favorite games", Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.toString());
            }
        });

        // Set up observers for ViewModel
        setupObservers();

        // Set click listener for previous page button
        binding.prevPageButton.setOnClickListener(view -> viewModel.onPrevButtonClick());

        // Set click listener for next page button
        binding.nextPageButton.setOnClickListener(view -> viewModel.onNextButtonClick());

        // Set item selected listener for genre filter spinner
        binding.genreFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.onGenreSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Set click listener for settings tab
        LinearLayout videoGameTab = binding.settingsTab;
        videoGameTab.setOnClickListener(view -> {
            Intent intent = new Intent(VideoGameActivity.this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Disable transition animation
        });
    }

    /**
     * Set up observers for LiveData updates.
     */
    private void setupObservers() {
        // Observe genres from ViewModel
        viewModel.getGenres().observe(this, genres -> {
            List<String> genreNames = new ArrayList<>();
            genreNames.add("No filter");
            genreNames.add("Favorite games");
            for (Genre genre : genres) {
                genreNames.add(genre.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genreNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = binding.genreFilter;
            spinner.setAdapter(adapter);
        });

        // Observe games from ViewModel
        viewModel.getGames().observe(this, games -> {
            RecyclerView videoGameView = binding.videoGameList;
            GameAdapter gameAdapter = new GameAdapter(VideoGameActivity.this, games, new OnGameClickListener() {
                @Override
                public void gameClicked(Game game) {
                    viewModel.handleGameInfoClick(game, VideoGameActivity.this);
                }

                @Override
                public void favoriteGameButtonClicked(Game game) {
                    if (game.isFavorite()) {
                        firebaseManager.insertFavoriteGame(game.getName());
                    } else {
                        firebaseManager.deleteFavoriteGame(game.getName());
                    }
                }
            });
            videoGameView.setAdapter(gameAdapter);
            videoGameView.setLayoutManager(new LinearLayoutManager(VideoGameActivity.this));
        });

        // Observe pagination text from ViewModel
        viewModel.getPaginationText().observe(this, text -> {
            TextView paginationText = binding.pageNumberText;
            paginationText.setText(text);
        });
    }
}
