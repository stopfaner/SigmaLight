package com.stopfan.sigmalight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.NavItem;
import com.stopfan.sigmalight.core.net.CategoryService;
import com.stopfan.sigmalight.core.net.LoginService;
import com.stopfan.sigmalight.core.net.Request;
import com.stopfan.sigmalight.core.net.response.CategoryResult;
import com.stopfan.sigmalight.core.net.response.RequestResult;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Denys on 11/7/2015.
 */
public class ParentActivity extends AppCompatActivity {

    //Main toolbar
    protected Toolbar toolbar;

    protected ActionBarDrawerToggle actionBarDrawerToggle;
    // Header View is private and changes only there
    private boolean hasUserMenuExpanded = false;

    //Layouts for navigation drawer
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected RecyclerView recyclerView;

    protected CompositeSubscription subscription;
    protected CategoryService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initService();
        fetchCategories();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Method that sets Navigation Menu from left and handles clicks on items
     */
    protected void setNavigationView() {

        if (toolbar != null && getSupportActionBar() != null) {

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = (NavigationView) findViewById(R.id.navigation_view);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    //Setting menuItem checked
                    menuItem.setChecked(true);

                    // Switch menuItem id to start new transition
                    switch (menuItem.getItemId()) {
                        case R.id.action_trc:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_eat:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_night:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_cinema:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_hotel:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_taxi:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_enerteinmant:
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.action_rest:
                            break;
                    }

                    // Closing left drawer after item click
                    drawerLayout.closeDrawers();

                    return true;
                }
            });


            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    toolbar, R.string.opened, R.string.closed) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    hasUserMenuExpanded = false;
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };

            drawerLayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
    }

    private void initService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CategoryResult.class, new CategoryResult.ResultDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LoginService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("Category"))
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(CategoryService.class);
        subscription = new CompositeSubscription();
    }

    private void fetchCategories() {
        subscription.add(
                service.getMenu(new Request<NavItem>("users.getIndexPage", null))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CategoryResult>() {
                    @Override
                    public void call(CategoryResult requestResult) {
                        Timber.d("SIZE: " + requestResult.data.size());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }));
    }

}
