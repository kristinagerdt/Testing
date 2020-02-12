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

    public String getLocationJsonAsString(String parameter) throws IOException {
        String result = "[]";
        searchUrl = new URL(BASE_SEARCH_URL + "?" + QUERY_PARAM + "=" + parameter);
        Optional<InputStream> inputStream = getInputStream();
        if (inputStream.isPresent()) {
            Optional<String> stringFromInputStream = getStringFromInputStream(inputStream.get());
            if (stringFromInputStream.isPresent()) {
                result = stringFromInputStream.get();
            }
        }
        return result;
    }

    public String getForecastJsonAsString(String woeid) throws IOException {
        String result = "";
        searchUrl = new URL(FORECAST_URL + woeid);
        Optional<InputStream> inputStream = getInputStream();
        Optional<String> stringFromInputStream = inputStream.flatMap(DataSource::getStringFromInputStream);
        if (stringFromInputStream.isPresent()) {
            result = stringFromInputStream.get();
        }
        return result;
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