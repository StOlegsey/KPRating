package com.example.kprating;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.MovieList;
import com.example.kprating.entities.UserMovie;
import com.example.kprating.interfaces.Constants;
import com.example.kprating.interfaces.JsonToObject;
import com.example.kprating.interfaces.UserRatingsInfo;
import com.example.kprating.interfaces.VariousRatings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class KpRatingApplication {

    public static void main(String[] args) {

        SpringApplication.run(KpRatingApplication.class, args);


        ArrayList<UserMovie> userMovies = JsonToObject.UserMovieRating(Constants.userId);
        //System.out.println(userMovies);
        //System.out.println(userMovies.size());
        //System.out.println(userMovies);

        //System.out.println(UserRatingsInfo.GenresRating(userMovies, 2));
        //System.out.println(UserRatingsInfo.PersonsRating(userMovies, "actor", 2));


        MovieList allMovies = JsonToObject.AllMovies(Constants.GoodMovieRating, userMovies);

        LinkedHashMap<Integer, Double> actorRatings = UserRatingsInfo.PersonsRating(userMovies, Constants.profession.get(0));
        LinkedHashMap<Integer, Double> writerRatings = UserRatingsInfo.PersonsRating(userMovies, Constants.profession.get(1));
        LinkedHashMap<Integer, Double> directorRatings = UserRatingsInfo.PersonsRating(userMovies, Constants.profession.get(2));
        LinkedHashMap<String, Double> genresRating = UserRatingsInfo.GenresRating(userMovies);//{name: 0.33, name: 0.66, name:1}

        LinkedHashMap<String, Double> allMovieRatings = new LinkedHashMap<>();
        for (Movie movie : allMovies.getMovieArrayList()) {
                double rating = VariousRatings.getMovieRating(movie, userMovies, actorRatings, writerRatings, directorRatings, genresRating);
                allMovieRatings.put(movie.getName(), rating);
        }

        LinkedHashMap<String, Double> sortedMovieRatings = new LinkedHashMap<>();

        allMovieRatings.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMovieRatings.put(x.getKey(), x.getValue()));
        //System.out.println(sortedMovieRatings);
    }

}
