package com.example.kprating.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieList {

    @SerializedName("docs")
    private List<Movie> movieArrayList = new ArrayList<>();

    @Override
    public String toString() {
        return movieArrayList + "";
    }
}
