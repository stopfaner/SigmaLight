package com.stopfan.sigmalight;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private String[] strings = {"Где поесть", "Торговые центры", "Ночная жизнь",
                                "Кинотеатры", "Гостиницы", "Такси"};

    private GridView mGridView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mGridView = (GridView) findViewById(R.id.gvMain);
        adapter = new ArrayAdapter<String>(this, R.layout.grid_item, R.id.text, strings);
        mGridView.setAdapter(adapter);
        mGridView.setNumColumns(3);

        mGridView.setVerticalSpacing(16);
        mGridView.setHorizontalSpacing(16);
    }

}
