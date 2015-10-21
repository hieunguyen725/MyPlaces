package model;

import java.util.List;

/**
 * Created by Hieu on 10/21/2015.
 */
public class User {
    private String username;
    private String password;
    private List<Place> placesList;

    public User(String username, String password, List<Place> placesList) {
        this.username = username;
        this.password = password;
        this.placesList = placesList;
    }

    public User(String username, String password) {
        this(username, password, null);
    }

    public List<Place> getPlacesList() {
        return placesList;
    }

    public void setPlacesList(List<Place> placesList) {
        this.placesList = placesList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
