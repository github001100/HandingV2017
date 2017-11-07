package com.hdmes.handingv2017;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BaojiaActivity extends AppCompatActivity {
//    private TextView text;
    private Spinner type,load,workclass,height;
    private EditText span;
    private CheckBox sijishi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baojia);

        sijishi=(CheckBox) findViewById(R.id.checkBox3) ;
        span=(EditText) findViewById(R.id.editText3);
        //初始化控件
        type=(Spinner)findViewById(R.id.spinner);
        //绑定数据源
        String[] typeItems =getResources().getStringArray(R.array.cranetype);
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
            //初始化控件
            load=(Spinner)findViewById(R.id.spinner2);
            if(arg3==0 || arg3==2 || arg3==3){
                //建立Adapter并绑定数据源
                ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(BaojiaActivity.this,R.array.xscraneload,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(R.layout.dropdown_stytle);
                //绑定Adapter到控件
                load.setAdapter(adapter);
                if(arg3==3){
                    span.setEnabled(false);//跨度不可用
                    sijishi.setVisibility(View.INVISIBLE);
                }
                else {
                    span.setEnabled(true);//跨度可用
                    sijishi.setVisibility(View.VISIBLE);
                }
            }
            if(arg3==1 ||arg3==4){
                //建立Adapter并绑定数据源
                ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(BaojiaActivity.this,R.array.xdcraneload,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(R.layout.dropdown_stytle);
                //绑定Adapter到控件
                load.setAdapter(adapter);
                if(arg3==4){
                    span.setEnabled(false);//跨度不可用
                    sijishi.setVisibility(View.INVISIBLE);
                }
                else {
                    span.setEnabled(true);//跨度可用
                    sijishi.setVisibility(View.VISIBLE);
                }
            }

            load.setOnItemSelectedListener(new SpinnerXMLLoadSelectedListener());
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
    /*
    //使用XML形式操作
    class SpinnerXMLLoadSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150,150,150));
//            ((TextView) arg0.getChildAt(0)).setTextSize(14);
//            TextView v1 =(TextView)arg1;
//            v1.setTextColor(Color.BLACK);
//            text.setText("你使用什么样的手机："+adapter.getItem(position));
            //初始化控件
            span=(Spinner)findViewById(R.id.spinner3);
            //建立Adapter并绑定数据源
            ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(BaojiaActivity.this,R.array.cranespan,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.dropdown_stytle);
            //绑定Adapter到控件
            span.setAdapter(adapter);
            span.setOnItemSelectedListener(new SpinnerXMLSpanSelectedListener());
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
    */
    //使用XML形式操作
    class SpinnerXMLLoadSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150,150,150));
//            ((TextView) arg0.getChildAt(0)).setTextSize(14);
//            TextView v1 =(TextView)arg1;
//            v1.setTextColor(Color.BLACK);
//            text.setText("你使用什么样的手机："+adapter.getItem(position));
            //初始化控件
            workclass=(Spinner)findViewById(R.id.spinner4);
            //建立Adapter并绑定数据源
            ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(BaojiaActivity.this,R.array.craneclass,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.dropdown_stytle);
            //绑定Adapter到控件
            workclass.setAdapter(adapter);
            workclass.setOnItemSelectedListener(new SpinnerXMLClassSelectedListener());
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
    //使用XML形式操作
    class SpinnerXMLClassSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150,150,150));
//            ((TextView) arg0.getChildAt(0)).setTextSize(14);
//            TextView v1 =(TextView)arg1;
//            v1.setTextColor(Color.BLACK);
//            text.setText("你使用什么样的手机："+adapter.getItem(position));
            //初始化控件
            height=(Spinner)findViewById(R.id.spinner5);
            //建立Adapter并绑定数据源
            ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(BaojiaActivity.this,R.array.craneheight,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.dropdown_stytle);
            //绑定Adapter到控件
            height.setAdapter(adapter);
            height.setOnItemSelectedListener(new SpinnerXMLHeightSelectedListener());
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
    //使用XML形式操作
    class SpinnerXMLHeightSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150,150,150));
//            ((TextView) arg0.getChildAt(0)).setTextSize(14);
//            TextView v1 =(TextView)arg1;
//            v1.setTextColor(Color.BLACK);
//            text.setText("你使用什么样的手机："+adapter.getItem(position));
//            //初始化控件
//            height=(Spinner)findViewById(R.id.spinner5);
//            //建立Adapter并绑定数据源
//            ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(BaojiaActivity.this,R.array.craneheight,android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(R.layout.dropdown_stytle);
//            //绑定Adapter到控件
//            height.setAdapter(adapter);
//            height.setOnItemSelectedListener(new SpinnerXMLHeightSelectedListener());
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
}
