package com.myapp.doctorapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import com.myapp.doctorapp.model.User;

public class Globals extends Application {
    public static final String API_INSERT="ApiBgTaskInsert";
    public static final String CHECK_REGISTRATION="CheckFbRegistrationTask";
    public static final String API_UPDATE_PROFILE="ApiBgTaskUpdateProfile";
    public static final String API_GET_DOCTOR_LIST="ApiBgTaskInsertGetDoctor";
    public static final String API_GET_EMAIL="ApiBgTaskGetEmail";
    public static final String API_GET_USER="ApiBgTaskGetUser";
    public static final String GET_USER_USING_ID="ApiBgTaskGetUserUsingId";
    public static final String API_GET_ID_LIST="ApiBgTaskGetIdList";
    public static final String SIGNUP="SignUpWithFacebookTask";
    public static final String ALERT_POP_UP="AlertDialog";
    public static final String SET_APPOINTMENT="SetAppointment";
    public static final String GET_APPOINT_DETAILS="GetAppointmentDetails";
    public static final String INSERT_MEDICINE="InsertMedicineDetails";
    public static final String GET_MY_MEDICINE="GetmedicineList";
    public static final String VERIFY_USER="VerifyUser";
    public static final String CHECK_VERIFICATION="CheckVerification";
    public static final String RESET_PASSWORD="ResetPassword";

    public boolean isValidEmail(String email){
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static User bundleToUser(Bundle bundle){
        User user=new User();
        String height=(bundle.getString("height")==null)?"0":bundle.getString("height");
        String weight=(bundle.getString("weight")==null)?"0":bundle.getString("weight");
        String password=(bundle.getString("password")==null)?"":bundle.getString("password");
        String mobile=(bundle.getString("mobile")==null)?"":bundle.getString("mobile");
        String id=(bundle.getString("id")==null)?"1":bundle.getString("id");
        String image=(bundle.getString("image")==null)?"R.drawable.default_image":bundle.getString("image");
        String blood=(bundle.getString("blood"))==null?"not set yet":bundle.getString("blood");


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
        user.setBloodGroup(blood);
        return user;
    }

    public static void addUserToPreference(SharedPreferences.Editor editor, User user){
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

}
