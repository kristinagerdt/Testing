package weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import weather.entity.Forecast;
import weather.entity.Location;

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

        Optional<Location> emptyCity = weatherForecaster.getLocationByCity("");
        if (emptyCity.isPresent()) {
            System.out.println(emptyCity.get());
        } else {
            System.out.println("Empty city: Error 403");
        }

        System.out.println("\ngetForecastByWoeid():");
        Optional<Forecast> londonForecast = weatherForecaster.getForecastByWoeid("44418");
        londonForecast.ifPresent(System.out::println);

        Optional<Forecast> nonExistentWoeid = weatherForecaster.getForecastByWoeid("4441");
        if (nonExistentWoeid.isPresent()) {
            System.out.println(nonExistentWoeid.get());
        } else {
            System.out.println("Non existent woeid: Error 404");
        }

        Optional<Forecast> emptyWoeid = weatherForecaster.getForecastByWoeid("");
        if (emptyWoeid.isPresent()) {
            System.out.println(emptyWoeid.get());
        } else {
            System.out.println("Empty woeid: Error 404");
        }

        double temperature = weatherForecaster.getTemperatureByCity("London");
        System.out.println("\nTemperature in London is " + Math.round(temperature * 100.00) / 100.00);
    }
}