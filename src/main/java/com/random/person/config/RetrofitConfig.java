package com.random.person.config;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class RetrofitConfig {

   @Value("${url.random.user}")
   private String baseUrl;

    @Value("${http.client.connect-timeout}")
    private Long connectTimeout;

    @Value("${http.client.read-timeout}")
    private Long readTImeout;

    @Value("${http.client.retry-on-failure}")
    private Boolean retry;

    @Bean
    public RandomUserApi randomUserApi(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTImeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(retry)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RandomUserApi.class);

    }
}
