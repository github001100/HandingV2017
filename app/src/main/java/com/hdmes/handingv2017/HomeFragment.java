package com.hdmes.handingv2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class HomeFragment extends Fragment {
    private ImageButton baojia,jisuan;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
//        baojia=(ImageButton)view.findViewById(R.id.imageButton);
//        View.OnClickListener listener0=null;
//        listener0=new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("111111");
//                Intent intent = new Intent(getActivity(),BaojiaActivity.class);
//                startActivity(intent);
//            }
//        };
//        baojia.setOnClickListener(listener0);
//        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //报价页面显示
        baojia = (ImageButton) getActivity().findViewById(R.id.imageButton);
        baojia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),BaojiaActivity.class);
                startActivity(intent1);
            }
        });
        //计算页面显示
        jisuan=(ImageButton)getActivity().findViewById(R.id.imageButton2);
        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getActivity(), JisuanActivity.class);
                startActivity(intent2);
            }
        });
    }

}