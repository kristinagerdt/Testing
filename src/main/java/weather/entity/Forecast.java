package weather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true) // we don't need ALL fields from JSON object
public class Forecast {
    private ConsolidatedWeather[] consolidatedWeather;

    public Forecast() {
    }

    public ConsolidatedWeather[] getConsolidatedWeather() {
        return consolidatedWeather;
    }

    @Override
    public String toString() {
        return "Forecast{ConsolidatedWeather=" + Arrays.toString(consolidatedWeather) + '}';
    }
}

/* GET https://www.metaweather.com/api/location/44418
[] Array, {} Object

external Object - Forecast
internal object - ConsolidatedWeather

{
    "consolidated_weather": [
        {
            "id": 4733895520026624,
            "weather_state_name": "Thunder",
            "weather_state_abbr": "t",
            "wind_direction_compass": "WSW",
            "created": "2020-02-10T15:16:03.206852Z",
            "applicable_date": "2020-02-10",
            "min_temp": 4.58,
            "max_temp": 8.95,
            "the_temp": 8.71,
            "wind_speed": 13.906091009798777,
            "wind_direction": 246.83709277801032,
            "air_pressure": 1000,
            "humidity": 57,
            "visibility": 8.728401137357832,
            "predictability": 80
        },...
    ]
}
 */