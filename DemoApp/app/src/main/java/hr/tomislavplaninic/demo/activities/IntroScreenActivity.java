package hr.tomislavplaninic.demo.activities;

import android.os.Bundle;
import android.view.View;

import hr.tomislavplaninic.demo.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import hr.tomislavplaninic.demo.database.GenreRepository;
import hr.tomislavplaninic.demo.databinding.ActivityIntroScreenBinding;
import hr.tomislavplaninic.demo.view_model.IntroScreenViewModel;

/**
 * Activity for the introductory screen.
 */
public class IntroScreenActivity extends AppCompatActivity {
    /**
     * View model
     */
    private IntroScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIntroScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_screen);
        viewModel = new ViewModelProvider(this).get(IntroScreenViewModel.class);

        // Set flag for returning user based on selected genres
        viewModel.setIsReturningUser(!new GenreRepository(this).getAllUserSelectedGenres().isEmpty());

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Start animation
        viewModel.startAnimation(this, binding.animationView);

        // Update UI text if returning user
        if (viewModel.getIsReturningUser()) {
            binding.introText.setText(getText(R.string.welcome_back_text));
        }

        // Handle click on intro screen
        binding.introScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check internet connection and navigate accordingly
                viewModel.checkInternetAndNavigate(IntroScreenActivity.this);
            }
        });
    }
}
