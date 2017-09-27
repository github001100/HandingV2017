package com.hdmes.handingv2017;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private TextView txt3,txt4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //为忘记密码和没有账号添加下划线
        txt3=((TextView)findViewById(R.id.textView3));
        txt4=((TextView)findViewById(R.id.textView4));
        txt3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        txt4.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }
}
