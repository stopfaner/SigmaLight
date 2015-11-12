package com.stopfan.sigmalight.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.utils.Data;
import com.stopfan.sigmalight.ui.fragment.LogInFragment;
import com.stopfan.sigmalight.ui.fragment.MainFragment;
import com.stopfan.sigmalight.ui.fragment.ProfileFragment;
import com.stopfan.sigmalight.ui.fragment.RegisterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ParentActivity {

    @Bind(R.id.profile_tab) View profileCabinet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        setMainFragment();
        setNavigationView();

        setListeners();

        /*mGridView = (GridView) findViewById(R.id.gvMain);
        adapter = new ArrayAdapter<String>(this, R.layout.grid_item, R.id.text, strings);
        mGridView.setAdapter(adapter);
        mGridView.setNumColumns(3);

        mGridView.setVerticalSpacing(16);
        mGridView.setHorizontalSpacing(16);*/
    }


    private void setMainFragment() {
        MainFragment fragment = MainFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_host, fragment)
                .commit();
    }

    private void setListeners() {
        profileCabinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Data.isAuthorized(getApplicationContext())) {
                    FragmentManager manager = getSupportFragmentManager();

                    FragmentTransaction ft = manager.beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

                    ProfileFragment fragment = ProfileFragment.getInstance();

                    ft.replace(R.id.fragment_host, fragment, "profileFragment");
                    manager.popBackStack("login", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ft.addToBackStack("profile");
                    ft.commit();
                } else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

                    LogInFragment fragment = LogInFragment.getInstance();

                    ft.replace(R.id.fragment_host, fragment, "loginFragment");
                    ft.addToBackStack("login");
                    ft.commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
