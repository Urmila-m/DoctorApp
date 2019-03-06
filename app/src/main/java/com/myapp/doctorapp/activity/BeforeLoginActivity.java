package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.fragments.SignInFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.EmailPasswordResponse;
import com.myapp.doctorapp.model.User;

import java.util.List;
import java.util.Set;

import static com.myapp.doctorapp.Globals.API_GET_EMAIL;
import static com.myapp.doctorapp.Globals.API_GET_USER;
import static com.myapp.doctorapp.Globals.API_INSERT;
import static com.myapp.doctorapp.Globals.SIGNUP;

public class BeforeLoginActivity extends PreferenceInitializingActivity implements OnFragmentButtonClickListener, OnDataRetrievedListener {

    private String email;
    private String password;

    Fragment myFragment;
    Bundle registrationInfo;
    ApiBackgroundTask apiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

//        if (preferences.contains("email")){
//            startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivity.class));
//        }
//        else {
            registrationInfo = new Bundle();
            apiTask = new ApiBackgroundTask();
            SignInFragment signInFragment = new SignInFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_before_login, signInFragment).commit();
            Log.e("TAG", "onCreate: before login activity");
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myFragment.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {
        if(id!=R.id.btn_custom_attribute_picker && id!=R.id.btn_sign_up_options_fb&&id!=R.id.btn_manual_sign_in){
            getSupportFragmentManager().beginTransaction().add(R.id.fl_before_login, fragment).commit();
            Set<String> key=bundle.keySet();
            for (String s:key
            ) {
                registrationInfo.putString(s, bundle.getString(s));
            }

            if (id==R.id.tv_sign_in_create_account){//fragment ma
                myFragment=fragment;
            }
        }
        else{
            if (id==R.id.btn_manual_sign_in){
                email=bundle.getString("email");
                password=bundle.getString("password");
                if (password.equals("")){
                    Log.e("TAG", "onButtonClicked: Fill the password!!");
                }
                else {
                    Log.e("TAG", "onButtonClicked: " + email);
                    apiTask.getEmailPassword(this);
                }
            }
            else {
                if (id==R.id.btn_sign_up_options_fb) {
//                    myFragment = ;
                }
                if (id == R.id.btn_custom_attribute_picker) {
                    Set<String> key = bundle.keySet();
                    for (String s : key
                    ) {
                        registrationInfo.putString(s, bundle.getString(s));
                    }
                    User user=bundleToUser(registrationInfo);
                    addUserToPreference(editor, user);
                }
                apiTask.insertResponse(registrationInfo, this);
                startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivity.class));
            }
        }
    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
       if (source.equals(API_INSERT)){
           Log.e("TAG", "onDataRetrieved: registration successful"+bundle.getString("errorMsg"));
       }
       else if (source.equals(API_GET_EMAIL)){
           Log.e("TAG", "onDataRetrieved: source api get email");
           int count=0;
           List<EmailPasswordResponse> list= (List<EmailPasswordResponse>) bundle.getSerializable("email_password_list");
           for (EmailPasswordResponse response: list
           ) {
               if(response.getEmail().equals(email)&&response.getPassword().equals(password)){
                   apiTask.getUser(email, this);//TODO email register vako xa xaina xuttai check garne ani password
                   count++;
                   break;
               }
           }
           if (count==0){
               Log.e("TAG", "onDataRetrieved: "+email+" doesnt have account yet!! or password incorrect");
           }
       }

       else if (source.equals(API_GET_USER)){
           User user= (User) bundle.getSerializable("User");
           addUserToPreference(editor, user);
           startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivity.class));
           finish();
       }

       else if (source.equals(SIGNUP)){
           apiTask.insertResponse(bundle, this);
           User user=bundleToUser(bundle);
           addUserToPreference(editor, user);
           Intent intent=new Intent(BeforeLoginActivity.this, AfterLoginActivity.class);
           startActivity(intent);
       }

    }

    public String[] bundleToString(Bundle bundle){
        String id=bundle.getString("id");
        String name=bundle.getString("name");
        String gender=bundle.getString("gender");
        String bday=bundle.getString("birthday");
        String email=bundle.getString("email");
        String image=bundle.getString("image");
        String[] person={id, name, gender, bday, email, image};
        return person;

    }

    void addUserToPreference(SharedPreferences.Editor editor, User user){
        editor.putString("id",user.getId());
        editor.putString("name",user.getName());
        editor.putString("dob", user.getDOB());
        editor.putString("email", user.getEmail());
        editor.putString("gender", user.getGender());
        editor.putString("height", user.getHeight());
        editor.putString("mobile", user.getMobileNumber());
        editor.putString("weight", user.getWeight());
        editor.putString("image", user.getImage());
        editor.putString("password", user.getPassword());
        editor.putString("blood", user.getBloodGroup());
        editor.commit();
    }

    User bundleToUser(Bundle bundle){
        User user=new User();
        String height=(bundle.getString("height")==null)?"0":bundle.getString("height");
        String weight=(bundle.getString("weight")==null)?"0":bundle.getString("weight");
        String password=(bundle.getString("password")==null)?"":bundle.getString("password");
        String mobile=(bundle.getString("mobile")==null)?"":bundle.getString("mobile");
        String id=(bundle.getString("id")==null)?"1":bundle.getString("id");
        String image=(bundle.getString("image")==null)?"R.drawable.default_image":bundle.getString("image");

        user.setName(bundle.getString("name"));
        user.setId(id);
        user.setDOB(bundle.getString("birthday"));
        user.setEmail(bundle.getString("email"));
        user.setPassword(password);
        user.setImage(image);
        user.setGender(bundle.getString("gender"));
        user.setHeight(height);
        user.setWeight(weight);
        user.setMobileNumber(mobile);
        user.setBloodGroup("not set yet");
        return user;
    }
}
