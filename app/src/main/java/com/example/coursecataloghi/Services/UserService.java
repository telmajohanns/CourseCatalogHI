package com.example.coursecataloghi.Services;

import com.example.coursecataloghi.Networking.NetworkManager;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {
    // Favorites
    // Signup o.s.frv. á að fara hingað (úr mainactivity...)
    // beiðni á
    private NetworkManager netMan = new NetworkManager();

    public int login(String userName, String pwd){
        int reqCode = 0;

        while (reqCode == 0) {
            reqCode = NetworkManager.login(userName, pwd);
        }
        return reqCode;
    }

    public void createUser(String uName, String pwd){
        netMan.signUp(uName, pwd);
    }

    public boolean addToFavorites(String username, String courseAcro) {
        ArrayList<String> favorites = netMan.getFavorites(username);
        if (courseAcro.contains("Veldu áfanga")) {
            return false;
        }
        if (favorites.isEmpty()) {
            System.out.println("Næ að bæta í tóman");
            netMan.addToFavorites(username, courseAcro);
            return true;
        }
        for (String acronym: favorites) {
            System.out.println(acronym);
            if (acronym.equals(courseAcro)) {
                return false;
            }
        }
        netMan.addToFavorites(username, courseAcro);
        return true;
    }

    public ArrayList<String> getFavorites(String username) throws IOException {
        ArrayList<String> fav = netMan.getFavorites(username);
        return fav;
    }
}