package com.example.kprating.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private class Rating
    {
        @SerializedName("kp")
        private double kp;
    }

    public double getkpRating(){
        return rating.kp;
    }


    public Person getPerson(String person){//Возвращаем список топ 3 персон
        Optional<Person> optionalDirector = personArrayList.stream()
                .filter(element -> element.getEnProfession().equals(person))
                .findFirst();
        Person director = optionalDirector.orElseThrow(() -> new NullPointerException());

        return director;
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
