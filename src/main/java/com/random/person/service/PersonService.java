package com.random.person.service;

import com.random.person.config.RandomUserApi;
import com.random.person.dto.PersonResponse;
import com.random.person.dto.RandomUserResponse;
import com.random.person.exception.ExternalApiException;
import retrofit2.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PersonService {

    private final RandomUserApi randomUserApi;

    public PersonService(RandomUserApi randomUserApi) {
        this.randomUserApi = randomUserApi;
    }

    public PersonResponse getPerson(){
        RandomUserResponse response = getData();
        RandomUserResponse.Result result = extractFirstResult(response);
        return mapToPersonResponse(result);
    }

    private RandomUserResponse getData(){
        try{

            Response<RandomUserResponse> response = randomUserApi.getRandomUser().execute();

            if(!response.isSuccessful() || response.body() == null) {
                throw new ExternalApiException ("Random User api failed with Status " + response.code() +" "+ response.message());
            }

            return  response.body();

        }catch (IOException e){
            throw new ExternalApiException("Error calling random api", e);
        }
    }

    private RandomUserResponse.Result extractFirstResult(RandomUserResponse response){
        if(response.getResults() == null || response.getResults().isEmpty()){
            throw new ExternalApiException("Random API return empty result");

        }
        return response.getResults().get(0);
    }

    private PersonResponse mapToPersonResponse(RandomUserResponse.Result result){
        return new PersonResponse(result.getGender(),
                buildFullName(result),buildAddress(result),
                result.getPicture().getLarge());

    }

    private String buildFullName(RandomUserResponse.Result result){
        return String.format("%s %s %s",
                safe(result.getName().getTitle()),
                safe(result.getName().getFirst()),
                safe(result.getName().getLast()));
    }

    private String buildAddress(RandomUserResponse.Result result){
        if (result== null || result.getLocation() == null){
            return "-";
        }
        RandomUserResponse.Street street = result.getLocation().getStreet();
        String streetFinal = street != null
                ? String.format("%s %s",
                safe(street.getName()),
                safe(street.getNumber()))
                : "";
        String city = safe(result.getLocation().getCity());
        return String.format("%s %s",
                streetFinal,
                city).trim();
    }

    private String safe(Object value){
        return value == null ? "" : value.toString();
    }
}
