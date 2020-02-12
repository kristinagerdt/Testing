package weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import weather.entity.Forecast;
import weather.entity.Location;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class WeatherForecasterTest {
    private ObjectMapper mapper;
    private DataSource dataSource;
    private WeatherForecaster weatherForecaster;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        dataSource = Mockito.mock(DataSource.class);
        weatherForecaster = new WeatherForecaster(dataSource, mapper);
    }

    @Test
    public void testLondonGetLocationByCity() throws IOException {
        when(dataSource.getLocationJsonAsString("London")).thenReturn(LOCATION);
        Optional<Location> location = weatherForecaster.getLocationByCity("London");
        location.ifPresent(l -> assertEquals("44418", l.getWoeid()));
    }

    @Test
    public void testNonExistentCityGetLocationByCity() throws IOException {
        when(dataSource.getLocationJsonAsString("1")).thenReturn("[]");
        Optional<Location> location = weatherForecaster.getLocationByCity("1");
        assertEquals(Optional.empty(), location);
    }

    @Test(expected = IOException.class) // 403 Error
    public void testEmptyStringGetLocationByCity() throws IOException {
        when(dataSource.getLocationJsonAsString("")).thenThrow(new IOException());
        weatherForecaster.getLocationByCity("");
    }

    @Test
    public void test44418GetForecastByWoeid() throws IOException {
        when(dataSource.getForecastJsonAsString("44418")).thenReturn(FORECAST);
        Forecast forecast = weatherForecaster.getForecastByWoeid("44418");
        Assert.assertTrue(forecast.getConsolidatedWeather().length > 0);
    }

    @Test(expected = IOException.class) // 404 Error
    public void testNonExistentWoeidGetForecastByWoeid() throws IOException {
        when(dataSource.getForecastJsonAsString("4441")).thenThrow(new IOException());
        weatherForecaster.getForecastByWoeid("4441");
    }

    @Test(expected = IOException.class) // 404 Error
    public void testEmptyStringGetForecastByWoeid() throws IOException {
        when(dataSource.getForecastJsonAsString("")).thenThrow(new IOException());
        weatherForecaster.getForecastByWoeid("");
    }

    @Test
    public void testLondonTemperatureByCity() throws Exception {
        when(dataSource.getLocationJsonAsString("London")).thenReturn(LOCATION);
        when(dataSource.getForecastJsonAsString("44418")).thenReturn(FORECAST);
        double actual = weatherForecaster.getTemperatureByCity("London");
        Assert.assertEquals(13.45d, actual, 0.001);
    }

    private static final String LOCATION = "[{\"title\": \"London\",\"location_type\": \"City\",\"woeid\": " +
            "44418,\"latt_long\": \"51.506321,-0.12714\"}]";
    private static final String FORECAST = "{\"consolidated_weather\": [{\"id\": 4983714639511552,\"the_temp\": " +
            "13.45},{\"id\": 5950526709563392,\"the_temp\": 9.1975}]}";
}