package com.example.kprating.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Person {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("enProfession")
    private String enProfession;

    @Override
    public String toString() {
        return "Persons{" +
                "name='" + name + '\'' +
                ", enProfession='" + enProfession + '\'' +
                '}';
    }


}
