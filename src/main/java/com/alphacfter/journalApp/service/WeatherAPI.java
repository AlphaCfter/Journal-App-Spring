package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Service for fetching weather data using the WeatherStack API.
 * <p>
 * This component retrieves weather information for a given city by sending an HTTP request
 * to the WeatherStack API. The API key is injected from the application configuration.
 * </p>
 *
 */
@Component
public class WeatherAPI {

    /**
     * API key for authenticating requests to the WeatherStack API.
     * Injected from the application properties file.
     */
    @Value("${weather.api.key}")
    private String API;

    /**
     * URL template for constructing the WeatherStack API request.
     * The placeholders are replaced dynamically in the request.
     */
    private static final String URL_TEMPLATE = "http://api.weatherstack.com/current?access_key=%s&query=%s";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Fetches the current weather for a specified city.
     * <p>
     * Constructs the request URL using the API key and city name, then sends an HTTP GET request
     * to retrieve weather details.
     * </p>
     *
     * @param city the name of the city for which to fetch weather data
     * @return a {@link WeatherResponse} object containing weather details for the specified city
     */
    public WeatherResponse getWeather(String city) {
        String url = String.format(URL_TEMPLATE, API, city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
