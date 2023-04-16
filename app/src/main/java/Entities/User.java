package Entities;

import java.util.ArrayList;

public class User {
    private String userID;
    private String username;
    private String password;
    private ArrayList<String> favorites;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void addCourseToFav(String courseAcro) {
        favorites.add(courseAcro);
    }
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
}
