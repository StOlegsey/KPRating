package com.example.kprating.interfaces;

import com.example.kprating.entities.MovieList;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public interface JsonToObject {

    public static LinkedHashMap<Integer, Integer> UserMoviesMap(ResponseEntity<String> responseJSON){

        final String response = responseJSON.toString();

        List<Integer> movieRating = Arrays.stream(response.split("<div class=\"vote\" >"))
                .skip(1)
                .map(s -> s.substring(0, s.indexOf("</div>")))
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        List<Integer> movieId = Arrays.stream(response.split("<div class=\"nameRus\"><a href=\"/film/"))
                .skip(1)
                .map(s -> s.substring(0, s.indexOf("/")))
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        LinkedHashMap<Integer, Integer> userRating = new LinkedHashMap<>();
        Iterator<Integer> ik = movieId.iterator();
        Iterator<Integer> iv = movieRating.iterator();

        while (ik.hasNext() && iv.hasNext()) {
            userRating.put(ik.next(), iv.next());
        }

        return userRating;
    }

    public static MovieList AllMovies(ResponseEntity<String> response){

        MovieList movieList = new Gson().fromJson(response.getBody(), MovieList.class);

        return movieList;
    }

}
