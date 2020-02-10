package weather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // we don't need ALL fields from JSON object
public class Location {
    private String title;
    private String woeid;

    public Location() {
    }

    public Location(String title, String woeid) {
        this.title = title;
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    public String getWoeid() {
        return woeid;
    }

    @Override
    public String toString() {
        return "Location{title=" + title + ", woeid=" + woeid + '}';
    }
}

/* GET https://www.metaweather.com/api/location/search/?query=london
[] Array, {} Object

Object - Location

[
    {
        "title": "London",
        "location_type": "City",
        "woeid": 44418,
        "latt_long": "51.506321,-0.12714"
    }
]
 */