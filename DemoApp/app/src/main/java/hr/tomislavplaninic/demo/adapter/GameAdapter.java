package hr.tomislavplaninic.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.game.Game;
import hr.tomislavplaninic.demo.game.OnGameClickListener;
import hr.tomislavplaninic.demo.game.Platform;

/**
 * Adapter for displaying games in a RecyclerView.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    // Context of the adapter
    private Context context;
    // List of games to display
    private List<Game> gameList;
    // Listener for game clicks
    private OnGameClickListener onGameClickListener;

    public GameAdapter(Context context, List<Game> gameList, OnGameClickListener onGameClickListener) {
        this.context = context;
        this.gameList = gameList;
        this.onGameClickListener = onGameClickListener;
    }

    /**
     * Set the game list and notify data set change.
     * @param gameList List of games to set
     */
    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    /**
     * ViewHolder class for holding game item views.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView gameImage;
        private TextView gameTitle;
        private TextView gameInfo;
        private ImageView favoriteGame;
        private LinearLayout favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.game_image);
            gameTitle = itemView.findViewById(R.id.game_title);
            gameInfo = itemView.findViewById(R.id.game_info);
            favoriteButton = itemView.findViewById(R.id.game_favorite);
            favoriteGame = itemView.findViewById(R.id.game_favorite_image);

            // Set click listeners
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if favoriteGame is not clicked
                    if (!isViewClicked(v, favoriteGame.getId())) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && onGameClickListener != null) {
                            Game game = gameList.get(position);
                            onGameClickListener.gameClicked(game);
                        }
                    }
                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click on favoriteGame image
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onGameClickListener != null) {
                        Game game = gameList.get(position);
                        game.setFavorite(!game.isFavorite());
                        changeImageIfFavorite(game);
                        onGameClickListener.favoriteGameButtonClicked(game);
                    }
                }
            });
        }

        /**
         * Bind data to views.
         * @param game The game to bind
         */
        public void bind(Game game) {
            gameTitle.setText(game.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = "Not known";
            if (game.getReleased() != null) {
                formattedDate = sdf.format(game.getReleased());
            }
            String infoText = "Release Date: " + formattedDate + "\n" +
                    "Rating: " + game.getRating() + "\n" +
                    "Platform: " + parsePlatforms(game.getPlatforms());
            gameInfo.setText(infoText);
            changeImageIfFavorite(game);
            Glide.with(context)
                    .load(game.getBackgroundImage())
                    .centerCrop()
                    .into(gameImage);
        }

        /**
         * Parse platform list into a string.
         * @param platforms List of platforms
         * @return String representation of platforms
         */
        private String parsePlatforms(List<Platform> platforms) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < platforms.size(); i++) {
                stringBuilder.append(platforms.get(i).getPlatformInfo().getName());
                if (!(platforms.size() - 1 == i)) {
                    stringBuilder.append("/");
                }
            }
            return stringBuilder.toString();
        }

        /**
         * Change image of favorite button based on game's favorite status.
         * @param game The game
         */
        private void changeImageIfFavorite(Game game) {
            if (game.isFavorite()) {
                favoriteGame.setImageResource(R.drawable.mario_star);
            } else {
                favoriteGame.setImageResource(R.drawable.star);
            }
        }

        /**
         * Check if a view or its parent is clicked.
         * @param clickedView The clicked view
         * @param viewId The ID of the view
         * @return True if clicked, false otherwise
         */
        private boolean isViewClicked(View clickedView, int viewId) {
            if (clickedView.getId() == viewId) {
                return true;
            } else if (clickedView.getParent() instanceof View) {
                return isViewClicked((View) clickedView.getParent(), viewId);
            }
            return false;
        }

    }

}
