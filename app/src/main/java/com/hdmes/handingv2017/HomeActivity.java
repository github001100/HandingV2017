package com.hdmes.handingv2017;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hdmes.handingv2017.eventmessage.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "vivi";
    @Bind(R.id.fragment_container)
    FrameLayout mContent;
    @Bind(R.id.rd_menu_home)
    RadioButton mRbHome;
    @Bind(R.id.rd_menu_News)
    RadioButton mRbShop;
    @Bind(R.id.rd_menu_Me)
    RadioButton mRbMessage;
    @Bind(R.id.rd_group)
    RadioGroup  mRgTools;
    private Fragment[] mFragments;
    private int mIndex;
    protected Toast toast = null;//定义一个吐司
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initFragment();
    }
    private void initFragment() {
        //首页
        HomeFragment homeFragment =new HomeFragment();
        //资讯
        NewsFragment newsFragment =new NewsFragment();
        //我的
        MeFragment meFragment =new MeFragment();
        //添加到数组
        mFragments = new Fragment[]{homeFragment,newsFragment,meFragment};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.fragment_container,homeFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if(mIndex==index){
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.fragment_container,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex=index;
    }
    @OnClick({R.id.rd_menu_home, R.id.rd_menu_News, R.id.rd_menu_Me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd_menu_home:
                setIndexSelected(0);
                break;
            case R.id.rd_menu_News:
                setIndexSelected(1);
                break;
            case R.id.rd_menu_Me:
                setIndexSelected(2);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setGoIndex(EventMessage eventMessage){
        Log.d(TAG, "setGoIndex: "+eventMessage.getTag());
        if(eventMessage!=null){
            int tag = eventMessage.getTag();
            if(tag== EventMessage.EventMessageAction.TAG_GO_MAIN){
                mRbHome.performClick();
                setIndexSelected(0);
            }else if(tag== EventMessage.EventMessageAction.TAG_GO_SHOPCART){
                mRbShop.performClick();
                setIndexSelected(1);
            }else if(tag== EventMessage.EventMessageAction.TAG_GO_MESSAGE){
                mRbMessage.performClick();
                setIndexSelected(2);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // mRbHome.performClick();
    }
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    //判断用户是否点击了“返回键”
    if (keyCode == KeyEvent.KEYCODE_BACK) {
        //与上次点击返回键时刻作差
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            //大于2000ms则认为是误操作，使用Toast进行提示
            if (toast != null) {
                toast.cancel();
                toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);

            } else {
                toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);

            }
            toast.show();
            //并记录下本次点击“返回键”的时刻，以便下次进行判断
            mExitTime = System.currentTimeMillis();
        } else {
            //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
            System.exit(0);
        }
        return true;
    }
    return super.onKeyDown(keyCode, event);
}
}

