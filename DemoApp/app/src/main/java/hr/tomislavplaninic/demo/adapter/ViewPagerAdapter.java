package hr.tomislavplaninic.demo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hr.tomislavplaninic.demo.fragments.InformationFragment;
import hr.tomislavplaninic.demo.fragments.GameRequirementsFragment;
import hr.tomislavplaninic.demo.game.GameDetails;

/**
 * Adapter for ViewPager2 to display fragments for game information and requirements.
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    /**
     * Game details to pass into the fragments
     */
    private GameDetails gameDetails;

    /**
     * Constructor for ViewPagerAdapter.
     * @param fragmentActivity The FragmentActivity hosting this adapter.
     * @param gameDetails GameDetails object containing information about the game.
     */
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, GameDetails gameDetails) {
        super(fragmentActivity);
        this.gameDetails = gameDetails;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return fragment based on position
        switch (position) {
            case 0:
                // Return InformationFragment for the first position
                return InformationFragment.newInstance(gameDetails);
            case 1:
                // Return GameRequirementsFragment for the second position
                return GameRequirementsFragment.newInstance(gameDetails);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        // Return the number of fragments
        return 2;
    }
}
