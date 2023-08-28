package com.example.kprating.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    private static class Genre
    {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    private static class Rating
    {
        @SerializedName("kp")
        private double kp;
    }

    public double getkpRating(){
        return rating.kp;
    }

    public ArrayList<String> getGenresName() {
        return genreList.stream()
                .map(Genre::getName)
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Person> getPerson(String person){

        return personArrayList.stream()
                .filter(element -> element.getEnProfession().equals(person))
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        return "\n"+//id=" + id +
                '\'' + name + '\'' +
                '(' + year + ')'+
                ", KP: " + rating.kp+
                "\t|  Your Rating ";
                //", personsArrayList=" + personArrayList;
    }
}
