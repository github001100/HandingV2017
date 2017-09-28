package com.hdmes.handingv2017;




import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity  {
//    private RadioGroup rpTab;
//    private RadioButton rbHome,rbNews,rbMe;
//    private HomeFragment fg1;
//    private NewsFragment fg2;
//    private MeFragment fg3;
    protected Toast toast = null;//定义一个吐司
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //bindView();
    }
//    private void bindView() {
//        rpTab = (RadioGroup)findViewById(R.id.rd_group);
//        rpTab.setOnCheckedChangeListener(this);
//
//        rbHome = (RadioButton)findViewById(R.id.rd_menu_home);
//        rbNews = (RadioButton)findViewById(R.id.rd_menu_News);
//        rbMe = (RadioButton)findViewById(R.id.rd_menu_Me);
//        rbHome.setChecked(true);
//    }
//    public void hideAllFragment(FragmentTransaction transaction){
//        if(fg1!=null){
//            transaction.hide(fg1);
//        }
//        if(fg2!=null){
//            transaction.hide(fg2);
//        }
//        if(fg3!=null){
//            transaction.hide(fg3);
//        }
//    }
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        hideAllFragment(transaction);
//        switch (checkedId){
//            case R.id.rd_menu_home:
//                if(fg1==null){
//                    fg1 = new HomeFragment();
//                    transaction.add(R.id.fragment_container,fg1);
//                }else{
//                    transaction.show(fg1);
//                }
//                break;
//            case R.id.rd_menu_News:
//                if(fg2==null){
//                    fg2 = new NewsFragment();
//                    transaction.add(R.id.fragment_container,fg2);
//                }else{
//                    transaction.show(fg2);
//                }
//                break;
//            case R.id.rd_menu_Me:
//                if(fg3==null){
//                    fg3 = new MeFragment();
//                    transaction.add(R.id.fragment_container,fg3);
//                }else{
//                    transaction.show(fg3);
//                }
//                break;
//        }
//        transaction.commit();
//    }
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
