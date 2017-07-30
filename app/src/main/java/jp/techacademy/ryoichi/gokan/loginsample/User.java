package jp.techacademy.ryoichi.gokan.loginsample;

import com.google.gson.annotations.Expose;

/**
 * Created by houxianliangyi on 2017/07/30.
 */

public class User {
    // たぶんこのメソッドを動かすと、{ "user"; { "name": "xxxxx", ... } }というファイルになる
    public User(){};

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Expose
    private String email;

    @Expose
    private String password;

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

}
