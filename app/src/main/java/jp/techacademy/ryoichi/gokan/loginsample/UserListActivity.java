package jp.techacademy.ryoichi.gokan.loginsample;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    SharedPreferences mPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // tokenを取得する
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String token = mPreference.getString("TOKEN", "");

        UserList mUserList = MainActivity.RetrofitUtils.build().create(UserList.class);

        Call<List<User>> task = mUserList.userList(token);

        task.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    Log.d("LoginSample", "Load UserList succeed!");
                    List<User> users = response.body();
                    Log.d("LoginSample", users.toString());

                    TextView mtextView = (TextView)findViewById(R.id.userListTextView);

                    // TextViewにユーザを追加
                    String text = "";
                    for (User user : users) {
                        text += user.getName() + " : " + user.getEmail() + '\n';
                    }
                    mtextView.setText(text);


                } else {
                    Log.d("LoginSample", "Load UserList failed!");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("LoginSample", "接続エラー");
            }
        });
    }
}
