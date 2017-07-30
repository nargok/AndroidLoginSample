package jp.techacademy.ryoichi.gokan.loginsample;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by houxianliangyi on 2017/07/30.
 */

public interface Login {

    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
    @POST("/v1/login")
    Call<User> logIn(@Field("email") String email,
                     @Field("password") String password
                     );
}
