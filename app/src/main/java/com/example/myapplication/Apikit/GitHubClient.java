package com.example.myapplication.Apikit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubClient {
    String API_BASE_URL = "https://api.github.com/";

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    );

    Retrofit retrofit =
            builder
                    .client(
                            httpClient.build()
                    )
                    .build();

    GitHubClient client =  retrofit.create(GitHubClient.class);
}
