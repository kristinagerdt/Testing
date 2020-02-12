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
import static org.junit.Assert.assertTrue;
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
        when(dataSource.getLocationString("London")).thenReturn(Optional.of(LOCATION));
        Optional<Location> location = weatherForecaster.getLocationByCity("London");
        location.ifPresent(l -> assertEquals("44418", l.getWoeid()));
    }

    @Test
    public void testNonExistentCityGetLocationByCity() throws IOException {
        when(dataSource.getLocationString("1")).thenReturn(Optional.of("[]"));
        Optional<Location> location = weatherForecaster.getLocationByCity("1");
        assertEquals(Optional.empty(), location);
    }

    @Test
    public void testEmptyStringGetLocationByCity() throws IOException {
        when(dataSource.getLocationString("")).thenReturn(Optional.empty());
        Optional<Location> location = weatherForecaster.getLocationByCity("");
        assertEquals(Optional.empty(), location);
    }

//    @Test(expected = IOException.class) // 403 Error
//    public void testEmptyStringGetLocationByCity() throws IOException {
//        when(dataSource.getLocationString("")).thenThrow(new IOException());
//        weatherForecaster.getLocationByCity("");
//    }

    @Test
    public void test44418GetForecastByWoeid() throws IOException {
        when(dataSource.getForecastString("44418")).thenReturn(Optional.of(FORECAST));
        Optional<Forecast> forecast = weatherForecaster.getForecastByWoeid("44418");
        forecast.ifPresent(f -> assertTrue(forecast.get().getConsolidatedWeather().length > 0));
    }

    @Test
    public void testNonExistentWoeidGetForecastByWoeid() throws IOException {
        when(dataSource.getForecastString("4441")).thenReturn(Optional.empty());
        Optional<Forecast> forecast = weatherForecaster.getForecastByWoeid("4441");
        assertEquals(Optional.empty(), forecast);
    }

    @Test
    public void testEmptyStringGetForecastByWoeid() throws IOException {
        when(dataSource.getForecastString("")).thenReturn(Optional.empty());
        Optional<Forecast> forecast = weatherForecaster.getForecastByWoeid("");
        assertEquals(Optional.empty(), forecast);
    }

    @Test
    public void testLondonTemperatureByCity() throws Exception {
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