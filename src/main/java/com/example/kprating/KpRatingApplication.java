package com.example.kprating;

import com.example.kprating.entities.MovieList;
import com.example.kprating.interfaces.JsonToObject;
import com.example.kprating.interfaces.QueueToKP;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedHashMap;

@SpringBootApplication
public class KpRatingApplication {

    public static void main(String[] args) throws JsonProcessingException {

        SpringApplication.run(KpRatingApplication.class, args);

        LinkedHashMap<Integer, Integer> userMovies = JsonToObject.UserMoviesMap(QueueToKP.getUserMovies(15935377));
        System.out.println(userMovies);
    }

}
