package weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import weather.entity.Forecast;
import weather.entity.Location;
import weather.exception.ForecastNotFoundException;
import weather.exception.InvalidLocationException;

import java.io.IOException;
import java.util.Optional;

public class WeatherForecaster {
    private DataSource dataSource;
    private ObjectMapper mapper = new ObjectMapper();

    public WeatherForecaster(DataSource dataSource) {
        this.dataSource = dataSource;
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // the_temp -> theTemp
    }

    public double getTemperatureByCity(String city) throws Exception {
        Location location = getLocationByCity(city);
        Forecast forecast = getForecastByWoeid(location.getWoeid());
        return forecast.getConsolidatedWeather()[0].getTheTemp();
    }

    public Location getLocationByCity(String city) throws IOException, InvalidLocationException {
        Optional<String> locationString = dataSource.getLocationString(city);
        if (locationString.isPresent() && !locationString.get().equals("[]")) {
            Location[] locations = mapper.readValue(locationString.get(), Location[].class);
            return locations[0];
        } else {
            throw new InvalidLocationException("No location for city \'" + city + "\'");
        }
    }

    public Forecast getForecastByWoeid(String woeid) throws IOException, ForecastNotFoundException {
        Optional<String> forecastString = dataSource.getForecastString(woeid);
        if (forecastString.isPresent()) {
            return mapper.readValue(forecastString.get(), Forecast.class);
        } else {
            throw new ForecastNotFoundException("No forecast for woeid \'" + woeid + "\'");
        }
    }
}