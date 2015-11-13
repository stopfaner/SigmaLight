package com.stopfan.sigmalight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.User;
import com.stopfan.sigmalight.core.utils.Data;

import timber.log.Timber;

/**
 * Created by Denys on 11/8/2015.
 */
public class ProfileFragment extends Fragment {

    private User user;

    private TextView mNameText;
    private TextView mSurnameText;
    private TextView mGenderText;
    private TextView mEmailText;
    private TextView mPhoneText;

    private Button mLogOut;

    public static ProfileFragment getInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        mNameText = (TextView) root.findViewById(R.id.name_text);
        mSurnameText = (TextView) root.findViewById(R.id.surname_text);
        mGenderText = (TextView) root.findViewById(R.id.male_text);
        mEmailText = (TextView) root.findViewById(R.id.email_text);
        mPhoneText = (TextView) root.findViewById(R.id.phone_text);

        mLogOut = (Button) root.findViewById(R.id.log_out_button);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();

                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

                manager.popBackStack("profile", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                ft.commit();
                Data.setNotAuthorized(getActivity());
            }
        });

        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = Data.getUserFromPrefs(getActivity());

        Timber.d("NAMEPREF " + user.getName());

        mNameText.setText(user.getName());
        mSurnameText.setText(user.getSurname());
        mGenderText.setText(user.getGender());
        mPhoneText.setText(user.getPhone());
        mEmailText.setText(user.getEmail());

    }
}
