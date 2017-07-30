package jp.techacademy.ryoichi.gokan.loginsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ログインボタンにリスナを登録
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("LoginSample", "LoginButton onClick");

                EditText emailEditText = (EditText)findViewById(R.id.emailEditText);
                EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // パラメータの作成
                Log.d("LoginSample", email);
                Log.d("LoginSample", password);


                if ((email != null) && (password != null)) {
                    HashMap<String, String> postUser = new HashMap<>();
                    postUser.put("email", email);
                    postUser.put("password", password);

                    Login login = RetrofitUtils.build().create(Login.class);

                    Call<User> task = login.logIn(email, password);
                    task.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                Log.d("LoginSample", "Login succeed!");
                                // response.body()にUserクラスに変換されたオブジェクトが入っている
                                User user = response.body();
                            } else {
                                Log.d("LoginSample", "Login failed!");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        }
                    });
                }
            }
        });


        // アカウント作成ボタン
        Button accountButton = (Button)findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("LoginSample", "AccountButton onClick");

                EditText emailEditText = (EditText)findViewById(R.id.emailEditText);
                EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if ((email != null) && (password != null)) {
                    HashMap<String, User> postUser = new HashMap<>();
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(password);
                    postUser.put("user", user);

                    Signup signup = RetrofitUtils.build().create(Signup.class);
                    Call<User> task = signup.signUp(postUser);
                    task.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                Log.d("LoginSample", "SignUp succeed!");
                                // response.body()にUserクラスに変換されたオブジェクトが入っている
                                User user = response.body();
                            } else {
                                Log.d("LoginSample", "SignUp failed!");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });


                }
            }
        });

    }

    public static class RetrofitUtils {
        public  static Retrofit build() {
            return new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}