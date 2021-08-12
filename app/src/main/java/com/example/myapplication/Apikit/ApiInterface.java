package com.example.myapplication.Apikit;



import com.example.myapplication.Models.MyResponse;
import com.example.myapplication.Models.Response201;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Belal on 10/5/2017.
 */

public interface ApiInterface {

    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
    String BASE_URL = "https://kencorpus.co.ke/";
    //String BASE_URL = "http://cb44e617b5dd.ngrok.io/ImageUploadApi/";192.168.8.1
    //String BASE_URL = "http://192.168.8.101/api/";
    String FILENAME="audio.mp3";


    //this is our multipart request
    //we have two parameters on is name and other one is description
    //@Headers({"Accept: application/json"})
    @Multipart
    @POST("files")
    Call<MyResponse> uploadFile(@Part MultipartBody.Part file);
    //,@Part("file") RequestBody name);

   // @POST("ApiInterface.php?apicall=upload")
    @Multipart
    @POST("files")
  // Call<MyResponse> uploadImage(
          //  @Part RequestBody file);
   // Call<MyResponse> uploadImage(@Part("fielddata\"; filename=\"my.pdf\" ") RequestBody fielddata);

    Call<MyResponse> uploadAnyFileType(@Part("file\"; filename=" + FILENAME) RequestBody file);
    @Multipart
    //@POST("/api/fileupload")
    @POST("file")
    //Call<ResponseBody> postFile(@Part MultipartBody.Part file, @Part("description") RequestBody description);
Call<MyResponse> postFile(@Part MultipartBody.Part file, @Part("description") RequestBody description);
    @Multipart
    @POST("recordmetadata")
    Call<Response201> postrecordmetadata(@Part MultipartBody.Part file,
                                         @Part("username") RequestBody username,
                                         @Part("speakername") RequestBody speakername,
                                         @Part("interviewdate") RequestBody interviewdate,
                                         @Part("source") RequestBody source,
                                         @Part("location") RequestBody location,
                                         @Part("agegroup") RequestBody agegroup,
                                         @Part("language") RequestBody language,
                                         @Part("gender") RequestBody gender,
                                         @Part("topic") RequestBody topic,
                                         @Part("category") RequestBody category);
}
