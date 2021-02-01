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
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.basicfunctionalitiespart2.Fragments.BarChartFragment;
import com.aditya.basicfunctionalitiespart2.Fragments.FirstFragment;
import com.aditya.basicfunctionalitiespart2.Fragments.PieChartFragment;
import com.aditya.basicfunctionalitiespart2.Fragments.RadarChartFragment;
import com.aditya.basicfunctionalitiespart2.Fragments.SecondFragment;
import com.aditya.basicfunctionalitiespart2.Fragments.ThirdFragment;
import com.github.mikephil.charting.data.RadarData;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;
    private TextView textView;

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

        textView = findViewById(R.id.saveConfig);
        if(savedInstanceState!=null){
            if(savedInstanceState.getString("value")!=null){
                textView.setText(savedInstanceState.getString("value"));
            }
        }

        /*WallpaperManager manager = (WallpaperManager) getSystemService(WALLPAPER_SERVICE);
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
        }*/
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("value",textView.getText().toString());
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
            case R.id.first_sub:
                fragmentClass = BarChartFragment.class;
                break;
            case R.id.second_sub:
                fragmentClass = PieChartFragment.class;
                break;
            case R.id.third_sub:
                fragmentClass = RadarChartFragment.class;
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
