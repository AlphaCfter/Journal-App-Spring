package com.alphacfter.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the response received from the Weather API.
 * <p>
 * This class maps the JSON response into a Java object using Jackson annotations.
 * It contains details about the current weather conditions.
 * </p>
 *
 */
@Getter
@Setter
public class WeatherResponse {

    private Current current;

    @Getter
    @Setter
    public class Current{

        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;

        @JsonProperty("is_day")
        private String isDay;

    }
}





