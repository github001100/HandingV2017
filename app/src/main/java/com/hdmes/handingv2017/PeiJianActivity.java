package com.hdmes.handingv2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hdmes.handingv2017.PeiJian.PeiJianAdapter;
import com.hdmes.handingv2017.PeiJian.PeiJianData;

import java.util.ArrayList;
import java.util.List;

public class PeiJianActivity extends AppCompatActivity {

    private ListView lv;
    private PeiJianAdapter peijianAdapter;
    private ArrayList<PeiJianData> mList;
    public static int clickposition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pei_jian);

        lv = (ListView) findViewById(R.id.peijian_lv);//得到ListView对象的引用
        peijianAdapter=new PeiJianAdapter(this,getData());
        //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, strs));
        //lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(peijianAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //设置监听事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != clickposition){
                    clickposition=position;
                }
                else{
                    clickposition=-1;
                }
                peijianAdapter.notifyDataSetChanged();
            }
        });
    }
    private List<PeiJianData> getData(){
        mList=new ArrayList<PeiJianData>();
        PeiJianData peiJianData=new PeiJianData();
        peiJianData.setPrice("2000.00");
        peiJianData.setType("参数：200*300-1500");
        peiJianData.setImgpath(R.mipmap.peijian_);

        PeiJianData peiJianData1=new PeiJianData();
        peiJianData1.setPrice("3000.00");
        peiJianData1.setType("参数：300*300-1500");
        peiJianData1.setImgpath(R.mipmap.peijian_);

        PeiJianData peiJianData2=new PeiJianData();
        peiJianData2.setPrice("4000.00");
        peiJianData2.setType("参数：100*300-1500");
        peiJianData2.setImgpath(R.mipmap.peijian_);

        PeiJianData peiJianData3=new PeiJianData();
        peiJianData3.setPrice("2000.00");
        peiJianData3.setType("参数：200*300-1500");
        peiJianData3.setImgpath(R.mipmap.peijian_);

        mList.add(peiJianData);
        mList.add(peiJianData1);
        mList.add(peiJianData2);
        mList.add(peiJianData3);
        mList.add(peiJianData3);
        mList.add(peiJianData3);
        mList.add(peiJianData3);
        mList.add(peiJianData3);
        /*
         *静态数据
        mList.add(111);
        mList.add(111);
        mList.add(111);
        mList.add(111);
        mList.add(111);
        mList.add(111);
        mList.add(111);
        */
        return mList;
    }
}
