package app.nvgtor.com.leanrning.login.model;

/**
 * Created by nvgtor on 2016/4/8.
 */
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkUserValidity(String username, String password){
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){
            return false;
        }
        return true;
    }
}
