package com.example.andras.myapplication.material;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.andras.myapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MaterialTestActivity extends AppCompatActivity {

    private static final long DRAWER_CLOSE_DELAY_MS = 200;

    @InjectView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.navigation)
    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;
    private final Handler drawerActionHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_test);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        // set up the hamburger icon to open and close the drawer
        drawerToggle = new ActionBarDrawerToggle(MaterialTestActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world) {
            //ezek nem kötelezőek
//            @Override
//            public void onDrawerClosed(View drawerView) {
//            ...
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//            ...
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//            ...
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//            ...
//
//            @Override
//            public boolean onOptionsItemSelected(MenuItem item) {
//                return super.onOptionsItemSelected(item);
//            }
//
//            @Override
//            public void onConfigurationChanged(Configuration newConfig) {
//            ...
//        }
        };
        //http://blog.xebia.com/2015/06/09/android-design-support-navigationview/
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // listen for navigation events
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView headerText = (TextView) headerView.findViewById(R.id.header_title);
        headerText.setText("This is the nav header");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swichView(-1);
    }

    private boolean onNavigationItemSelected(final MenuItem menuItem) {
        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening. This may seem a bit hacky but in practice
        // it works noticeably faster than listening to the onDrawerClosed callback
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swichView(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);

        return true;
    }

    private void swichView(int resId) {
        Fragment fragment = getFragmentById(resId);
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //többi menüelem kezelése
        //...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private Fragment getFragmentById(int resId) {
        switch (resId) {
            case R.id.nav_drawer_recycleview : return new RecyclerViewFragment();
            case R.id.nav_drawer_collapsible : startActivity(new Intent(this, CollapsibleToolbarActivity.class));
            default: return null;
        }
    }
}
