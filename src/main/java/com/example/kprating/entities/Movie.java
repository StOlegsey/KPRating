package com.example.kprating.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private int year;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("persons")
    private List<Person> personArrayList = new ArrayList<>();

    @SerializedName("genres")
    private List<Genre> genreList = new ArrayList<>();

    private class Genre
    {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    private class Rating
    {
        @SerializedName("kp")
        private double kp;
    }

    public double getkpRating(){
        return rating.kp;
    }

    public ArrayList<String> getGenresName() {
        return genreList.stream()
                .map(e-> e.getName())
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Person> getPerson(String person){

        ArrayList<Person> personList = personArrayList.stream()
                .filter(element -> element.getEnProfession().equals(person))
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));

        return personList;
    }

    @Override
    public String toString() {
        return "\nid=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", rating=" + rating.kp +
                ", personsArrayList=" + personArrayList;
    }
}
