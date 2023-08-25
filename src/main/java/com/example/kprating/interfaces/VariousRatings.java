package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface VariousRatings {

    private static double getPersonRating(String profession, Movie movie, ArrayList<UserMovie> userMovies) {
        ArrayList<Person> persons = movie.getPerson(profession); //{RyanG, JonnyD, JorjiC}
        LinkedHashMap<Integer, Double> personsRatings = UserRatingsInfo.PersonsRating(userMovies, profession);//{name: 0.33, name: 0.66, name:1}

        Double rating = persons.stream()
                .map(id -> personsRatings.getOrDefault(id.getId(), 0.0))
                .mapToDouble(Double::doubleValue)
                .sum();
        //System.out.println(personsRatings);
        System.out.println(persons+" rating: "+ rating+ " ("+profession+")");
        return rating;
    }

    private static double getGenreRating(Movie movie, ArrayList<UserMovie> userMovies) {

        ArrayList<String> genres = movie.getGenresName(); //{drama, phantasy, horror}
        LinkedHashMap<String, Double> genresRating = UserRatingsInfo.GenresRating(userMovies);//{name: 0.33, name: 0.66, name:1}

        Double rating = genres.stream()
                .map(name -> genresRating.getOrDefault(name, 0.0))
                .mapToDouble(Double::doubleValue)
                .sum();
        System.out.println(genres+" rating: "+ rating);
        return rating;
    }

    public static double getMovieRating(Movie movie, ArrayList<UserMovie> userMovies){

        double kpRating = 0.044 * movie.getkpRating() - 0.24;

        double movieRating = getPersonRating("actor", movie, userMovies) +
                getPersonRating("writer", movie, userMovies)
                + getPersonRating("director", movie, userMovies)
                + getGenreRating(movie, userMovies) + kpRating;

        System.out.println(movie.getName()+": "+movieRating);
        return movieRating;
    }
}
