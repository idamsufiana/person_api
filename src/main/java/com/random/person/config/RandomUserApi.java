package com.random.person.config;

import com.random.person.dto.RandomUserResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUserApi {

    @GET("api/")
    Call<RandomUserResponse> getRandomUser();
}
