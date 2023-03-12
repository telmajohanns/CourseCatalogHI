package Entities;

import java.util.ArrayList;

public class User {
    private String userID;
    private String username;
    private String password;
    private ArrayList<Course> favorites;

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

    public ArrayList<Course> getFavorites() {
        return favorites;
    }

    public void addCourseToFav(Course course) {
        favorites.add(course);
    }
    public void setFavorites(ArrayList<Course> favorites) {
        this.favorites = favorites;
    }
}
