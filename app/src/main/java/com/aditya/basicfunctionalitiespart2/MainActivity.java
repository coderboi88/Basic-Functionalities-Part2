package com.aditya.basicfunctionalitiespart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

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

        WallpaperManager manager = (WallpaperManager) getSystemService(WALLPAPER_SERVICE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.aditya);

        try{
            manager.setBitmap(bitmap);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //Fot r Lock Screen
                manager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);

                //For Home Screen
                manager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()){
            case R.id.first:
                fragmentClass = FirstFragment.class;
                Toast.makeText(this, "First Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.second:
                fragmentClass = SecondFragment.class;
                Toast.makeText(this, "Second Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.third:
                fragmentClass = ThirdFragment.class;
                Toast.makeText(this, "Third Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                fragmentClass = FirstFragment.class;
                break;
        }

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } 

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();

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
