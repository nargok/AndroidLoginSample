package jp.techacademy.ryoichi.gokan.loginsample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mPreference;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        // ログインボタンにリスナを登録
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("LoginSample", "LoginButton onClick");

                EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
                EditText emailEditText = (EditText)findViewById(R.id.emailEditText);
                EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // パラメータの作成
                Log.d("LoginSample", email);
                Log.d("LoginSample", password);


                if ((email.length() != 0) && (password.length() != 0)) {
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
                                String name = user.getName();
                                msg = "こんにちは、" + name + "さん";

                                // トークンを登録する
                                SharedPreferences.Editor editor = mPreference.edit();
                                editor.putString("TOKEN", user.getToken().toString());
                                editor.commit();


                            } else {
                                Log.d("LoginSample", "Login failed!");
                                msg = "ログインに失敗しました";
                            }
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("LoginSample", "接続エラー");
                            msg = "接続エラー";
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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

                EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
                EditText emailEditText = (EditText)findViewById(R.id.emailEditText);
                EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if ((name.length() != 0) && (email.length() != 0) && (password.length() != 0)) {
                    HashMap<String, User> postUser = new HashMap<>();
                    User user = new User();
                    user.setName(name);
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
                                msg = "アカウント作成成功";
                                User user = response.body();
                            } else {
                                Log.d("LoginSample", "SignUp failed!");
                                msg = "アカウント作成失敗";
                            }
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            msg = "接続エラー";
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

        // ユーザ一覧ボタン
        Button userListButton = (Button)findViewById(R.id.listButton);
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginSample", "UserList Button was tapped");

                // トークン情報を取得する
                String token = mPreference.getString("TOKEN", "");
                Log.d("LoginSample", token);

                if (token.length() != 0) {
                    // tokenがあれば画面遷移する
                    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                    startActivity(intent);
                } else {
                    // ログインしていないなら画面遷移できない
                    Log.d("LoginSample", "You need to login");
                    Toast.makeText(MainActivity.this, "ログインしてください", Toast.LENGTH_SHORT).show();

                }

            }
        });

        // ログアウトボタン
        Button logoutButton = (Button)findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("LoginSample", "Logout Button was tapped");

                // Token情報を削除する
                SharedPreferences.Editor editor = mPreference.edit();
                editor.remove("TOKEN");
                editor.commit();

                Toast.makeText(MainActivity.this, "ログアウトしました", Toast.LENGTH_SHORT).show();

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
