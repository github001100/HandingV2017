package com.hdmes.handingv2017;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.http.HttpsConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdmes.handingv2017.MyActivitys.Food;
import com.hdmes.handingv2017.MyActivitys.MyAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView txt3, txt4;
    protected Toast toast = null;//定义一个吐司
    private long mExitTime;
    private MyAdapter mAdapter;
    private List<Food> data = new ArrayList<Food>();
    private ListView lv;
    protected Context context;
    private ProgressDialog progressDialog;
    private ProgressDialog pd;
    private String Stated;
    private String strTmp;
    private String strTmp2;
    public boolean netStatus = false;
    public boolean wifi;
    public boolean internet;
    private String HttpUrl = "http://192.168.0.188:8088";
    private Button btnLogin = null;

    //消息处理机制 Handler +Message +Looper+UI线程(主线程)
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    btnLogin.setText("连接超时...");
                    toast = Toast.makeText(getApplicationContext(), "连接超时!", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case 2:
                    btnLogin.setText("连接超时..");
                    toast = Toast.makeText(getApplicationContext(), "连接超时!", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case 1:
                    //Toast.makeText(MainActivity.this, "toast", Toast.LENGTH_SHORT).show();
                    progressDialog.setMessage("用户名或密码错误");
                    progressDialog.show();
                    btnLogin.setText("登录");
                    btnLogin.setClickable(true);
                    if (toast != null) {
                        toast.cancel();
                        toast = Toast.makeText(getApplicationContext(), "用户名或密码错误!", Toast.LENGTH_SHORT);
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "用户名或密码错误!", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                    break;
                case 0:
                    //progressDialog.setMessage("登录成功..稍后!");
                    //progressDialog.show();
                    btnLogin.setText("登录成功");
                    btnLogin.setClickable(false);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //网络状态检查
        context = getApplicationContext();
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        //internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        //if (internet || wifi) {
        //执行相关操作
        //netStatus = true;
        //Toast.makeText(context, "网络已连接！", Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(this);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIcon(R.drawable.h100);
        progressDialog.setTitle("登录提示");
        progressDialog.setMessage("Loading...");
        //progressDialog.setCancelable(false);
        // 方式一：new Dialog
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //dialog.show();
        //ProgressDialog dialog2 = ProgressDialog.show(this, "提示", "正在登陆中");
        //progressDialog.show();
        //为忘记密码和没有账号添加下划线
        txt3 = ((TextView) findViewById(R.id.textView3));
        txt4 = ((TextView) findViewById(R.id.textView4));
        txt3.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt4.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        btnLogin = (Button) findViewById(R.id.button);//获取登录按钮
        btnLogin.setOnClickListener(new Button.OnClickListener() {//设置监听
            public void onClick(View v) {

                stmpLogin();
            }

        });
    }

    /**
     * Md5 加密函数
     *
     * @param sSecret
     * @return
     */
    public static String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 登录输入判断
     */
    private void stmpLogin() {
        EditText Ev1 = (EditText) findViewById(R.id.editText_username);
        EditText Ev2 = (EditText) findViewById(R.id.editText2_userpass);
        strTmp = Ev1.getText().toString();
        //Ev2.setText("2ec81e7e04bb7e465b551c0d1535c42e");//密码加密后
        strTmp2 = Ev2.getText().toString();
        if (TextUtils.isEmpty(strTmp) & TextUtils.isEmpty(strTmp2)) {
            //toast.setText("请输入用户名！");
            if (toast != null) {
                toast.cancel();
                toast = Toast.makeText(getApplicationContext(), "请输入用户名和密码", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "请输入用户名和密码", Toast.LENGTH_SHORT);
            }
            toast.show();
        } else if (TextUtils.isEmpty(strTmp)) {
            if (toast != null) {
                toast.cancel();
                toast = Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT);
            }
            toast.show();
        } else if (TextUtils.isEmpty(strTmp2)) {
            if (toast != null) {
                toast.cancel();
                toast = Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT);
            }
            toast.show();
        } else {
            strTmp2 = getMd5Value(Ev2.getText().toString());//登录密码加密后

            if (internet || wifi) {
                netStatus = true;

                //lv = (ListView) findViewById(R.id.lv);
                btnLogin.setText("Loading..");//正在登录
                btnLogin.setClickable(false);

                pd = new ProgressDialog(MainActivity.this);
                mAdapter = new MyAdapter(MainActivity.this, data);
                //lv.setAdapter(mAdapter);
                new MainActivity.MyFoodTask().execute();
            } else {
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(context, "亲，网络异常，请检查网络连接！", Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(context, "亲，网络异常，请检查网络连接！", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);

                } else {
                    toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);

                }
                toast.show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * MyFoodTask
     */
    private class MyFoodTask extends AsyncTask<String, Void, Map<String, Object>> {

        @Override
        protected void onPreExecute() {

            pd.setMessage("Loading...");

        }

        /**
         * 通过 web Http 获取服务器数据  到Map集合
         *
         * @param params
         * @return
         */
        @Override
        protected Map<String, Object> doInBackground(String... params) {

            String path = HttpUrl + "/Login/CheckLogin";//本地服务器请求地址
            HttpClient httpClient = new DefaultHttpClient();

            HttpsURLConnection httpsURLConnection;
            //httpsURLConnection.setConnectTimeout(50000);

            HttpGet httpGet = new HttpGet(path);
            BasicNameValuePair UserName = new BasicNameValuePair("username", strTmp);
            BasicNameValuePair UserPassword = new BasicNameValuePair("password", strTmp2);
            BasicNameValuePair code = new BasicNameValuePair("code", "code");
            //POST提交参数放到List 里
            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            //把BasicNameValuePair放入集合中
            parameters.add(UserName);
            parameters.add(UserPassword);
            parameters.add(code);

            HttpParams paramss = null;
            paramss = httpClient.getParams();

            try {//将List传到后台Web API（C# 服务器）
                HttpPost httpPost = new HttpPost(path);
                int TIME_OUT_IN_SECONDS = 8000;
                HttpConnectionParams.setConnectionTimeout(paramss, TIME_OUT_IN_SECONDS);//超时时间10秒
                HttpConnectionParams.setSoTimeout(paramss, 35000);
                UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(parameters, "utf-8");//这里异常, java.io.UnsupportedEncodingExceptio处理
                httpPost.setEntity(entity1);
                HttpResponse resp = null;//初始化响应
                try {
                    resp = httpClient.execute(httpPost);//请求耗时操作 网络(有IoException 异常需处理)
                } catch (ConnectTimeoutException e) {
                    Log.i("000", e.getMessage());

                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }finally {
                    httpClient.getConnectionManager().shutdown();

                }

                //HttpResponse resp = client.execute(get);
                if (resp != null) {
                    int scode = resp.getStatusLine().getStatusCode();
                    if (resp.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = resp.getEntity();
                        String result = EntityUtils.toString(entity);//有异常需处理IOException
                        Log.i("111", "result=" + result);
                        Map<String, Object> map = parseJson(result);//有异常 java.lang.exception
                        return map;
                    }
                } else {


                }

            } catch (Exception e) {
                // TODO Auto-generated catch block

                Message msg = new Message();
                msg.what = 3;
                mHandler.sendMessage(msg);
                httpClient.getConnectionManager().shutdown();
                e.printStackTrace();
            }
            return null;
        }

        /**
         * onPostExecute
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Map<String, Object> result) {
            pd.dismiss();
            //isNextPage = (Boolean) result.get("isNextPage");
            data.addAll((List<Food>) result.get("foodList"));
            mAdapter.notifyDataSetChanged();//通知更新数据
            for (int i = 0; i < data.size(); i++) {
                Food f = data.get(i);
                new MainActivity.MyFoodTask.MyImgTask().execute(f);
            }
        }

        /**
         * 将服务器返回的数据进行Json解析
         * 登录信息
         *
         * @param json
         * @return
         * @throws Exception
         */
        protected Map<String, Object> parseJson(String json) throws Exception {

            Map<String, Object> result = new HashMap<String, Object>();
            List<Food> lists = new ArrayList<Food>();
            Food f = null;
            f = new Food();
            JSONObject smallObj = new JSONObject(json);
            //实体entity赋值
            //f.setId(smallObj.getInt("Stated"));
            f.setTitle(smallObj.getString("state"));
            f.setContent(smallObj.getString("state"));
            f.setImgPath(smallObj.getString("message"));
            lists.add(f);

            result.put("foodList", lists);
            data.clear();//初始化List 否则List 逐渐增加
            data.addAll((List<Food>) result.get("foodList"));
            Food st = data.get(0);
            Stated = st.getContent();//Stated返回状态 success Or error

            if (Stated.toString().trim().equals("error".toString().trim())) {

                //子线程：通知UI线程更新UI
                try {
                    Thread.sleep(500);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {//登录成功，返回首页相关操作
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);

                finish();//结束登录页面
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HomeActivity.class);
                //intent.setClass(MainActivity.this, ModifyActivity.class);

                intent.putExtra("user", strTmp);//保存用户名
                //intent.putExtra("username", strTmp);//给intent 添加额外数据--当前登录用户
                //startActivityForResult(intent,1);//判断登录是否成功
                startActivity(intent);
            }
            return result;
        }

        private class MyImgTask extends AsyncTask<Food, Void, Food> {
            /**
             * @param params
             * @return
             */
            @Override
            protected Food doInBackground(Food... params) {
                Food f = params[0];
                String imgPath = HttpUrl + "/Login/CheckLogin" + f.getImgPath();
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(imgPath);
                try {
                    HttpResponse resp = client.execute(get);
                    if (resp.getStatusLine().getStatusCode() == 200) {

                        HttpEntity entity = resp.getEntity();
                        byte img[] = EntityUtils.toByteArray(entity);
                        f.setImgData(img);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return f;
            }

            @Override
            protected void onPostExecute(Food result) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}