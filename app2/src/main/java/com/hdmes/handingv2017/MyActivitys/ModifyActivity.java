package com.hdmes.handingv2017.MyActivitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hdmes.handingv2017.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ModifyActivity extends AppCompatActivity {
    private Spinner cranes;
    private TextView tv;
    private String[] typeItems;
    private String url = "http://192.168.0.188:8088/SystemManage/CraneGZ0/CraneModifyAdd";//服务器接口地址
    protected EditText h_content;
    private ProgressDialog progressDialog;

    protected String hname = "Admin";   //提报人
    protected String hcraneid;//起重机识别码
    protected String hoperate;//故障内容描述
    protected String hcontent;//备注

    //Handler用于接收服务端返回的数据更新ui
    private Handler hanlder = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //String result = (String) msg.obj;
                    //h_content.setText("服务器返回: " + result);
                    //弹出提交成功对话
                    progressDialog.setMessage("您的反馈信息我们已收到！");
                    progressDialog.show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        //获取用户名
        Intent intent = getIntent();//检索intent赋值给变量
        //Bundle bundle = intent.getExtras();//得到intent附带的额外数据
        String str;//= bundle.getString("user");//返回制定Key值
        str = intent.getStringExtra("username");
        hname = str;//提报人
        cranes = (Spinner) findViewById(R.id.spinner);
        h_content = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView14);
        //设置弹出框提示
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIcon(R.drawable.h100);
        progressDialog.setTitle("温馨提示：");
        progressDialog.setMessage("Loading...");

        Button button5 = (Button) findViewById(R.id.button5);
        //绑定数据源
        typeItems = getResources().getStringArray(R.array.data);
        //建立Adapter并绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeItems);
        adapter.setDropDownViewResource(R.layout.dropdown_stytle);
        //绑定Adapter到控件
        cranes.setAdapter(adapter);
        cranes.setOnItemSelectedListener(new ModifyActivity.SpinnerXMLSelectedListener());

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showNormalDialog();
                /**
                 * 开辟一个子线程访问网络，否则会抛出异常
                 */
                hcontent = h_content.getText().toString();
                hoperate = tv.getText().toString();

                new Thread() {
                    @Override
                    public void run() {

                        NameValuePair pair1 = new BasicNameValuePair("hname", hname);
                        NameValuePair pair2 = new BasicNameValuePair("hcrane_id", hcraneid);
                        NameValuePair pair3 = new BasicNameValuePair("hoperate", hoperate);
                        NameValuePair pair4 = new BasicNameValuePair("hcontent", hcontent);

                        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                        pairList.add(pair1);
                        pairList.add(pair2);
                        pairList.add(pair3);
                        pairList.add(pair4);
                        try {

                            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                                    pairList);
                            // URl是接口地址
                            HttpPost httpPost = new HttpPost(url);
                            //设置字符编码格式
                            UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(pairList, "utf-8");
                            httpPost.setEntity(entity1);
                            // 将请求体内容加入请求中
                            //httpPost.setEntity(requestHttpEntity);
                            // 需要客户端对象来发送请求
                            HttpClient httpClient = new DefaultHttpClient();
                            // 发送请求
                            HttpResponse response = httpClient.execute(httpPost);
                            // 显示响应
                            showResponseResult(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            }

        });

    }

    /**
     * 显示响应结果到命令行和TextView
     *
     * @param response
     */
    private void showResponseResult(HttpResponse response) {
        if (null == response) {
            return;
        }

        HttpEntity httpEntity = response.getEntity();
        try {
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String result1 = "";
            String line = "";
            while (null != (line = reader.readLine())) {
                result1 += line;

            }

            System.out.println(result1);
            /**
             * 把服务器返回的结果 发送到hanlder中，在子线程中是不允许更新ui的
             */
            hanlder.obtainMessage(0, result1).sendToTarget();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @setIcon 设置对话框图标
     * @setTitle 设置对话框标题
     * @setMessage 设置对话框消息提示
     * setXXX方法返回Dialog对象，因此可以链式设置属性
     */
    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ModifyActivity.this);
        normalDialog.setIcon(R.drawable.h100);
        normalDialog.setTitle("确定要提报下列起重机");
        normalDialog.setMessage(tv.getText());
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        hcontent = h_content.getText().toString();
                        hoperate = tv.getText().toString();

                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        /**
         * 下拉列表选择时间
         *
         * @param arg0
         * @param arg1
         * @param position
         * @param arg3
         */
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

            //tv.setText("识别码：" + typeItems[position]);
            hcraneid = typeItems[position];
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }

}