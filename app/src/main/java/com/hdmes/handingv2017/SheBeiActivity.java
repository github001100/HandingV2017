package com.hdmes.handingv2017;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SheBeiActivity extends AppCompatActivity {
    private Spinner cranes;
    private String[] typeItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_bei);

        cranes=(Spinner) findViewById(R.id.spinner10);
        //绑定数据源
        typeItems = getResources().getStringArray(R.array.data);
        //建立Adapter并绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeItems);
        adapter.setDropDownViewResource(R.layout.dropdown_stytle);
        //绑定Adapter到控件
        cranes.setAdapter(adapter);
        cranes.setOnItemSelectedListener(new SheBeiActivity.SpinnerXMLSelectedListener());
    }

    public class SpinnerXMLSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150, 150, 150));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
