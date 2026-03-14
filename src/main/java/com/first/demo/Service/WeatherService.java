package com.first.demo.Service;

import com.first.demo.Cache.AppCache;
import com.first.demo.Entity.User;
import com.first.demo.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        String finalAPI = appCache.App_Cache.get("weather_api").replace("<CITY>",city).replace("<API_KEY>",apiKey);
        ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

    // consume external post APIs
    public WeatherResponse postWeather(String city){
        String finalAPI = appCache.App_Cache.replace("CITY",city).replace("API_KEY",apiKey);

        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key","value");

        User user = User.builder().username("A").password("A").build();
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }


}