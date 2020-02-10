package weather.exception;

public class ForecastNotFoundException extends Exception {
    public ForecastNotFoundException(String message) {
        super(message);
    }
}