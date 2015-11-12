package com.stopfan.sigmalight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.User;
import com.stopfan.sigmalight.core.utils.Data;

import timber.log.Timber;

/**
 * Created by Denys on 11/8/2015.
 */
public class ProfileFragment extends Fragment {

    private User user;

    public static ProfileFragment getInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = Data.getUserFromPrefs(getActivity());

        Timber.d(user.getName());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
