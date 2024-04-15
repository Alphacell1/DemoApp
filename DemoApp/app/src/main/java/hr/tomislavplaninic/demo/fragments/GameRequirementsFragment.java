package hr.tomislavplaninic.demo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.game.GameDetails;

/**
 * A fragment to display game requirements.
 */
public class GameRequirementsFragment extends Fragment {

    private static final String FIRST_PARAMETER = "param1";

    private GameDetails gameDetails;

    /**
     * Required empty public constructor for Fragment.
     */
    public GameRequirementsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of the fragment.
     *
     * @param gameDetails The game details to display requirements for
     * @return A new instance of fragment GameRequirementsFragment.
     */
    public static GameRequirementsFragment newInstance(GameDetails gameDetails) {
        GameRequirementsFragment fragment = new GameRequirementsFragment();
        Bundle args = new Bundle();
        args.putSerializable(FIRST_PARAMETER, gameDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gameDetails = (GameDetails) getArguments().getSerializable(FIRST_PARAMETER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requirements, container, false);
        TextView minimumReq = rootView.findViewById(R.id.minimum_requirements);
        TextView recommendedReq = rootView.findViewById(R.id.recommended_requirements);
        if (getArguments() != null && getArguments().containsKey(FIRST_PARAMETER)) {
            GameDetails gameDetails = (GameDetails) getArguments().getSerializable(FIRST_PARAMETER);

            if (gameDetails != null) {
                minimumReq.setText(gameDetails.getRequirements().getMinimum());
                recommendedReq.setText(gameDetails.getRequirements().getRecommended());
            }
        }
        return rootView;
    }
}
