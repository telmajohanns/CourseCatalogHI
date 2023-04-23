package Entities;

public class User {
    //Tilviksbreytur til að halda utan um notendanafn og lykilorð notenda
    private String username;
    private String password;

    /**
     * Smiður sem býr til user
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Getterar og Setterar
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
