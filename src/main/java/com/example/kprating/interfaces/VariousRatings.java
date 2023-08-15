package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.Person;

public interface VariousRatings {

    private static double getActorRating() {
        return 0.0;
    }

    private static double getDirectorRating(Movie movie)
    {
        Person director = movie.getPerson("director");

        return 0.0;
    }

    private static double getWriterRating() {
        return 0.0;
    }

    private static double getGenreRating() {
        return 0.0;
    }

    public static double getMovieRating(Movie movie){

        double kpRating = 0.044 * movie.getkpRating() - 0.24;

        double movieRating = getActorRating()+ getWriterRating() + getDirectorRating(movie) + getGenreRating() + kpRating;

        return movieRating;
    }
}
