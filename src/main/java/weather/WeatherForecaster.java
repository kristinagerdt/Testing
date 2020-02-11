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
        Optional<Location> optionalLocation = getLocationByCity(city);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            Forecast forecast = getForecastByWoeid(location.getWoeid());
            return forecast.getConsolidatedWeather()[0].getTheTemp();
        }
        return 0.0;
    }

    public Optional<Location> getLocationByCity(String city) throws IOException {
        String locationString = dataSource.getLocationString(city);
        Location[] locations = mapper.readValue(locationString, Location[].class);
        if (locations.length != 0) return Optional.of(locations[0]);
        return Optional.empty();
    }

    public Forecast getForecastByWoeid(String woeid) throws IOException {
        String forecastString = dataSource.getForecastString(woeid);
        Forecast forecast = mapper.readValue(forecastString, Forecast.class);
        return forecast;
    }
}