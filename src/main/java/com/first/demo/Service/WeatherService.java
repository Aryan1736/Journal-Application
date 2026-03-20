package com.first.demo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) throws JsonProcessingException {
        WeatherResponse weatherResponse = redisService.get("Weather of " + city, WeatherResponse.class);
        if(weatherResponse!=null)
            return weatherResponse;
        else{
            String finalAPI = appCache.App_Cache.get("weather_api").replace("<CITY>",city).replace("<API_KEY>",apiKey);
            ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body!=null)
                redisService.set("Weather of " + city,body,300L);
            return body;
        }
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