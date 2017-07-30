package jp.techacademy.ryoichi.gokan.loginsample;

import java.util.HashMap;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by houxianliangyi on 2017/07/30.
 */

public interface Signup {
    @POST("/v1/users")
    // TODO JsonOcjectにすれば、Jsonが帰ってくる？
    Call<User> signUp(
            @Body HashMap<String, User>user
    );
}
