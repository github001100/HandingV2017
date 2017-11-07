package com.hdmes.handingv2017.Help;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdmes.handingv2017.R;

import java.util.List;

/**
 * Created by HANDING on 2017/11/6.
 */

public class HelpAdapter extends BaseAdapter{
    private Context context;
    private List<HelpData> listdata;

    public HelpAdapter (Context context,List<HelpData> listdata){
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
        View view= View.inflate(context, R.layout.help_item,null);
        //初始化view
        TextView help=(TextView) view.findViewById(R.id.textView52);
        //绑定数据
        HelpData helpdata=listdata.get(position);
        help.setText(helpdata.getHelpinfo());
        //返回绑定数据
        return view;
    }
}
