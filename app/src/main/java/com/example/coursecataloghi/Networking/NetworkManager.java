package com.example.coursecataloghi.Networking;

import android.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    private static int respCode;
    private ArrayList<String> favorites = new ArrayList<>();
    public static int login(String username, String password) {
        respCode = 0;

        String logInUrl = "http://10.0.2.2:4000/login/" + username + "&" + password;
        RequestBody formBody = new FormBody.Builder()
                .add("username", username).add("password", password)
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(logInUrl).post(formBody).build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    respCode = response.code();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return respCode;
    }


    public boolean signUp(String username, String password) {

        String signupUrl = "http://10.0.2.2:4000/signup/" + username + "&" + password;
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(signupUrl).post(formBody).build();

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



    public void addToFavorites(String username, String acronym) {
        String addFavUrl = "http://10.0.2.2:4000/favorites/" + username + "&" + acronym;
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("acronym", acronym)
                .build();


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(addFavUrl).post(formBody).build();

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

    public ArrayList<String> getFavorites(String username) {
        final CountDownLatch latch = new CountDownLatch(1);
        String getFavUrl = "http://10.0.2.2:4000/favorites/" + username;
        /*RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();*/

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(getFavUrl).get().build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //höndla failed connection
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==401) {
                        return;
                    }

                    //höndla responsið sem kemur frá bakenda
                    String svar = response.body().string();
                    //System.out.println("SVAR: " + svar + "code: " + response.code());
                    //System.out.println(response);

                    //JSONObject jsonObject = new JSONObject(svar);
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.fromJson(svar, JsonElement.class);
                    if (jsonElement.isJsonArray()) {
                        System.out.println(jsonElement.isJsonArray());
                        JsonArray jsonArray = jsonElement.getAsJsonArray();
                        for(JsonElement element: jsonArray) {
                            JsonObject jsonObject = element.getAsJsonObject();
                            System.out.println("Acro: " + jsonObject.get("acronym").getAsString());
                            favorites.add(jsonObject.get("acronym").getAsString());
                        }
                    }
                    if(jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        System.out.println("Acro: " + jsonObject.get("acronym").getAsString());
                        favorites.add(jsonObject.get("acronym").getAsString());
                    }
                    latch.countDown();
                }
            });
            latch.await(60, TimeUnit.SECONDS); // Wait for the response with a timeout (e.g., 30 seconds)
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return favorites;
    }
}
