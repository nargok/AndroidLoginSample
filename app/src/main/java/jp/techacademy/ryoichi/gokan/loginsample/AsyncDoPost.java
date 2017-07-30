package jp.techacademy.ryoichi.gokan.loginsample;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * GETサンプル。型指定はそれぞれ以下に対応する
 * 第１：doInBackground
 * 第２：onProgressUpdate
 * 第３：doInBackgroundの戻り値、onPostExecuteの引数
 */

public class AsyncDoPost extends AsyncTask<String, Integer, JSONObject> {

    // コールバック用
    private CallBackTask callBacktask;

    // メッセージ格納用
    private String msg;

    private Context context;

    // urlのendpoint
    private String endpoint;

    // コンストラクタ
    public AsyncDoPost(Context context) {
        super();
        this.context = context;
        msg = "";
        endpoint = "";
    }

    // doInBackgroundの事前処理（UI操作可能）
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 主処理(UI操作不可）
    protected JSONObject doInBackground(String... params) {
//        HttpPost post = new HttpPost(urls[0]);
        JSONObject holder = new JSONObject();
        JSONObject jsonObject = null;
        JSONObject userObj = new JSONObject();

        HttpURLConnection connection = null;
        try {
            // TODO コンストラクタのパラメータを増やしてendpointを設定する
            URL url = new URL("http://localhost:3000/" + URLEncoder.encode(endpoint, "UTF-8"));

            // TODO the params 後で設定する　ログインとアカウント作成の時で分ける方法を考える
            userObj.put("email", "aaaaa");
            userObj.put("password", "aaaaa");
            holder.put("user", userObj); // これを使えば {"user" {"email": xxxxx, "password": yyyy} }が実現できる
//            StringEntity se = new StringEntity(holder.toString());

            // setup the request headers
//            post.setHeader("Accept", "application/json");
//            post.setHeader("Content-Type", "application/json");

            connection = (HttpURLConnection) url.openConnection();
            // POSTの場合はwriteでパラメタを渡す
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//            out.write()
            out.close();

            final int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                // 取得した文字列からJsonObjectを作成
                jsonObject = new JSONObject(sb.toString());
                msg = "正常に処理がされました";
            } else {
                msg = "HTTP接続エラー";
            }

        } catch (MalformedURLException e) {
            msg = "MalformedURLException:" + e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            msg = "Exectipn:" + e.getMessage();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return jsonObject;
    }

    // 進捗状況をUIに反映するための処理(UI操作可能)
    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    // doInBackgroundの事後処理
    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        callBacktask.CallBack(result);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // 呼び出し元で、CallBackTaskを割り当てる
    public void setOnCallBack(CallBackTask _cbj) {
        callBacktask = _cbj;
    }

    // コールバック用のstaticなクラス
    public static class CallBackTask {
        // 呼び出し元でoverrideする
        public void CallBack(JSONObject result){

        }
    }

}
