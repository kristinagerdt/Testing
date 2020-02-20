package trackerClient.model;

public class User {
    private String firstName;
    private String lastName;
    private String password;
    private String repeatPassword;
    private String username;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", password=" + password +
                ", repeatPassword=" + repeatPassword +
                ", userName=" + username + '}';
    }
}

/*
{
  "firstName": "string",
  "lastName": "string",
  "password": "string",
  "repeatPassword": "string",
  "username": "string"
}
 */