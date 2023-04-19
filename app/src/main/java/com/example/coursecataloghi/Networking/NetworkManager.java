package com.example.coursecataloghi.Networking;

import android.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import Entities.Data;
import Entities.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkManager {
    // get, post.... föll
    //má vera eitt per eða mörg
    // generic föll, taka inn beiðnina og hasmap af mögulegum breytum, un og pw
    //signup fall, login fall, getfavorites, setfavorites, 3 post og 1 get
    //bara senda beiðni á bakendann, engin logík eða neitt, gerir ekkert með gögnin

    Data data = Data.getInstance();
    // framendinn þarf ekki að tékka á hvort user sé til, það ætti allt að vera á bakendanum
    //ekkert user logic hér
    //login fall sem sendir network req á bakendann
    //add to fav og get fav

    public boolean login(String username, String password) {
        //stormy appið frá Sigga - það er android app

        //Það vantar rétta slóð í staðinn fyrir "bakendi"
        String logInUrl = "https://course-catalog-ksot.onrender.com/login/" + username + "/" + password;

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(logInUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //höndla responsið sem kemur frá bakenda

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    public boolean signUp(String username, String password) {
        //stormy appið frá Sigga - það er android app

        //Það vantar rétta slóð í staðinn fyrir "bakendi"
        String logInUrl = "http://localhost:4000/" + username + "/" + password;

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(logInUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //höndla responsið sem kemur frá bakenda


                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User>  users = data.getUsers();
        return users;
    }

    public void createUser(String uName, String pwd){
        data.createUser(uName, pwd);
    }

    public void addToFavorites(String userName, String courseAcro) {
        String logInUrl = "https://course-catalog-ksot.onrender.com/addtofav/" + userName + "/" + courseAcro;

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(logInUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //höndla responsið sem kemur frá bakenda
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getFavorites(String userName) {
        String logInUrl = "https://course-catalog-ksot.onrender.com/getfav/" + userName;
        ArrayList<String> fav = new ArrayList<String>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(logInUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //höndla responsið sem kemur frá bakenda
                    String svar = response.body().toString();
                    try {
                        JSONObject jsonObject = new JSONObject(svar);
                        Gson gson = new Gson();
                        String[] favlisti = gson.fromJson(String.valueOf(gson), String[].class);
                        for (String afangi: favlisti) {
                            fav.add(afangi);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fav;
    }
}
