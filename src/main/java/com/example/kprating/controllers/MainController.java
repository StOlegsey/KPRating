package com.example.kprating.controllers;


import com.example.kprating.services.getRatingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    getRatingService getRating = new getRatingService();

    @GetMapping("/api/ratingsFor/user")
    public String ratingsForUser(@RequestParam("userId") Integer userId, @RequestParam("totalMovies") Integer total){

        return getRating.getAllRatings(userId, total).toString();
    }
}
