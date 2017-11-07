package com.hdmes.handingv2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.hdmes.handingv2017.Help.HelpAdapter;
import com.hdmes.handingv2017.Help.HelpData;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private ListView lv;
    private HelpAdapter helpAdapter;
    private List<HelpData> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        lv=(ListView) findViewById(R.id.help_lv);
        helpAdapter=new HelpAdapter(this,getData());
        lv.setAdapter(helpAdapter);
    }

    public List<HelpData> getData() {
        mList=new ArrayList<HelpData>();

        HelpData helpdata=new HelpData();
        helpdata.setHelpinfo("1.如何成为天车宝注册用户？");

        HelpData helpdata1=new HelpData();
        helpdata1.setHelpinfo("2.如何修改个人信息？");

        HelpData helpdata2=new HelpData();
        helpdata2.setHelpinfo("3.如何更改设备注册信息？");

        HelpData helpdata3=new HelpData();
        helpdata3.setHelpinfo("4.无法接受设备信息，怎么办？");

        HelpData helpdata4=new HelpData();
        helpdata4.setHelpinfo("5.点击联系我们按钮是出现闪退是怎么回事？");

        HelpData helpdata5=new HelpData();
        helpdata5.setHelpinfo("6.大数据信息不准确是怎么回事？");

        mList.add(helpdata);
        mList.add(helpdata1);
        mList.add(helpdata2);
        mList.add(helpdata3);
        mList.add(helpdata4);
        mList.add(helpdata5);

        return mList;
    }
}
