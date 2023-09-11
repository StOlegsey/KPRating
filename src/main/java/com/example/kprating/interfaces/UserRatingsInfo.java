package com.example.kprating.interfaces;

import com.example.kprating.entities.Movie;
import com.example.kprating.entities.Person;
import com.example.kprating.entities.UserMovie;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface UserRatingsInfo {

    public static LinkedHashMap<Integer, Double> PersonsRating(ArrayList<UserMovie> userMovies, String person){

        List<List<Person>> personList = userMovies.stream()//Список списка топ3 персон
                .filter(i -> i.getUserScore() >= Constants.GoodMovieRating)
                .map((i) -> i.getPerson(person))
                .collect(Collectors.toList());

        //System.out.println(personList);

        final Integer dirSize = personList.size();

        LinkedHashMap<Integer, Double> personsRating = personList.stream()// Person1, Person2
                .flatMap(List::stream)
                .map(Person::getId)
                .collect(Collectors.toMap(
                                Function.identity(),
                                p -> {
                                    int index = personList.stream()
                                            .flatMap(List::stream)
                                            .toList()
                                            .indexOf(p);
                                    return index == 0 ? 1.0 : (1.0 - (index / (double) personList.size())); // if number of role is 1: value 1; 2: 0.66; 3: 0.33
                                },
                        Double::sum,
                                LinkedHashMap::new))
                .entrySet() //(id:1, amount: 2); (id:2, amount: 4)
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> Math.min(v.getValue() / dirSize * Constants.modifierForRating, 0.2), //if rating > 0.2: set 0.2
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        //personsRating.entrySet().stream()
               // .forEach(System.out::println);

        return personsRating;
    }

    public static LinkedHashMap<String, Double> GenresRating(ArrayList<UserMovie> userMovies){

        List<List<String>> genreList = userMovies.stream()//Список списка топ3 жанров фильмов
                .filter(i -> i.getUserScore() >= Constants.GoodMovieRating)
                .map(Movie::getGenresName)
                .collect(Collectors.toList());

        final Integer dirSize = genreList.size();

        LinkedHashMap<String, Double> genreRating = genreList.stream()// Person1, Person2
                .flatMap(List::stream)
                .collect(Collectors.toMap(
                        Function.identity(),
                        p -> {
                            int index = genreList.stream()
                                    .flatMap(List::stream)
                                    .toList()
                                    .indexOf(p);
                            return index == 0 ? 1.0 : (1.0 - (index / (double) genreList.size())); // if number of genre is 1: value 1; 2: 0.66; 3: 0.33
                        },
                        Double::sum,
                        LinkedHashMap::new))
                .entrySet() //(name:drama, amount: 2); (name:fantasy, amount: 4)
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> Math.min(v.getValue() / dirSize * Constants.modifierForRating, 0.2), //if rating > 0.2: set 0.2
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ))         // (name:drama, freq:0.2); (name:fantasy, freq:0.01)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        System.out.println(genreRating);

        //genreRating.entrySet().stream()
               // .forEach(System.out::println);

        return genreRating;
    }
}
