package com.example.kprating;

import com.example.kprating.entities.MovieList;
import com.example.kprating.entities.UserMovie;
import com.example.kprating.interfaces.JsonToObject;
import com.example.kprating.interfaces.QueueToKP;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootApplication
public class KpRatingApplication {

    public static void main(String[] args) {

        SpringApplication.run(KpRatingApplication.class, args);

        //ArrayList<UserMovie> userMovies = JsonToObject.UserMovieRating(15935377);
        //System.out.println(userMovies);

        MovieList allMovies = JsonToObject.AllMovies(7);

    }

}
