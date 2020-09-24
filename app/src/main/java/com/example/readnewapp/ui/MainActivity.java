package com.example.readnewapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.Menu;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.readnewapp.R;
import com.example.readnewapp.config.NewsDatabase;
import com.example.readnewapp.config.NewsViewModel;
import com.example.readnewapp.config.VolleySingleton;
import com.example.readnewapp.model.News;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private NewsDatabase db;
    private NewsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = null;
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navigationView = findViewById(R.id.nav_view);
//        if (savedInstanceState == null) {
//            viewModel.nav = navController.saveState();
//        } else {
//            navController.restoreState(viewModel.nav);
//        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_in, R.id.nav_out)
                .setDrawerLayout(drawerLayout)
                .build();
        db = new NewsDatabase(this);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        assert navigationView != null;
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_news) {
            startActivity(new Intent(MainActivity.this, SavedActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.home:
                navController.navigate(R.id.nav_home);
                break;
            case R.id.nav_in:
                navController.navigate(R.id.nav_in);
                break;
            default:
                navController.navigate(R.id.nav_out);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        viewModel.nav = navController.saveState();
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        navController.restoreState(viewModel.nav);
    }
}