package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface VariousRatings {

    private static double getActorRating() {
        return 0.0;
    }

    private static double getDirectorRating(Movie movie, ArrayList<UserMovie> userMovies)
    {
        Person director = movie.getPerson("director");
        LinkedHashMap<Person, Double> userRatings = UserRatingsInfo.PersonsRating(userMovies, "director", 2);
        Double rating = userRatings.entrySet().stream()
                .filter(p -> p.equals(director))
                .map(i -> i.getValue())
                .findFirst()
                .orElse(null);
        return rating;
    }

    private static double getWriterRating(Movie movie, ArrayList<UserMovie> userMovies) {
        Person writer = movie.getPerson("writer");
        LinkedHashMap<Person, Double> userRatings = UserRatingsInfo.PersonsRating(userMovies, "writer", 2);
        Double rating = userRatings.entrySet().stream()
                .filter(p -> p.equals(writer))
                .map(i -> i.getValue())
                .findFirst()
                .orElse(null);
        return rating;
    }

    private static double getGenreRating() {
        return 0.0;
    }

    public static double getMovieRating(Movie movie){

        ArrayList<UserMovie> userMovies = JsonToObject.UserMovieRating(15935377);

        double kpRating = 0.044 * movie.getkpRating() - 0.24;

        double movieRating = getActorRating()+ getWriterRating(movie, userMovies) + getDirectorRating(movie, userMovies) + getGenreRating() + kpRating;

        return movieRating;
    }
}
