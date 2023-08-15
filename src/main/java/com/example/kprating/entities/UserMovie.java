package com.example.kprating.entities;

import java.util.List;

public class UserMovie extends Movie{

    private int userScore;

    public UserMovie(){

    }

    public UserMovie(Movie movie, int userScore) {
        super(movie.getId(), movie.getName(), movie.getYear(), movie.getRating(), movie.getPersonArrayList());
        this.userScore = userScore;
    }

    @Override
    public String toString() {
        return "\n"+ super.getName() + " Score: " + userScore;
    }
}
