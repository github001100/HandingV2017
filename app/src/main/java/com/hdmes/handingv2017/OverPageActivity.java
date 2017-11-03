package com.hdmes.handingv2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;


public class OverPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_page);
        //定时延迟跳转页面
        Timer time=new Timer();
        time.schedule(new MyTask(),2000);
    }
    class MyTask extends TimerTask{
       @Override
       public void run() {
           Intent intent = new Intent(OverPageActivity.this,MainActivity.class);
           startActivity(intent);
           finish();
       }
   }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_MENU) {
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
