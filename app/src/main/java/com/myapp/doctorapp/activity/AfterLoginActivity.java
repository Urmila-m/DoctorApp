package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.myapp.doctorapp.DoctorAdapter;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.fragments.HomeFragment;
import com.myapp.doctorapp.fragments.ProfileFragment;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AfterLoginActivity extends PreferenceInitializingActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentButtonClickListener {

    CircleImageView imageView, imageProfile;
    TextView tvName;
    TextView tvEmail;
    FrameLayout frameLayout;

    TextView tvNameProfile, tvAge, tvHeight, tvWeight, tvGender, tvBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View view=findViewById(R.id.dl_content);
        View view1=view.findViewById(R.id.include_after_login);
        frameLayout=view1.findViewById(R.id.fl_after_login_content);

        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), new HomeFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        imageView=navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);
        tvName=navigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_name);
        tvEmail=navigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_email);

        setNavHeaderElements();
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_profile) {
           ProfileFragment fragment=new ProfileFragment();
           fragment.setArguments(preferenceToBundle());
           getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_home) {
           getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), new HomeFragment()).commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_log_out) {
           //TODO manual sign in ho vane logout garda preference clear garera matra beforeLoginActivity Jane
           editor.clear();
           editor.remove("email");
           LoginManager.getInstance().logOut();//fb sign in vaye
           Intent intent=new Intent(AfterLoginActivity.this, BeforeLoginActivity.class);
           startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), fragment).addToBackStack(null).commit();
    }

    void setNavHeaderElements(){
        String image=preferences.getString("image", "");
        String name=preferences.getString("name", "");
        String email=preferences.getString("email", "");
        tvName.setText(name);
        tvEmail.setText(email);
        Picasso.get().load(image)
                .placeholder(R.drawable.default_image)
                .into(imageView);

    }

    Bundle preferenceToBundle(){//only the values needed to be passed to ProfileFragment
        Bundle bundle=new Bundle();
        bundle.putString("name",preferences.getString("name", ""));
        bundle.putString("image",preferences.getString("image", ""));
        bundle.putString("weight",preferences.getString("weight", ""));
        bundle.putString("height",preferences.getString("height", ""));
        bundle.putString("gender",preferences.getString("gender", ""));
        bundle.putString("birthday",preferences.getString("dob", ""));
        bundle.putString("blood", "AB+");//TODO this field needs to be added in database

        return bundle;
    }
}
