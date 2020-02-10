package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class DataSource {
    private static final String BASE_SEARCH_URL = "https://www.metaweather.com/api/location/search";
    private static final String QUERY_PARAM = "query";
    private static final String FORECAST_URL = "https://www.metaweather.com/api/location/";

    private URL searchUrl;

    public Optional<String> getLocationString(String parameter) throws IOException {
        Optional<String> result = Optional.empty();
        searchUrl = new URL(BASE_SEARCH_URL + "?" + QUERY_PARAM + "=" + parameter);
        Optional<InputStream> inputStream = getInputStream();
        if (inputStream.isPresent()) {
            result = getStringFromInputStream(inputStream.get());
        }
        return result;
    }

    public Optional<String> getForecastString(String woeid) throws IOException {
        searchUrl = new URL(FORECAST_URL + woeid);
        Optional<InputStream> inputStream = getInputStream();
        return inputStream.flatMap(DataSource::getStringFromInputStream);
    }

    private Optional<InputStream> getInputStream() throws IOException {
        URLConnection connection = searchUrl.openConnection();
        return Optional.of(connection.getInputStream());
    }

    private static Optional<String> getStringFromInputStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader
                .lines()
                .reduce(String::concat);
    }
}