package hr.tomislavplaninic.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.adapter.ViewPagerAdapter;
import hr.tomislavplaninic.demo.game.GameDetails;

/**
 * Dialog for displaying game overview.
 */
public class GameOverviewDialog {

    private final GameDetails gameDetails;
    private ViewPager2 viewPager2;

    /**
     * Constructs a new GameOverviewDialog.
     *
     * @param gameDetails The details of the game to display in the overview
     */
    public GameOverviewDialog(GameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }

    /**
     * Shows the game overview dialog.
     *
     * @param context The context to use
     */
    public void show(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.game_overview);

        ImageView imageOverview = dialog.findViewById(R.id.game_overview_image);
        viewPager2 = dialog.findViewById(R.id.view_pager);
        LinearLayout gameInfo = dialog.findViewById(R.id.game_overview_info);
        LinearLayout gameOverview = dialog.findViewById(R.id.game_overview_requirements);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);

        dialog.show();

        Glide.with(context)
                .load(gameDetails.getBackgroundImage())
                .centerCrop()
                .into(imageOverview);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter((FragmentActivity) context, gameDetails);
        viewPager2.setAdapter(viewPagerAdapter);

        gameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(0);
            }
        });

        gameOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(1);
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                viewPager2.setAdapter(null);
                viewPager2 = null;
            }
        });
    }
}
