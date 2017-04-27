package com.me.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.me.movies.About;

import static com.me.movies.R.id.tool_bar;


public class DetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
      /*  Toolbar myChildToolbar =
                (Toolbar) findViewById(tool_bar);
        setSupportActionBar(myChildToolbar);
        myChildToolbar.setTitleTextColor(Color.WHITE);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
       // ab.setDisplayHomeAsUpEnabled(true);

      /*  if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.container_new,new DetailActivityFragment()).commit();
        }*/
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    case R.id.about_page:
                        Intent intent=new Intent(DetailActivity.this, About.class);
                        startActivity(intent);
                    case R.id.moviespage:
                        Intent intent1=new Intent(DetailActivity.this,MainActivity.class);
                        startActivity(intent1);
                    default:
                        return true;
                }
            }
        });
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //initializing the tablayout
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        //adding the tabs
        tabLayout.addTab(tabLayout.newTab().setText("INFO"),0);
        tabLayout.addTab(tabLayout.newTab().setText("CAST"),1);
        tabLayout.addTab(tabLayout.newTab().setText("OVERVIEW"),2);
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        //initializing viewpager
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new MainActivity.TabLayoutOnPageChangeListener(tabLayout));

        //creating our pageradapter
        PagerAdapter2 adapter=new PagerAdapter2(getSupportFragmentManager(),tabLayout.getTabCount());
        //adding adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!isNetworkAvailable()) {
            Toast.makeText(this,"No internet connection found",Toast.LENGTH_LONG).show();

        }}
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();

    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    }




