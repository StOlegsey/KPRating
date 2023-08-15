package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.MovieList;
import com.example.kprating.entities.UserMovie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

public interface JsonToObject {

    @Description("Map from Raw JSON to HashMap(UserMovieInfo, UserRating)")
    public static ArrayList<UserMovie> UserMovieRating(Integer userId) throws NumberFormatException {

        final String response = QueueToKP.getUserMovies(userId).toString();

        List<Integer> movieRating = Arrays.stream(response.split("<div class=\"vote\" >"))
                .skip(1)
                .map(s -> s.substring(0, s.indexOf("</div>")))
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        List<Integer> movieId = Arrays.stream(response.split("<div class=\"nameRus\"><a href=\"/(film|series)/"))
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

        ArrayList<Integer> userMoviesList = new ArrayList<>();
        userRating.forEach((key, value) -> userMoviesList.add(key));

        MovieList movieList = new Gson().fromJson(QueueToKP.getMoviesByIDs(userMoviesList).getBody(), MovieList.class);

        ArrayList<UserMovie> userMovieRating = new ArrayList<>();

        for (Movie movie : movieList.getMovieArrayList()) {
            Integer rating = userRating.get(movie.getId());
            if (rating != null) {
                userMovieRating.add(new UserMovie(movie, rating));
            }
        }

        return userMovieRating;
    }

    @Description("Map from Raw JSON to Movie.ArrayList")
    public static MovieList AllMovies(int rating_from){
        final int maxOnPage = 250;
        int page = 1;

        ResponseEntity<String> response = QueueToKP.getAllMovies(rating_from,maxOnPage,page);

        MovieList movieList = new Gson().fromJson(response.getBody(), MovieList.class);

        int total = 1000;//new Gson().fromJson(response.getBody(), JsonObject.class).get("total").getAsInt();

        while(total > maxOnPage * page){
            page++;

            System.out.println("Loaded: "+maxOnPage*page);

            response = QueueToKP.getAllMovies(rating_from,maxOnPage,page);

            movieList.getMovieArrayList()
                    .addAll(new Gson().fromJson(response.getBody(), MovieList.class).getMovieArrayList());
        }

        return movieList;
    }

}
