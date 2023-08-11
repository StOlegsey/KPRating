package com.example.kprating.interfaces;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface QueueToKP {

    private static ResponseEntity<String> makeRequest(String url) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-api-key", "71B9447-9N54A3M-KWGEQTW-9A9G4CR");

        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);

        ResponseEntity<String> JSONresult = new RestTemplate().exchange(url, HttpMethod.GET, httpEntity, String.class);

        return JSONresult;
    }

    public static ResponseEntity<String> getUserMovies(int userId) {
        final String url = "https://www.kinopoisk.ru/user/"+userId+"/votes/list/vs/vote/perpage/200/#list";

        return makeRequest(url);
    }

    public static ResponseEntity<String> getAllMovies(int rating_from)
    {
        final String url = "https://api.kinopoisk.dev/v1.3/movie?rating.kp="+rating_from+"-10&selectFields=id name year rating.kp type persons genres";

        return makeRequest(url);
    }

}
