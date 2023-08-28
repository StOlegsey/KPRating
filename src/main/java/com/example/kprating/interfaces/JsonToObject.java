package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.MovieList;
import com.example.kprating.entities.UserMovie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;

import java.util.*;

public interface JsonToObject {

    @Description("Map from Raw JSON to HashMap(UserMovieInfo, UserRating)")
    public static ArrayList<UserMovie> UserMovieRating(Integer userId) throws NumberFormatException {

        final int perpage = 200;

        int page = 0;
        int total = perpage;

        ArrayList<UserMovie> userMovieRating = new ArrayList<>();

        while(page * perpage <= total) {
            page++;
            final String response = QueueToKP.getUserMovies(userId, page, perpage).toString();

            total = Integer.parseInt(response.substring(response.indexOf(" из ") + 4, response.indexOf(" из ") + 7));

            List<Integer> movieRating = Arrays.stream(response.split("<div class=\"vote\" >"))
                    .skip(1)
                    .map(s -> s.substring(0, s.indexOf("</div>")))
                    .map(Integer::parseInt)
                    .toList();

            List<Integer> movieId = Arrays.stream(response.split("<div class=\"nameRus\"><a href=\"/(film|series)/"))
                    .skip(1)
                    .map(s -> s.substring(0, s.indexOf("/")))
                    .map(Integer::parseInt)
                    .toList();

            LinkedHashMap<Integer, Integer> userRating = new LinkedHashMap<>();
            Iterator<Integer> ik = movieId.iterator();
            Iterator<Integer> iv = movieRating.iterator();

            while (ik.hasNext() && iv.hasNext()) {
                userRating.put(ik.next(), iv.next());
            }

            ArrayList<Integer> userMoviesList = new ArrayList<>();
            userRating.forEach((key, value) -> userMoviesList.add(key));

            MovieList movieList = new Gson().fromJson(QueueToKP.getMoviesByIDs(userMoviesList).getBody(), MovieList.class); // Too many requests

            for (Movie movie : movieList.getMovieArrayList()) {
                Integer rating = userRating.get(movie.getId());
                if (rating != null) {
                    userMovieRating.add(new UserMovie(movie, rating));
                }
            }


        }

        System.out.println(userMovieRating.size()+": всего");

        return userMovieRating; // Несколько фильмов почему то пропадает
    }

    @Description("Map from Raw JSON to MovieList")
    public static MovieList AllMovies(int rating_from, ArrayList<UserMovie> userMovies, Integer total){
        final int maxOnPage = 250;
        int page = 1;

        ResponseEntity<String> response = QueueToKP.getAllMovies(rating_from,maxOnPage,page);

        MovieList movieList = new Gson().fromJson(response.getBody(), MovieList.class);

        if(total == null) {
            total = new Gson().fromJson(response.getBody(), JsonObject.class).get("total").getAsInt();
        }

        while(total > maxOnPage * page){
            page++;

            System.out.println("Loaded: "+maxOnPage*page);

            response = QueueToKP.getAllMovies(rating_from,maxOnPage,page);

            MovieList recievedMovies = new Gson().fromJson(response.getBody(), MovieList.class);

            movieList.getMovieArrayList()
                    .addAll(recievedMovies.getMovieArrayList());
        }

        movieList.getMovieArrayList().removeIf(movie ->
                userMovies.stream()
                        .map(Movie::getId)
                        .toList()
                        .contains(movie.getId())
        );

        return movieList;
    }

}
