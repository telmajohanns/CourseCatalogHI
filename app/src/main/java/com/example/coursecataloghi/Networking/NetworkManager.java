package com.example.coursecataloghi.Networking;

import java.util.ArrayList;

import Entities.Data;
import Entities.User;

public class NetworkManager {
    // get, post.... föll
    //má vera eitt per eða mörg
    // generic föll, taka inn beiðnina og hasmap af mögulegum breytum, un og pw
    //signup fall, login fall, getfavorites, setfavorites, 3 post og 1 get
    //bara senda beiðni á bakendann, engin logík eða neitt, gerir ekkert með gögnin

    Data data = Data.getInstance();
    // framendinn þarf ekki að tékka á hvort user sé til, það ætti allt a ðvera á bakendanum
    //ekkert user logic hér
    //login fall sem sendir network req á bakendann
    //add to fav og get fav

    public User login(String username, String password) {
        //þarf að hafa OKHTTP library
        //stormy appið frá Sigga - það er android app
        return null;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User>  users = data.getUsers();
        return users;
    }

    public void createUser(String uName, String pwd){
        data.createUser(uName, pwd);
    }
}
