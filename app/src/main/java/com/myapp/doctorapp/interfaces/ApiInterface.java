package com.myapp.doctorapp.interfaces;

import android.os.Bundle;

import com.myapp.doctorapp.model.AppointmentDetail;
import com.myapp.doctorapp.model.Doctor;
import com.myapp.doctorapp.model.EmailPasswordResponse;
import com.myapp.doctorapp.model.IdModel;
import com.myapp.doctorapp.model.MedicineDetails;
import com.myapp.doctorapp.model.PostResponse;
import com.myapp.doctorapp.model.User;
import com.myapp.doctorapp.model.VerificationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<PostResponse> insertData(@Field("action") String insert,
                                  @Field("name") String name,
                                  @Field("mobile") String mobile,
                                  @Field("id") String id,
                                  @Field("dob") String dob,
                                  @Field("gender") String gender,
                                  @Field("height") String height,
                                  @Field("weight") String weight,
                                  @Field("password") String password,
                                  @Field("image") String image,
                                  @Field("email") String email);


    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<List<EmailPasswordResponse>> getEmailPasswordList(@Field("action") String getEmailList);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<User> getUser(@Field("action") String getRecordWithEmail,
                       @Field("email") String email);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<List<Doctor>> getDoctorList(@Field("action") String getDoctorList);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<PostResponse> updateProfile(@Field("action") String editProfile,
                                     @Field("weight") String weight,
                                     @Field("height") String height,
                                     @Field("blood") String bloodGroup,
                                     @Field("email") String email);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<List<IdModel>> getIdList(@Field("action") String getIdList);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<User> getUserUsingId(@Field("action") String getRecordWithId,
                              @Field("id") String id);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<PostResponse> setAppointment(@Field("action") String setAppointment,
                                      @Field("doctorName") String doctor,
                                      @Field("doctorFee") String doctorFee,
                                      @Field("patient") String patient,
                                      @Field("appointment_time") String time,
                                      @Field("appointment_date") String date);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<List<AppointmentDetail>> getAppointmentDetails(@Field("action") String getAppDetails,
                                                        @Field("patientName") String patient);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<PostResponse> insertMedicineDetails(@Field("action") String insertMedicineDetails,
                                             @Field("medicine") String medicine,
                                             @Field("doctor") String doctor,
                                             @Field("patient") String patient,
                                             @Field("time") String time,
                                             @Field("morning") boolean morning,
                                             @Field("day") boolean day,
                                             @Field("night") boolean night,
                                             @Field("rating") float rating);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<List<MedicineDetails>> getMyMedicines(@Field("action") String getMyMedicine,
                                               @Field("patient") String patient);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<PostResponse> verifyUser(@Field("action") String verifyUser,
                                  @Field("patientEmail") String patientEmail);

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<VerificationResponse> checkVerification(@Field("action") String checkVerification,
                                                 @Field("patientEmail") String patientEmail);

}

