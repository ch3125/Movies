package com.me.movies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    public static boolean TABLET=false;
    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    public boolean isTABLET(Context context){
        boolean xlarge=((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==4);
        boolean large=((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TABLET=isTABLET(this);
       /*if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new MoviesFragment()).commit();
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
                        Intent intent=new Intent(MainActivity.this, About.class);
                        startActivity(intent);
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
       // View hview=mNavigationView.getHeaderView(0);



        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //initializing the tablayout
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        //adding the tabs
        tabLayout.addTab(tabLayout.newTab().setText("UPCOMING"),0);
        tabLayout.addTab(tabLayout.newTab().setText("NOW PLAYING"),1);
        tabLayout.addTab(tabLayout.newTab().setText("POPULAR"),2);
        tabLayout.addTab(tabLayout.newTab().setText("TOP RATED"),3);
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        //initializing viewpager
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));

        //creating our pageradapter
        PagerAdapter1 adapter=new PagerAdapter1(getSupportFragmentManager(),tabLayout.getTabCount());
        //adding adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);





    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!isNetworkAvailable()) {
            Toast.makeText(this,"No internet connection found",Toast.LENGTH_LONG).show();

        }else{
            checkFirstRun();

        }
        }
    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            // Place your dialog code here to display the dialog
            //second dialog
            LayoutInflater li=LayoutInflater.from(this);
            View promptsView=li.inflate(R.layout.dialog_entername,null);
            AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
            alertdialogbuilder.setView(promptsView);
            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.username);

            // set dialog message
            alertdialogbuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Toast.makeText(MainActivity.this,"Welcome "+userInput.getText(),Toast.LENGTH_LONG).show();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertdialogbuilder.create();

            // show it
            alertDialog.show();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }


    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();

    }

   // private Boolean exit = false;
    @Override
    public void onBackPressed() {
      /*  if (exit) {

            moveTaskToBack(true);
            // finish activity
        } else {*/
            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);

            alertDialog1.setTitle("Close the app?");

            alertDialog1.setMessage("Are you sure you want to exit?");

            alertDialog1.setIcon(R.drawable.ok);

            alertDialog1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {

                    // Write your code here to invoke YES event
                    moveTaskToBack(true);
                }
            });

            // Setting Negative "NO" Button
            alertDialog1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    dialog.cancel();

                }
            });

            // Showing Alert Message
            alertDialog1.show();
          /*  exit = true;
           new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);*/

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

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final WeakReference<TabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            final TabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null) {
                final boolean updateText = (mScrollState == ViewPager.SCROLL_STATE_DRAGGING)
                        || (mScrollState == ViewPager.SCROLL_STATE_SETTLING
                        && mPreviousScrollState == ViewPager.SCROLL_STATE_DRAGGING);
                tabLayout.setScrollPosition(position, positionOffset, updateText);
            }
        }

        @Override
        public void onPageSelected(int position) {
            final TabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null) {
                tabLayout.getTabAt(position).select();
            }
        }
    }
}


