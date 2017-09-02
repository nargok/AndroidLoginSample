package jp.techacademy.ryoichi.gokan.loginsample;

import com.google.gson.annotations.Expose;

/**
 * Created by houxianliangyi on 2017/07/30.
 */

public class User {
    public User(){};

    public User(String name, String email, String password, String access_token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.access_token = access_token;
    }

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    private String password;

    @Expose
    private String access_token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken(){
        return access_token;
    }

    public void setToken(String access_token) {
        this.access_token = access_token;
    }

}
