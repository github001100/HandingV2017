package com.hdmes.handingv2017;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class JisuanActivity extends AppCompatActivity {
    private Spinner type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisuan);
        //初始化控件
        type=(Spinner)findViewById(R.id.spinner6);
        //绑定数据源
        String[] typeItems =getResources().getStringArray(R.array.jisuantype);
//        text = (TextView) findViewById(R.id.textView6);
        //建立Adapter并绑定数据源
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,typeItems);
        adapter.setDropDownViewResource(R.layout.dropdown_stytle);
        //绑定Adapter到控件
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
    }
    //使用XML形式操作
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150,150,150));
//            ((TextView) arg0.getChildAt(0)).setTextSize(14);
//            TextView v1 =(TextView)arg1;
//            v1.setTextColor(Color.BLACK);
//            text.setText("你使用什么样的手机："+adapter.getItem(position));
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
}
