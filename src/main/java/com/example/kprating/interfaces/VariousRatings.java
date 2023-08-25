package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface VariousRatings {

    private static double getPersonRating(String profession, Movie movie, LinkedHashMap<Integer, Double> personsRatings) {
        ArrayList<Person> persons = movie.getPerson(profession); //{RyanG, JonnyD, JorjiC}

        Double rating = persons.stream()
                .map(id -> personsRatings.getOrDefault(id.getId(), 0.0))
                .mapToDouble(Double::doubleValue)
                .sum();
        //System.out.println(personsRatings);
        //System.out.println(persons+" rating: "+ rating+ " ("+profession+")");
        return rating;
    }

    private static double getGenreRating(Movie movie, LinkedHashMap<String, Double> genresRating) {

        ArrayList<String> genres = movie.getGenresName(); //{drama, phantasy, horror}

        Double rating = genres.stream()
                .map(name -> genresRating.getOrDefault(name, 0.0))
                .mapToDouble(Double::doubleValue)
                .sum();
        //System.out.println(genres+" rating: "+ rating);
        return rating;
    }

    public static double getMovieRating(Movie movie, ArrayList<UserMovie> userMovies, LinkedHashMap<Integer, Double> actorRatings, LinkedHashMap<Integer, Double> writerRatings,LinkedHashMap<Integer, Double> directorRatings, LinkedHashMap<String, Double> genresRating){

        double kpRating = 0.044 * movie.getkpRating() - 0.24;

        double movieRating = getPersonRating("actor", movie, actorRatings) +
                getPersonRating("writer", movie, writerRatings)
                + getPersonRating("director", movie, directorRatings)
                + getGenreRating(movie, genresRating) + kpRating;

        //System.out.println(movie.getName()+": "+movieRating);
        return movieRating;
    }
}
