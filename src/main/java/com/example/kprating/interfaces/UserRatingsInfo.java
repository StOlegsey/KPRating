package com.example.kprating.interfaces;

import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.*;
import java.util.stream.Collectors;

public interface UserRatingsInfo {

    public static LinkedHashMap<Person, Double> PersonsRating(ArrayList<UserMovie> userMovies, String person, Integer modifier){

        List<Person> directorList = userMovies.stream()//Список списка топ3 персон
                .map((i) -> i.getPerson(person))
                .collect(Collectors.toList());

        final Integer dirSize = directorList.size();

        LinkedHashMap<Person, Double> directorsRating = directorList.stream()// Person1, Person2
                .map((i) -> i.getId())
                .collect(Collectors.toMap(
                        k -> k,
                        v -> 1.0,
                        (sum1, sum2) -> sum1 + sum2,
                        (LinkedHashMap::new)
                ))          // (id:1, amount:3); (id:2, amount:5)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> (v.getValue() / dirSize * modifier > 0.2) ? 0.2 : v.getValue() / dirSize * modifier, //if rating > 0.2: set 0.2
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ))         // (id:1, freq:0.2); (id:2, freq:0.01)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        (k) -> (directorList.stream()
                                .filter((p)-> p.getId().equals(k.getKey()))
                                .findFirst()
                                .orElse(null)),
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        directorsRating.entrySet().stream()
                .forEach(System.out::println);

        return directorsRating;
    }
}
