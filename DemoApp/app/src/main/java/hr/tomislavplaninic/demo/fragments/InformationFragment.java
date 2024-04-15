package hr.tomislavplaninic.demo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import hr.tomislavplaninic.demo.R;
import hr.tomislavplaninic.demo.game.GameDetails;

/**
 * A simple {@link Fragment} subclass to display game information.
 */
public class InformationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    /**
     * Required empty public constructor for Fragment.
     */
    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of the fragment.
     *
     * @param gameDetails The game details to display information for.
     * @return A new instance of fragment InformationFragment.
     */
    public static InformationFragment newInstance(GameDetails gameDetails) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, gameDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            GameDetails gameDetails = (GameDetails) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        TextView gameTitle = rootView.findViewById(R.id.game_title);
        TextView gameDescription = rootView.findViewById(R.id.game_description);
        if (getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            GameDetails gameDetails = (GameDetails) getArguments().getSerializable(ARG_PARAM1);

            if (gameDetails != null) {
                gameTitle.setText(gameDetails.getName());
                gameDescription.setText(HtmlCompat.fromHtml(gameDetails.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            }
        }
        return rootView;
    }
}
