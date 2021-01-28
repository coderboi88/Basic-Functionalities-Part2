package com.aditya.basicfunctionalitiespart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvView);
    }

    private void setupDrawerContent(NavigationView nvView) {
        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setupMenu(menuItem);
                return true;
            }
        });
    }

    private void setupMenu(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.first:
                Toast.makeText(this, "First Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.second:
                Toast.makeText(this, "Second Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.third:
                Toast.makeText(this, "Third Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
