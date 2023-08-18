package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface VariousRatings {

    private static double getPersonRating(String per, Movie movie, ArrayList<UserMovie> userMovies) {
        ArrayList<Person> writer = movie.getPerson(per);
        LinkedHashMap<Person, Double> userRatings = UserRatingsInfo.PersonsRating(userMovies, per, 2);
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

        double movieRating = getPersonRating("actor", movie, userMovies) +
                getPersonRating("writer", movie, userMovies)
                + getPersonRating("director", movie, userMovies)
                + getGenreRating() + kpRating;

        return movieRating;
    }
}
