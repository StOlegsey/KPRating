package com.example.kprating.services;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.MovieList;
import com.example.kprating.entities.UserMovie;
import com.example.kprating.interfaces.Constants;
import com.example.kprating.interfaces.JsonToObject;
import com.example.kprating.interfaces.UserRatingsInfo;
import com.example.kprating.interfaces.VariousRatings;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class getRatingService {

    public LinkedHashMap<Movie, Double> getAllRatings(Integer userId, Integer total){

        ArrayList<UserMovie> userMovies = JsonToObject.UserMovieRating(userId);
        MovieList allMovies = JsonToObject.AllMovies(Constants.GoodMovieRating, userMovies, total);

        LinkedHashMap<Integer, Double> actorRatings = UserRatingsInfo.PersonsRating(userMovies, Constants.profession.get(0));
        LinkedHashMap<Integer, Double> writerRatings = UserRatingsInfo.PersonsRating(userMovies, Constants.profession.get(1));
        LinkedHashMap<Integer, Double> directorRatings = UserRatingsInfo.PersonsRating(userMovies, Constants.profession.get(2));
        LinkedHashMap<String, Double> genresRating = UserRatingsInfo.GenresRating(userMovies);//{name: 0.33, name: 0.66, name:1}

        LinkedHashMap<Movie, Double> allMovieRatings = new LinkedHashMap<>();

        for(Movie movie : allMovies.getMovieArrayList()) {
            double rating = VariousRatings.getMovieRating(movie, userMovies, actorRatings, writerRatings, directorRatings, genresRating);
            allMovieRatings.put(movie, rating);
        }

        LinkedHashMap<Movie, Double> sortedMovieRatings = new LinkedHashMap<>();

            allMovieRatings.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMovieRatings.put(x.getKey(), x.getValue()));
        //System.out.println(sortedMovieRatings);

        return sortedMovieRatings;
    }
}
