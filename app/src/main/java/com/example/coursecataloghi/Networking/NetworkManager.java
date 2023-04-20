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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NetworkManager {
    // get, post.... föll
    //má vera eitt per eða mörg
    // generic föll, taka inn beiðnina og hasmap af mögulegum breytum, un og pw
    //signup fall, login fall, getfavorites, setfavorites, 3 post og 1 get
    //bara senda beiðni á bakendann, engin logík eða neitt, gerir ekkert með gögnin

    private Data data = Data.getInstance();
    // framendinn þarf ekki að tékka á hvort user sé til, það ætti allt að vera á bakendanum
    //ekkert user logic hér
    //login fall sem sendir network req á bakendann
    //add to fav og get fav

    public boolean login(String username, String password) {
        //stormy appið frá Sigga - það er android app

        //Það vantar rétta slóð í staðinn fyrir "bakendi"
        String logInUrl = "http://10.0.2.2:4000/login/" + username;

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
        String signupUrl = "http://10.0.2.2:4000/signup/";
        System.out.println(signupUrl);
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        try {
            System.out.println("Komst í signup");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(signupUrl).post(formBody).build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    System.out.println("failaði að signa up");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //höndla responsið sem kemur frá bakenda
                    System.out.println("Komst í signup!");


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

    /*public void createUser(String uName, String pwd){
        data.createUser(uName, pwd);
    }*/

    public void addToFavorites(String userName, String courseAcro) {
        String logInUrl = "http://localhost:4000/favorites/" + userName + "&" + courseAcro;

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
        String logInUrl = "http://localhost:4000/favorites/" + userName;
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
