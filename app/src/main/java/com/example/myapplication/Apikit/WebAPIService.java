package com.example.myapplication.Apikit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WebAPIService {

    @Multipart
    //@POST("/api/fileupload")
    @POST("files")
    Call<ResponseBody> postFile(@Part MultipartBody.Part file,
                                @Part("description") RequestBody description);
    @Multipart
    @POST("recordmetadata")

    Call<ResponseBody> postrecordmetadata(@Part MultipartBody.Part file,
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