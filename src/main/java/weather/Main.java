package weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import weather.entity.Forecast;
import weather.entity.Location;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception {
        DataSource source = new DataSource();
        WeatherForecaster weatherForecaster = new WeatherForecaster(source, new ObjectMapper());

        System.out.println("getLocationByCity():");
        Optional<Location> london = weatherForecaster.getLocationByCity("London");
        london.ifPresent(System.out::println);

        Optional<Location> nonExistentCity = weatherForecaster.getLocationByCity("1");
        if (nonExistentCity.isPresent()) {
            System.out.println(nonExistentCity.get());
        } else {
            System.out.println("'1': Non existent city");
        }

        try {
            weatherForecaster.getLocationByCity("");
        } catch (IOException e) {
            System.out.println("Empty string: Error 403");
        }

        System.out.println("\ngetForecastByWoeid():");
        Forecast londonForecast = weatherForecaster.getForecastByWoeid("44418");
        System.out.println(londonForecast);

        try {
            weatherForecaster.getForecastByWoeid("4441");
        } catch (FileNotFoundException e) {
            System.out.println("Non existent woeid: Error 404");
        }

        try {
            weatherForecaster.getForecastByWoeid("");
        } catch (FileNotFoundException e) {
            System.out.println("Empty string: Error 404");
        }

        double temperature = weatherForecaster.getTemperatureByCity("London");
        System.out.println("Temperature in London is " + temperature);
    }
}