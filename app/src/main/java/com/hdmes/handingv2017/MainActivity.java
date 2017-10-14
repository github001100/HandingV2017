package com.hdmes.handingv2017;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txt3,txt4;
    private EditText count,password;
    protected Toast toast = null;//定义一个吐司
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //为忘记密码和没有账号添加下划线
        txt3=((TextView)findViewById(R.id.textView3));
        txt4=((TextView)findViewById(R.id.textView4));
        txt3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        txt4.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        //获取账号密码EditText的id
        count=(EditText)findViewById(R.id.editText2);
        password=(EditText)findViewById(R.id.editText3);


        //账号密码输入提示
        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setOnClickListener(new Button.OnClickListener() {//设置监听
            public void onClick(View v) {
                if(count.length()==0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"请输入账号或密码！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //使用Bundle将用户名传递给Mefragment中
//                    MeFragment meFragment=new MeFragment();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("count",count.getText().toString().trim());
//                    meFragment.setArguments(bundle);
//                    FragmentManager fragmentManager=getSupportFragmentManager();
//                    FragmentTransaction transaction=fragmentManager.beginTransaction();
//                    HomeFragment homeFragment=HomeFragment.newInstance(count.getText().toString(),"");
//                    transaction.add(R.id.linearlayout,homeFragment);
//                    transaction.commit();
                    //切换主页面
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    intent.putExtra("user",count.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

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
