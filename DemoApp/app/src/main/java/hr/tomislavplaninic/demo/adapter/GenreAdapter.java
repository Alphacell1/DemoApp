package hr.tomislavplaninic.demo.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.genre.Genre;
import hr.tomislavplaninic.demo.genre.GenreClickListener;

/**
 * Adapter for displaying genres in a RecyclerView.
 */
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    // List of genres
    private List<Genre> genres = new ArrayList<>();
    // Context of the adapter
    private Context context;
    // Listener for genre clicks
    private GenreClickListener genreClickListener;

    // Animation duration
    private static final int ANIMATION_DURATION = 200;

    /**
     * Constructor for GenreAdapter.
     * @param context Context of the adapter
     * @param genreClickListener Listener for genre clicks
     */
    public GenreAdapter(Context context, GenreClickListener genreClickListener) {
        this.context = context;
        this.genreClickListener = genreClickListener;
    }

    /**
     * Set the list of genres and notify data set change.
     * @param genres List of genres to set
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
        notifyDataSetChanged();
    }

    // Method to get selected genres
    private List<Genre> getSelectedGenres() {
        return genres.stream().filter(Genre::isSelected).collect(Collectors.toList());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_tile_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre genre = genres.get(position);
        holder.genreName.setText(genre.getName());
        if (genre.isSelected()) {
            holder.applyOriginalColor();
        } else {
            holder.applyGreyscaleEffect();
        }
        Glide.with(context)
                .load(genre.getImage_background())
                .centerCrop()
                .into(holder.genreBackground);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    /**
     * ViewHolder class for holding genre item views.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView genreName;
        ImageView genreBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.genreName);
            genreBackground = itemView.findViewById(R.id.genreBackground);
            itemView.setOnClickListener(this); // Set click listener
        }

        @Override
        public void onClick(View v) {
            Genre genre = genres.get(getAdapterPosition());

            genre.setSelected(!genre.isSelected());

            if (genre.isSelected()) {
                applyGreyscaleWithAnimation();
            } else {
                applyColorWithAnimation();
            }
            genreClickListener.onClicked(getSelectedGenres());
        }

        // Apply color with animation
        private void applyColorWithAnimation() {
            // Color matrix for greyscale effect
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);

            ValueAnimator colorAnimator = ValueAnimator.ofFloat(0f, 1f);
            colorAnimator.setDuration(ANIMATION_DURATION);
            colorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = (float) animation.getAnimatedValue();
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(1 - fraction);
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    genreBackground.setColorFilter(filter);
                }
            });

            // Scale animation
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(genreBackground, View.SCALE_X, 1f, 1.2f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(genreBackground, View.SCALE_Y, 1f, 1.2f);
            scaleXAnimator.setDuration(ANIMATION_DURATION);
            scaleYAnimator.setDuration(ANIMATION_DURATION);

            // Animator set for running animations together
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(colorAnimator, scaleXAnimator, scaleYAnimator);
            animatorSet.start();
        }

        // Apply greyscale with animation
        private void applyGreyscaleWithAnimation() {
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(ANIMATION_DURATION);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = (float) animation.getAnimatedValue();
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(fraction);
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    genreBackground.setColorFilter(filter);
                }
            });

            // Scale animation
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(genreBackground, View.SCALE_X, 1.2f, 1f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(genreBackground, View.SCALE_Y, 1.2f, 1f);
            scaleXAnimator.setDuration(ANIMATION_DURATION);
            scaleYAnimator.setDuration(ANIMATION_DURATION);

            // Animator set for running animations together
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator, scaleXAnimator, scaleYAnimator);
            animatorSet.start();
        }

        // Apply greyscale effect
        private void applyGreyscaleEffect() {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
            genreBackground.setColorFilter(filter);
            float scale = 1.2f;
            genreBackground.setScaleX(scale);
            genreBackground.setScaleY(scale);
        }

        // Apply original color
        private void applyOriginalColor() {
            genreBackground.clearColorFilter();
        }
    }
}
