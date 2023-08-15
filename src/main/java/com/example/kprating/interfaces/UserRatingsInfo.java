package com.example.kprating.interfaces;

import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface UserRatingsInfo {

    public static LinkedHashMap<Integer, Double> PersonsRating(ArrayList<UserMovie> userMovies, String person){

        List<Person> directorList = userMovies.stream()
                .map((i) -> i.getPerson(person))
                .collect(Collectors.toList());
        final int amount = userMovies.size();

        /*LinkedHashMap<Person, Double> map = directorList.stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> 1,
                        Integer::sum,
                        LinkedHashMap::new
                ));*/


        return null;
    }
}
