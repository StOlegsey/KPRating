package com.example.kprating;

import com.example.kprating.entities.UserMovie;
import com.example.kprating.interfaces.JsonToObject;
import com.example.kprating.interfaces.UserRatingsInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class KpRatingApplication {

    public static void main(String[] args) {

        SpringApplication.run(KpRatingApplication.class, args);

        ArrayList<UserMovie> userMovies = JsonToObject.UserMovieRating(15935377);
        System.out.println(userMovies);

        //System.out.println(UserRatingsInfo.PersonsRating(userMovies, "director", 2));
        //System.out.println(UserRatingsInfo.PersonsRating(userMovies, "actor", 2));

        //MovieList allMovies = JsonToObject.AllMovies(7);

    }

}
