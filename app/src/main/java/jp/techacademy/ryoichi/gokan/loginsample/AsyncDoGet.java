package jp.techacademy.ryoichi.gokan.loginsample;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * Created by houxianliangyi on 2017/07/28.
 */
/**
 * GETサンプル。型指定はそれぞれ以下に対応する
 * 第１：doInBackground
 * 第２：onProgressUpdate
 * 第３：doInBackgroundの戻り値、onPostExecuteの引数
 */
public class AsyncDoGet extends AsyncTask<String, Integer, Integer> {
    // コールバック用
    private CallBackTask callBackTask;

    // メッセージ格納用
    private String msg;

    private Context context;

    // urlのendpoint
    private String endpoint;

    // parameter


    //　コンストラクタ
    public AsyncDoGet(Context context) {
        super();
        this.context = context;
        msg = "";
        endpoint = "";
    }


    // doInBackgroundの事前処理(UI操作可能)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 主処理(UI操作不可能)
    @Override // 戻り値にはJsonObjectにして呼び出し元でデータを取り扱う
    protected Integer doInBackground(String... params) {
        int ans = 0;

        HttpURLConnection connection = null;
        try {
            // 接続先URL
            // URLのendpointをパラメータで渡せるようにする
            // idとパスワードをパラメータで送る方法を考える
            URL url = new URL("http://localhost:3000/" + URLEncoder.encode(endpoint, "UTF-8"));

            connection = (HttpURLConnection) url.openConnection();

            //  接続できたら
            final int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 読み込む
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                //  取得した文字列からJSONを作成
                JSONObject jsonObject = new JSONObject(sb.toString());
//                ans = jsonObject.getInt("ans");
                msg = "正常に接続されました";
            } else {
                msg = "HTTP説即エラー";
            }

        } catch (MalformedURLException e) {
            msg = "MalformedURLException:" + e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            msg = "Exception:" + e.getMessage();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return ans;
    }

    // 進捗状況をUIに反映するための処理（UI操作可能）
    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    // doInBackgroundの事後処理
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        callBackTask.CallBack(result);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // 呼び出し元で、CallBackTaskを割り当てる
    public void setOnCallBack(CallBackTask _cbj) {
        callBackTask = _cbj;
    }

    // コールバック用のstaticなクラス
    public static class CallBackTask {
        // 呼び出し側でoverrideする
        public void CallBack(int result) {

        }
    }

}
