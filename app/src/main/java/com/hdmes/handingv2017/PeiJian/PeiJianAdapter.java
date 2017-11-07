package com.hdmes.handingv2017.PeiJian;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdmes.handingv2017.PeiJianActivity;
import com.hdmes.handingv2017.R;

import java.util.List;

/**
 * 备品备件适配器
 * Created by HANDING on 2017/11/4.
 */

public class PeiJianAdapter extends BaseAdapter{
    private Context context;
    private List<PeiJianData> listdata;

    public PeiJianAdapter(Context context,List<PeiJianData> listdata){
        this.context=context;
        this.listdata=listdata;
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context,R.layout.peijian_item,null);
        //view.setBackgroundColor(Color.parseColor("#969696"));
        //初始化view
        ImageView img=(ImageView) view.findViewById(R.id.imageView10);
        TextView txt_pic=(TextView) view.findViewById(R.id.peijian_tv_title);
        TextView txt_type=(TextView) view.findViewById(R.id.textView2);
        TextView chk=(TextView) view.findViewById(R.id.peijian_checked);
        if(position == PeiJianActivity.clickposition){
            chk.setTextColor(Color.rgb(63,125,225));
        }
        //绑定数据到view
        PeiJianData pjdata= listdata.get(position);
        img.setImageResource(pjdata.getImgpath());
        txt_pic.setText(pjdata.getPrice());
        txt_type.setText(pjdata.getType());
        //返回绑定数据
        return view;
    }
}
