package com.myapp.doctorapp.backgroundtasks;

import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.interfaces.ApiInterface;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.AppointmentDetail;
import com.myapp.doctorapp.model.Doctor;
import com.myapp.doctorapp.model.EmailPasswordResponse;
import com.myapp.doctorapp.model.IdModel;
import com.myapp.doctorapp.model.MedicineDetails;
import com.myapp.doctorapp.model.PostResponse;
import com.myapp.doctorapp.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.doctorapp.Globals.API_GET_DOCTOR_LIST;
import static com.myapp.doctorapp.Globals.API_GET_EMAIL;
import static com.myapp.doctorapp.Globals.API_GET_ID_LIST;
import static com.myapp.doctorapp.Globals.API_GET_USER;
import static com.myapp.doctorapp.Globals.API_INSERT;
import static com.myapp.doctorapp.Globals.API_UPDATE_PROFILE;
import static com.myapp.doctorapp.Globals.GET_APPOINT_DETAILS;
import static com.myapp.doctorapp.Globals.GET_MY_MEDICINE;
import static com.myapp.doctorapp.Globals.GET_USER_USING_ID;
import static com.myapp.doctorapp.Globals.INSERT_MEDICINE;
import static com.myapp.doctorapp.Globals.SET_APPOINTMENT;
import static com.myapp.doctorapp.Globals.anInt;

public class ApiBackgroundTask {
    private ApiInterface apiInterface;

    public ApiBackgroundTask(){
        apiInterface= ApiClient.getRetrofitObj().create(ApiInterface.class);
    }

    public void insertResponse(final Bundle bundle, final OnDataRetrievedListener listener) {
        if (listener != null) {
            String height=(bundle.getString("height")==null)?"0":bundle.getString("height");
            String weight=(bundle.getString("weight")==null)?"0":bundle.getString("weight");
            String password=(bundle.getString("password")==null)?"":bundle.getString("password");
            String mobile=(bundle.getString("mobile")==null)?"":bundle.getString("mobile");
            String id=(bundle.getString("id")==null)?(anInt++)+"":bundle.getString("id");
            String image=(bundle.getString("image")==null)?"R.drawable.default_image":bundle.getString("image");

            apiInterface.insertData("insertData",
                    bundle.getString("name"),
                    mobile,
                    id,
                    bundle.getString("birthday"),
                    bundle.getString("gender"),
                    height,
                    weight,
                    password,
                    image,
                    bundle.getString("email"))
            .enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    Bundle b=new Bundle();
                    b.putString("errorMsg", response.body().getErrorMsg());
                    b.putBoolean("success", response.body().isSuccess());
                    listener.onDataRetrieved(API_INSERT, b);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
//                    TODO toast error message and check throwable
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });

        }
    }

    public void getEmailPassword(final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.getEmailPasswordList("getEmailList").enqueue(new Callback<List<EmailPasswordResponse>>() {
                @Override
                public void onResponse(Call<List<EmailPasswordResponse>> call, Response<List<EmailPasswordResponse>> response) {
                    List<EmailPasswordResponse> list=response.body();
                    Bundle b=new Bundle();
                    b.putSerializable("email_password_list", (Serializable) list);
                    listener.onDataRetrieved(API_GET_EMAIL, b);
                }

                @Override
                public void onFailure(Call<List<EmailPasswordResponse>> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void getUser(String email, final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.getUser("getRecordWithEmail", email).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user=response.body();
                    Bundle b=new Bundle();
                    b.putSerializable("User",user);
                    Log.e("TAG", "onResponse: "+user.getDOB());
                    listener.onDataRetrieved(API_GET_USER, b);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void getDoctorList(final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.getDoctorList("getDoctorList").enqueue(new Callback<List<Doctor>>() {
                @Override
                public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                    List<Doctor> list=response.body();
                    Bundle b=new Bundle();
                    b.putSerializable("doctor_list", (Serializable) list);
                    listener.onDataRetrieved(API_GET_DOCTOR_LIST, b);
                }

                @Override
                public void onFailure(Call<List<Doctor>> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void updateProfile(String height, String weight, String bloodGroup, String email, final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.updateProfile("editProfile", weight, height, bloodGroup, email).enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    PostResponse feedback=response.body();
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("success", feedback.isSuccess());
                    bundle.putString("errorMsg", feedback.getErrorMsg());
                    listener.onDataRetrieved(API_UPDATE_PROFILE, bundle);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage() );
                }
            });
        }
    }

    public  void getIdList(final OnDataRetrievedListener listener){
        if (listener!=null){
           apiInterface.getIdList("getIdList").enqueue(new Callback<List<IdModel>>() {
               @Override
               public void onResponse(Call<List<IdModel>> call, Response<List<IdModel>> response) {
                   List<IdModel> idList=response.body();
                   Bundle bundle=new Bundle();
                   bundle.putSerializable("id_list", (Serializable) idList);
                   listener.onDataRetrieved(API_GET_ID_LIST, bundle);
               }

               @Override
               public void onFailure(Call<List<IdModel>> call, Throwable t) {
                   Log.e("TAG", "onFailure: "+t.getMessage() );
               }
           });
        }
    }

    public void getUserUsingId(String id, final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.getUserUsingId("getRecordWithId", id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user=response.body();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("user", user);
                    listener.onDataRetrieved(GET_USER_USING_ID, bundle);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void setAppointment(Bundle bundle, final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.setAppointment("setAppointment",
                    bundle.getString("doctorName"),
                    bundle.getString("doctorFee"),
                    bundle.getString("patient"),
                    bundle.getString("appointment_time"),
                    bundle.getString("appointment_date")
                    ).enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    PostResponse postResponse=response.body();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("response", (Serializable) postResponse);
                    Log.e("TAG", "onResponse: "+postResponse.getErrorMsg());
                    listener.onDataRetrieved(SET_APPOINTMENT, bundle);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void getAppointmentDetails(String patient, final OnDataRetrievedListener listener){
        if (listener!=null) {
            apiInterface.getAppointmentDetails("getAppDetails", patient)
                    .enqueue(new Callback<List<AppointmentDetail>>() {
                        @Override
                        public void onResponse(Call<List<AppointmentDetail>> call, Response<List<AppointmentDetail>> response) {
                            Bundle b=new Bundle();
                            b.putSerializable("DetailList", (Serializable) response.body());
                            List<AppointmentDetail> list=response.body();
                            for (AppointmentDetail d:list
                                 ) {
                                Log.e("TAG", "onResponse: "+d.toString()+"\n");
                            }
                            listener.onDataRetrieved(GET_APPOINT_DETAILS, b);
                        }

                        @Override
                        public void onFailure(Call<List<AppointmentDetail>> call, Throwable t) {
                            Log.e("TAG", "onFailure: "+ t.getMessage());
                        }
                    });
        }
    }

    public void insertMedicineDetails(MedicineDetails details, final OnDataRetrievedListener listener){
        if(listener!=null){
            apiInterface.insertMedicineDetails("insertMedicineDetails", details.getMedicine(), details.getDoctor(), details.getPatient(), details.getTime(), details.isMorning(), details.isDay(), details.isNight(), details.getRating())
            .enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    Bundle b=new Bundle();
                    b.putSerializable("response", (Serializable) response.body());
                    listener.onDataRetrieved(INSERT_MEDICINE, b);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void getMyMedicine(String patient, final OnDataRetrievedListener listener){
        if(listener!=null){
            apiInterface.getMyMedicines("getMyMedicine", patient)
                    .enqueue(new Callback<List<MedicineDetails>>() {
                        @Override
                        public void onResponse(Call<List<MedicineDetails>> call, Response<List<MedicineDetails>> response) {
                            Log.e("TAG", "onResponse: "+response.toString());
                            List<MedicineDetails> medicineList=medicineList=response.body();
                            Bundle b=new Bundle();
                            b.putSerializable("medicineList", (Serializable) medicineList);
                            listener.onDataRetrieved(GET_MY_MEDICINE, b);
                        }

                        @Override
                        public void onFailure(Call<List<MedicineDetails>> call, Throwable t) {
                            Log.e("TAG", "onFailure: "+t.getMessage());
                        }
                    });
        }
    }
}
