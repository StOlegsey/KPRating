package com.example.kprating;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.MovieList;
import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;
import com.example.kprating.interfaces.JsonToObject;
import com.example.kprating.interfaces.UserRatingsInfo;
import com.example.kprating.interfaces.VariousRatings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class KpRatingApplication {

    public static void main(String[] args) {

        SpringApplication.run(KpRatingApplication.class, args);

        ArrayList<UserMovie> userMovies = JsonToObject.UserMovieRating(15935377);
        //System.out.println(userMovies);

        //System.out.println(UserRatingsInfo.GenresRating(userMovies, 2));
        //System.out.println(UserRatingsInfo.PersonsRating(userMovies, "actor", 2));


        MovieList allMovies = JsonToObject.AllMovies(7);

        LinkedHashMap<String, Double> allMovieRatings = new LinkedHashMap<>();
        for (Movie movie : allMovies.getMovieArrayList()) {
                double rating = VariousRatings.getMovieRating(movie, userMovies);
                allMovieRatings.put(movie.getName(), rating);
        }
        LinkedHashMap<String, Double> sortedMovieRatings = new LinkedHashMap<>();
        allMovieRatings.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMovieRatings.put(x.getKey(), x.getValue()));
        System.out.println(sortedMovieRatings);
    }

}
