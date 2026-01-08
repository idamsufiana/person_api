package com.random.person.dto;

import lombok.Data;

import java.util.List;

@Data
public class RandomUserResponse {

    private List<Result> results;
    private Info info;

    @Data
    public static class Result{
        private String gender;
        private Name name;
        private Location location;
        private String email;
        private Picture picture;
        private String nat;
    }

    @Data
    public static class Name{
        private String title;
        private String first;
        private String last;
    }

    @Data
    public static class Location{
        private Street street;
        private String city;
        private String state;
        private String postcode;
    }

    @Data
    public static class Picture{
        private String large;
        private String medium;
        private String thumbnail;
    }

    @Data
    public static class Info{
        private String seed;
        private Integer results;
        private Integer page;
        private String version;
    }

    @Data
    public static class Street{
        private Integer number;
        private String name;
    }
}
