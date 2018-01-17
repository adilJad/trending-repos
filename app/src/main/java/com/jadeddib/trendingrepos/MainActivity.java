package com.jadeddib.trendingrepos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.jadeddib.trendingrepos.ui.SettingsFragment;
import com.jadeddib.trendingrepos.ui.TrendingFragment;

public class MainActivity
        extends AppCompatActivity {


    public static final String TRENDING_FRGAGMENT_TAG = "trendingFragment";
    public static final String SETTINGS_FRAGMENT_TAG = "settingsFragment";

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener
            =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_trending:
                            fragment = (TrendingFragment) getSupportFragmentManager().findFragmentByTag(TRENDING_FRGAGMENT_TAG);
                            if (fragment == null) {
                                fragment = new TrendingFragment();
                            }
                            break;
                        case R.id.navigation_settings:
                            fragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(SETTINGS_FRAGMENT_TAG);
                            if (fragment == null) {
                                fragment = new SettingsFragment();
                            }
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_container, fragment);
                    transaction.commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, new TrendingFragment());
        transaction.commit();

        BottomNavigationView
                navigation =
                (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
