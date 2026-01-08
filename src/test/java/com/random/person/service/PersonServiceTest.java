package com.random.person.service;


import com.random.person.config.RandomUserApi;
import com.random.person.dto.PersonResponse;
import com.random.person.dto.RandomUserResponse;
import com.random.person.exception.ExternalApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private RandomUserApi randomUserApi;

    @Mock
    private Call<RandomUserResponse> call;

    @InjectMocks
    private PersonService personService;

    private RandomUserResponse mockResponse;

    @BeforeEach
    void setup(){
        mockResponse = buildRandomResponse();
    }

    private RandomUserResponse buildRandomResponse(){
        RandomUserResponse response = new RandomUserResponse();
        RandomUserResponse.Name name = new RandomUserResponse.Name();
        name.setFirst("ida");
        name.setLast("sufiana");
        name.setTitle("Ms");
        RandomUserResponse.Picture picture = new RandomUserResponse.Picture();
        picture.setLarge("");

        RandomUserResponse.Location location = new RandomUserResponse.Location();
        location.setCity("Jakarta");
        location.setState("");
        location.setStreet(new RandomUserResponse.Street());
        location.setPostcode(1234);
        RandomUserResponse.Result result = new RandomUserResponse.Result();
        result.setName(name);
        result.setLocation(location);
        result.setPicture(picture);
        result.setGender("female");
        response.setResults(List.of(result));

        return response;
    }

    @Test
    void getPerson_success() throws IOException {
        when(randomUserApi.getRandomUser()).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(mockResponse));

        PersonResponse response = personService.getPerson();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("female", response.getGender());
        Assertions.assertEquals("Ms ida sufiana", response.getFullname());

    }

    @Test
    void getPerson_error() throws IOException {
        when(randomUserApi.getRandomUser()).thenReturn(call);
        when(call.execute()).thenThrow(new IOException("timeout"));

        ExternalApiException ex = assertThrows(ExternalApiException.class, ()-> personService.getPerson());
        Assertions.assertTrue(ex.getMessage().contains("Error calling random api"));

    }
}
