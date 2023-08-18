package com.example.kprating.interfaces;

import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface UserRatingsInfo {

    public static LinkedHashMap<Person, Double> PersonsRating(ArrayList<UserMovie> userMovies, String person, Integer modifier){

        List<List<Person>> personList = userMovies.stream()//Список списка топ3 персон
                .map((i) -> i.getPerson(person))
                .collect(Collectors.toList());

        System.out.println(personList);

        final Integer dirSize = personList.size();

        LinkedHashMap<Person, Double> personsRating = personList.stream()// Person1, Person2
                .flatMap(List::stream)
                .map(i -> i.getId())
                .collect(Collectors.toMap(
                                Function.identity(),
                                p -> {
                                    int index = personList.stream()
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList())
                                            .indexOf(p);
                                    return index == 0 ? 1.0 : (1.0 - (index / (double) personList.size()));
                                },
                                (v1, v2) -> v1 + v2,
                                LinkedHashMap::new))
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
                        (k) -> (personList.stream()
                                .flatMap(List::stream)
                                .filter((p)-> p.getId().equals(k.getKey()))
                                .findFirst()
                                .orElse(null)),
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        personsRating.entrySet().stream()
                .forEach(System.out::println);

        return personsRating;
    }
}
