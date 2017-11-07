package com.hdmes.handingv2017.UserInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdmes.handingv2017.R;

import java.util.List;

/**
 * Created by HANDING on 2017/11/5.
 */

public class UserInfoAdapter extends BaseAdapter{
    private Context context;
    private List<UserInfoData> listdata;

    public UserInfoAdapter(Context context,List<UserInfoData> listdata){
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
        View view =View.inflate(context, R.layout.userinfo,null);
        //初始化view
        TextView tyle=(TextView) view.findViewById(R.id.textView33);
        TextView info=(TextView) view.findViewById(R.id.textView34);
        //绑定数据
        UserInfoData uidata=listdata.get(position);
        tyle.setText(uidata.getType());
        info.setText(uidata.getInfo());
        //返回绑定数据
        return view;
    }
}
