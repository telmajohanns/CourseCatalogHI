package com.example.coursecataloghi.Services;

import android.content.SharedPreferences;

import com.example.coursecataloghi.Networking.NetworkManager;
import com.example.coursecataloghi.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Entities.Data;
import Entities.User;

public class UserService {
    // Favorites
    // Signup o.s.frv. á að fara hingað (úr mainactivity...)
    // beiðni á
    private NetworkManager netMan = new NetworkManager();

    public boolean logIn(String userName, String pwd){
        for (User u: netMan.getUsers()) {
            if (userName.equals(u.getUsername())) {
                if (pwd.equals(u.getPassword())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean doesUeserExsist(String uName){
        for (User u: netMan.getUsers())
            if(uName.equals(u.getUsername())){
                return true;
            }
        return false;
    }

    public void createUser(String uName, String pwd){
        netMan.createUser(uName, pwd);
    }

    public void addToFavorites(String user, String courseAcro){
        netMan.addToFavorites(user, courseAcro);
    }

    public ArrayList<String> getFavorites(String username){
        ArrayList<String> fav = netMan.getFavorites(username);
        return fav;
    }
}