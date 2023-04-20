package Entities;

import java.util.ArrayList;

public class Data {
    private static Data INSTANCE;
    private ArrayList<User> users = new ArrayList<>();
    private Data() {
        users.add(new User("admin", "admin"));
        users.add(new User("sme", "sme"));
    }
    public static Data getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Data();
        }
        return INSTANCE;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void createUser(String username, String password){
        users.add(new User(username,password));
    }
}
