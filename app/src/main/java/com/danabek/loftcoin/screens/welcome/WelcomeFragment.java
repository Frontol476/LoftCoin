package com.danabek.loftcoin.screens.welcome;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danabek.loftcoin.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private final static String KEY_PAGE = "page";

    public static WelcomeFragment newInstance(WelcomePage page) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_PAGE, page);

        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.subtitle)
    TextView subtitle;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();

        if (args != null) {
            WelcomePage page = args.getParcelable(KEY_PAGE);

            if (page != null) {
                icon.setImageResource(page.getIcon());
                title.setText(page.getTitle());
                subtitle.setText(page.getSubtitle());
            }
        }
    }
}
