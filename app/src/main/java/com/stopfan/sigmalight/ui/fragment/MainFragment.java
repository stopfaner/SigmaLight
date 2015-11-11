package com.stopfan.sigmalight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.SubCategory;
import com.stopfan.sigmalight.core.models.User;
import com.stopfan.sigmalight.core.net.LoginService;
import com.stopfan.sigmalight.core.net.RequestResult;
import com.stopfan.sigmalight.core.utils.Data;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Denys on 11/7/2015.
 */
public class MainFragment extends Fragment {

    private LoginService service;
    private CompositeSubscription subscription;

    private View trcCategory;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //test();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        trcCategory = rootView.findViewById(R.id.trc_categry);
        trcCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();

                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 1);
                fragment.setArguments(bundle);

                ft.replace(R.id.fragment_host, fragment, "subCategory");
                ft.addToBackStack("subCategory");
                ft.commit();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Data.setNotAuthorized(getActivity());
        //initService();
    }

    private void initService() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RequestResult.class, new RequestResult.ResultDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LoginService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("LOGIN"))
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(LoginService.class);
        subscription = new CompositeSubscription();

    }




}
