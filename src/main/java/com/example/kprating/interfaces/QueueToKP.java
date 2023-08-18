package com.example.kprating.interfaces;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

import java.util.ArrayList;
import java.util.stream.Collectors;

public interface QueueToKP {

    private static ResponseEntity<String> makeRequest(String url) throws HttpClientErrorException { // Make a request to KP by url (returns JSON)

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-api-key", "71B9447-9N54A3M-KWGEQTW-9A9G4CR"); //71B9447-9N54A3M-KWGEQTW-9A9G4CR

        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);

        ResponseEntity<String> JSONresult = new RestTemplate().exchange(url, HttpMethod.GET, httpEntity, String.class);

        return JSONresult;
    }

    public static ResponseEntity<String> getMoviesByIDs(ArrayList<Integer> IdArray) { //returns Movies (raw json) info by id Array
        String url = "https://api.kinopoisk.dev/v1.3/movie?selectFields=id name year rating.kp type persons genres";
        url = url+"&id="+ IdArray.stream()
                .map(Object::toString)
                .collect(Collectors.joining("&id="));
        url+="&limit=200";

        return makeRequest(url);
    }

    public static ResponseEntity<String> getUserMovies(int userId) { // returns user movies (raw json) by user id
        final String url = "https://www.kinopoisk.ru/user/"+userId+"/votes/list/vs/vote/perpage/200/#list";

        return makeRequest(url);
    }

    public static ResponseEntity<String> getAllMovies(int rating_from, int limit, int page) // returns all movies (json) with rating from "rating_from" to 10
    {
        final String url = "https://api.kinopoisk.dev/v1.3/movie?rating.kp="+rating_from+"-10&selectFields=id name year rating.kp type persons genres&limit="+limit+"&page="+page;

        return makeRequest(url);
    }

}
