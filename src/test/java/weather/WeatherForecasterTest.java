package weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import weather.entity.Forecast;
import weather.entity.Location;
import weather.exception.ForecastNotFoundException;
import weather.exception.InvalidLocationException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class WeatherForecasterTest {
    private WeatherForecaster weatherForecaster;
    private DataSource dataSource = Mockito.mock(DataSource.class);

    @Before
    public void setUp() {
        weatherForecaster = new WeatherForecaster(dataSource);
    }

    @Test
    public void testLondonGetLocationByCity() throws Exception {
        when(dataSource.getLocationString("London"))
                .thenReturn(Optional.of(LOCATION));
        Location location = weatherForecaster.getLocationByCity("London");
        Assert.assertEquals("woeid", "44418", location.getWoeid());
    }

    @Test(expected = InvalidLocationException.class)
    public void testEmptyStringGetLocationByCity() throws Exception {
        weatherForecaster.getLocationByCity("");
    }

    @Test(expected = InvalidLocationException.class)
    public void testNotExistCityGetLocationByCity() throws Exception {
        Location location = weatherForecaster.getLocationByCity("1");
        Assert.assertNull(location);
    }

    @Test
    public void testGetForecastByWoeid() throws Exception {
        when(dataSource.getForecastString("44418"))
                .thenReturn(Optional.of(FORECAST));
        Forecast forecast = weatherForecaster.getForecastByWoeid("44418");
        Assert.assertTrue("results are not empty", forecast.getConsolidatedWeather().length > 0);
    }


    @Test(expected = ForecastNotFoundException.class)
    public void testEmptyStringGetWeatherForecastByWoeid() throws Exception {
        weatherForecaster.getForecastByWoeid("");
    }

    @Test(expected = ForecastNotFoundException.class)
    public void testNotExistWoeidGetWeatherForecastByWoeid() throws Exception {
        Forecast forecast = weatherForecaster.getForecastByWoeid("4441");
        Assert.assertNull(forecast);
    }

    @Test
    public void testGetWeatherForecastByCity() throws Exception {
        when(dataSource.getLocationString("London")).thenReturn(Optional.of(LOCATION));
        when(dataSource.getForecastString("44418")).thenReturn(Optional.of(FORECAST));
        double actual = weatherForecaster.getTemperatureByCity("London");
        Assert.assertEquals(13.45d, actual, 0.001);
    }

    private static final String LOCATION = "[{\"title\": \"London\",\"location_type\": \"City\",\"woeid\": " +
            "44418,\"latt_long\": \"51.506321,-0.12714\"}]";
    private static final String FORECAST = "{\"consolidated_weather\": [{\"id\": 4983714639511552,\"the_temp\": " +
            "13.45},{\"id\": 5950526709563392,\"the_temp\": 9.1975}]}";
}