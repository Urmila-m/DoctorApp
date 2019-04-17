package com.myapp.doctorapp.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.backgroundtasks.NotificationAlarmThread;
import com.myapp.doctorapp.fragments.FindDoctorFragment;
import com.myapp.doctorapp.fragments.HomeFragment;
import com.myapp.doctorapp.fragments.MyAppointmentFragment;
import com.myapp.doctorapp.fragments.ProfileFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.AppointmentDetail;
import com.myapp.doctorapp.model.PostResponse;
import com.myapp.doctorapp.services.NotificationService;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.myapp.doctorapp.Globals.ALERT_POP_UP;
import static com.myapp.doctorapp.Globals.API_GET_DOCTOR_LIST;
import static com.myapp.doctorapp.Globals.API_UPDATE_PROFILE;
import static com.myapp.doctorapp.Globals.GET_APPOINT_DETAILS;
import static com.myapp.doctorapp.Globals.SET_APPOINTMENT;

public class AfterLoginActivity extends PreferenceInitializingActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentButtonClickListener, OnDataRetrievedListener {

    CircleImageView imageView;
    TextView tvName;
    TextView tvEmail;
    FrameLayout frameLayout;
    ApiBackgroundTask apiTask;
    NavigationView navigationView;
    Bundle timeAndDate;
    AlarmManager alarmManager;
    List<PendingIntent> listOfPI;
//    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        Log.e("TAG", "onCreate: testing the code after thread" );

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

        apiTask=new ApiBackgroundTask();

        apiTask.getAppointmentDetails(preferences.getString("name", ""), new OnDataRetrievedListener() {
            @Override
            public void onDataRetrieved(String source, Bundle bundle) {
                List<AppointmentDetail> list = (List<AppointmentDetail>) bundle.getSerializable("DetailList");
                list = filterFutureDate(list);
                listOfPI=new ArrayList<>();
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                AfterLoginActivity.this.addPIofToday(list, listOfPI);
            }
        });

        timeAndDate=new Bundle();
        timeAndDate.putString("patient", preferences.getString("name", ""));

        View view=findViewById(R.id.dl_content);
        View view1=view.findViewById(R.id.include_after_login);
        frameLayout=view1.findViewById(R.id.fl_after_login_content);

        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), new HomeFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        } else if (id == R.id.nav_appointment) {
           Log.e("TAG", "onNavigationItemSelected: "+preferences.getString("name", ""));
           apiTask.getAppointmentDetails(preferences.getString("name", ""), this);

        } else if (id == R.id.nav_log_out) {
           editor.clear().commit();
           LoginManager.getInstance().logOut();//fb sign in vaye
           Intent intent=new Intent(AfterLoginActivity.this, SignInActivity.class);

           for (PendingIntent pi:listOfPI
                ) {
               alarmManager.cancel(pi);
           }
           startActivity(intent);
           finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {
        if (id==R.id.find_doctor_block) {
            apiTask.getDoctorList(this);
        }

        else if(id==R.id.home_appointment){
            apiTask.getAppointmentDetails(preferences.getString("name", ""), this);
        }
        else if (id==R.id.btn_edit_profile){
            Bundle previousValues=new Bundle();
            previousValues.putString("height", preferences.getString("height", ""));
            previousValues.putString("weight", preferences.getString("weight", ""));
            previousValues.putString("blood", preferences.getString("blood", ""));
            fragment.setArguments(previousValues);
            getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).addToBackStack(null).commit();
        }

        else if (id==R.id.btn_edit_profile_update){

            String height=bundle.getString("height");
            String weight=bundle.getString("weight");
            String blood=bundle.getString("blood");

            editor.putString("weight", weight).commit();
            editor.putString("height", height).commit();
            editor.putString("blood", blood).commit();

            apiTask.updateProfile(height, weight, blood,
                    preferences.getString("email", ""),
                    this);

            navigationView.setCheckedItem(R.id.nav_home);
            getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).commit();
        }

        else if (id==R.id.btn_date_picker){
            Toast.makeText(this, "date Picker", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).commit();
            for (String s:bundle.keySet()
                 ) {
                timeAndDate.putString(s, bundle.getString(s));
            }
        }

        else if (id==R.id.btn_choose_time){
            getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).commit();
            for (String s:bundle.keySet()
            ) {
                timeAndDate.putString(s, bundle.getString(s));
            }

            apiTask.setAppointment(timeAndDate, this);

        }

        else if (id==R.id.btn_book_appointment_doctor_info){
            for (String s:bundle.keySet()
            ) {
                timeAndDate.putString(s, bundle.getString(s));
            }
            getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).commit();
        }

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

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        if (source.equals(API_GET_DOCTOR_LIST)) {
            FindDoctorFragment fragment = new FindDoctorFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), fragment).addToBackStack(null).commit();
        }
        else if (source.equals(API_UPDATE_PROFILE)){
            Log.e("TAG", "onDataRetrieved: "+bundle.getString("errorMsg"));
        }

        else if (source.equals(ALERT_POP_UP)){
            Intent intent=new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+bundle.getString("phone")));
            startActivity(intent);
        }

        else if (source.equals(SET_APPOINTMENT)){
            PostResponse response= (PostResponse) bundle.getSerializable("response");
            Log.e("TAG", "onDataRetrieved: "+response.getErrorMsg() );
            if (response.isSuccess()){
                Log.e("TAG", "onDataRetrieved: appointment set");
                //TODO if today ho vaye pending intent ma add garne
                Toast.makeText(this, "appointment set", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.e("TAG", "onDataRetrieved: "+ response.getErrorMsg());
                Toast.makeText(this, response.getErrorMsg(), Toast.LENGTH_LONG).show();
            }
        }

        else if (source.equals(GET_APPOINT_DETAILS)){

            MyAppointmentFragment fragment=new MyAppointmentFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), fragment).commit();

        }
    }

    Bundle preferenceToBundle(){//only the values needed to be passed to ProfileFragment
        Bundle bundle=new Bundle();
        bundle.putString("name",preferences.getString("name", ""));
        bundle.putString("image",preferences.getString("image", ""));
        bundle.putString("weight",preferences.getString("weight", ""));
        bundle.putString("height",preferences.getString("height", ""));
        bundle.putString("gender",preferences.getString("gender", ""));
        bundle.putString("birthday",preferences.getString("dob", ""));
        bundle.putString("blood", preferences.getString("blood", ""));
        return bundle;
    }

    private List<AppointmentDetail> filterFutureDate(List<AppointmentDetail> date){
        List<AppointmentDetail> futureApp=new ArrayList<>();
        Calendar current=Calendar.getInstance();
        for (AppointmentDetail d:date
             ) {
            Calendar c=stringToCalendar(d.getAppointment_date(), d.getAppointment_time());
            if (c.getTimeInMillis()>=current.getTimeInMillis()){
                futureApp.add(d);
            }
        }
        return futureApp;
    }

    private Calendar stringToCalendar(String date, String time){
        Date appDate=null;
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy hh:mm");
        try {
            appDate=dateFormat.parse(date+" "+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c=Calendar.getInstance();
        c.setTime(appDate);
        return c;
    }

    private boolean isDateToday(AppointmentDetail d){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String currentDate=dateFormat.format(c.getTime());
        if (d.getAppointment_date().equals(currentDate)){
            return true;
        }
        else
            return false;
    }

    public void addPIofToday(List<AppointmentDetail> list, List<PendingIntent> listOfPI){
        List<Calendar> appointDateList = new ArrayList<>();
        for (AppointmentDetail d:list
        ) {
            if (isDateToday(d)){
                Log.e("TAG", "onDataRetrieved: "+d.getAppointment_date()+" "+d.getAppointment_time());
                Calendar appointDate=stringToCalendar(d.getAppointment_date(), d.getAppointment_time());
                Intent intent = new Intent(AfterLoginActivity.this, NotificationService.class);
                intent.putExtra("title", "Appointment");
                intent.putExtra("message", "You have an appoinment with "+d.getDoctor()+" today at "+appointDate.get(Calendar.HOUR_OF_DAY)+":"+appointDate.get(Calendar.MINUTE));
                PendingIntent pi=PendingIntent.getService(AfterLoginActivity.this, (int) (Math.random()*1000), intent, 0);
                listOfPI.add(pi);
                appointDateList.add(appointDate);
            }
        }

       for (int i=0; i<appointDateList.size();i++){
           alarmManager.setExact(AlarmManager.RTC_WAKEUP, appointDateList.get(i).getTimeInMillis(), listOfPI.get(i));
       }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: ");
    }

}
