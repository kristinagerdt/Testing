package weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import weather.entity.Forecast;
import weather.entity.Location;

import java.io.IOException;
import java.util.Optional;

public class WeatherForecaster {
    private DataSource dataSource;
    private ObjectMapper mapper;

    public WeatherForecaster(DataSource dataSource, ObjectMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // the_temp -> theTemp
    }

    public double getTemperatureByCity(String city) throws IOException {
        Optional<Location> location = getLocationByCity(city);
        if (location.isPresent()) {
            Optional<Forecast> forecast = getForecastByWoeid(location.get().getWoeid());
            if (forecast.isPresent()) {
                return forecast.get().getConsolidatedWeather()[0].getTheTemp();
            }
        }
        return 0.0;
    }

    public Optional<Location> getLocationByCity(String city) throws IOException {
        Optional<String> locationString = dataSource.getLocationString(city);
        if (locationString.isPresent()) {
            Location[] locations = mapper.readValue(locationString.get(), Location[].class);
            if (locations.length != 0) return Optional.of(locations[0]);
        }
        return Optional.empty();
    }

    public Optional<Forecast> getForecastByWoeid(String woeid) throws IOException {
        Optional<String> forecastString = dataSource.getForecastString(woeid);
        if (forecastString.isPresent()) {
            Forecast forecast = mapper.readValue(forecastString.get(), Forecast.class);
            return Optional.of(forecast);
        }
        return Optional.empty();
    }
}