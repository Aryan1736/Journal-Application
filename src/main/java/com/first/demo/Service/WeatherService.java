package com.first.demo.Service;

import com.first.demo.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "90eadfd3f44b8a0de3f3ffc0ed8972b7";

    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalAPI = API.replace("CITY",city).replace("API_KEY",apiKey);
        ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
