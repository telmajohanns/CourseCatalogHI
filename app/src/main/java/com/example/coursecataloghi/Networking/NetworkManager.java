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

    public ArrayList<User> getUsers(){
        ArrayList<User>  users = data.getUsers();
        return users;
    }

    public void createUser(String uName, String pwd){
        data.createUser(uName, pwd);
    }
}
