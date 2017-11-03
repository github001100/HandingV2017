package com.hdmes.handingv2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hdmes.handingv2017.MyActivitys.ModifyActivity;
import com.hdmes.handingv2017.MyActivitys.VideoActivity;


public class HomeFragment extends Fragment {

    private ImageButton baojia, jisuan, baoxiu,shuju,jiankong,beijian,women;
    private ImageButton videoIBtn;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        baoxiu = (ImageButton) view.findViewById(R.id.imageButton10);
        baojia = (ImageButton) view.findViewById(R.id.imageButton);
        jisuan = (ImageButton) view.findViewById(R.id.imageButton2);
        shuju = (ImageButton) view.findViewById(R.id.imageButton3);
        videoIBtn = (ImageButton) view.findViewById(R.id.imageButton11);
        beijian = (ImageButton) view.findViewById(R.id.imageButton12);
        women = (ImageButton) view.findViewById(R.id.imageButton13);

        //报价按钮特效
        baojia.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.baojiapress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.baojia));
                }
                return false;
            }
        });
        //报价页面显示
        baojia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), BaojiaActivity.class);
                startActivity(intent1);
            }
        });
        //计算按钮特效
        jisuan.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.shejipress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.sheji));
                }
                return false;
            }
        });
        //计算页面显示
        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), JisuanActivity.class);
                startActivity(intent2);
            }
        });

        //数据按钮特效
        shuju.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.shujupress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.shuju));
                }
                return false;
            }
        });
        //数据页面显示
        shuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), ShujuActivity.class);
                startActivity(intent2);
            }
        });

        //报修按钮特效
        baoxiu.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.baoxiupress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.baoxiu));
                }
                return false;
            }
        });
        //报修页面显示
        baoxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), ModifyActivity.class);
                String str = ((HomeActivity) getActivity()).getData();

                intent3.putExtra("username", str);//给intent 添加额外数据--当前登录用户
                startActivity(intent3);
            }
        });
        //视频按钮特效
        videoIBtn.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.jiankongpress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.jiankong));
                }
                return false;
            }
        });
        //视频监控页面显示
        videoIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent2);
            }
        });
        //备件按钮特效
        beijian.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.beijianpress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.beijian));
                }
                return false;
            }
        });
        //备件页面显示
        beijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), PeiJianActivity.class);
                startActivity(intent2);
            }
        });

        //我们按钮特效
        women.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.womenpress));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.women));
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}