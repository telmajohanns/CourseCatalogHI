package com.example.coursecataloghi.Services;

import com.example.coursecataloghi.Networking.NetworkManager;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {

    //Tilviksbreyta af Networkmanager til þess að sjá um samskipti við bakenda
    private NetworkManager netMan = new NetworkManager();

    /**
     * Fall sem sér um að senda innskráningarbeiðni á networkmanager og skilar niðurstöðunni
     * úr því kalli til baka
     * @param userName
     * @param pwd
     * @return status kóði frá http kalli
     */
    public int login(String userName, String pwd){
        int reqCode = 0;

        while (reqCode == 0) {
            reqCode = NetworkManager.login(userName, pwd);
        }
        return reqCode;
    }

    /**
     * Fall til þess að senda beiðni um að stofna nýjann notenda á networkmanagerinn
     * @param usernamename notendanafn
     * @param pwd lykilorð
     */
    public void createUser(String usernamename, String pwd){
        netMan.signUp(usernamename, pwd);
    }

    /**
     * Fall til þess að bæta áföngum við favorites lista notenda. Skilar sannleiksgildi
     * eftir hvort það tókst að bæta áfanga við eða ekki
     * @param username notendanafn
     * @param courseAcro skammstöfun á áfanga, t.d. HBV601G
     * @return boolean
     */
    public boolean addToFavorites(String username, String courseAcro) {
        ArrayList<String> favorites = netMan.getFavorites(username);
        if (courseAcro.contains("Veldu áfanga")) {
            return false;
        }
        if (favorites.isEmpty()) {
            netMan.addToFavorites(username, courseAcro);
            return true;
        }
        for (String acronym: favorites) {
            if (acronym.equals(courseAcro)) {
                return false;
            }
        }
        netMan.addToFavorites(username, courseAcro);
        return true;
    }

    /**
     * Fall til þess að sækja lista af favorites áföngum í gegnum networkmanager. Skilar lista
     * af favorites áföngum notenda
     * @param username notendanafn
     * @return
     * @throws IOException
     */
    public ArrayList<String> getFavorites(String username) throws IOException {
        ArrayList<String> fav = netMan.getFavorites(username);
        return fav;
    }
}