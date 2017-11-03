package com.hdmes.handingv2017.MyActivitys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdmes.handingv2017.R;

import java.util.List;

/**
 * 汉鼎新闻适配器 ListView
 * Created by Administrator on 2017/9/11 0011.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;

    private List<Food> data;

    public MyAdapter(Context context, List<Food> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.news_model, null);

        }
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_icon);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_desc = (TextView) convertView.findViewById(R.id.tv_des);

        Food food = data.get(position);

        if (food.getImgData() == null) {
            iv_img.setImageResource(R.drawable.timg);//默认
        } else {
            byte[] imgData = food.getImgData();
            Bitmap bm = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            iv_img.setImageBitmap(bm);
        }

        tv_name.setText(food.getTitle());
        tv_desc.setText(food.getContent());

        return convertView;
    }

}