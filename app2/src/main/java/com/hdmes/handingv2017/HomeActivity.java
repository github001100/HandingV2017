package com.hdmes.handingv2017;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hdmes.handingv2017.MyActivitys.Food;
import com.hdmes.handingv2017.MyActivitys.ModifyActivity;
import com.hdmes.handingv2017.MyActivitys.MyAdapter;
import com.hdmes.handingv2017.MyActivitys.NewsBean;
import com.hdmes.handingv2017.MyActivitys.NewsUtils;
import com.hdmes.handingv2017.eventmessage.EventMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    RadioGroup mRgTools;
    private Fragment[] mFragments;
    private int mIndex;
    protected Toast toast = null;//定义一个吐司
    private long mExitTime;
    private String ct;//接收参数MainA

    private List<Food> data = new ArrayList<Food>();
    private MyAdapter mAdapter;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        //接收MainActivity传递的count
        Intent intent = getIntent();
        ct = intent.getStringExtra("user");
        initFragment();

        //Intent intent1 = new Intent();
        //intent1.setClass(HomeActivity.this, ModifyActivity.class);
        //intent1.putExtra("username", ct);//给intent 添加额外数据--当前登录用户

    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fragment_container, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex = index;
    }
    //设置函数用于MeFragment
    public String getData() {
        return ct;
    }

    private void initFragment() {
        //首页
        HomeFragment homeFragment = new HomeFragment();
        //资讯
        NewsFragment newsFragment = new NewsFragment();
        //我的
        MeFragment meFragment = new MeFragment();
        //添加到数组
        mFragments = new Fragment[]{homeFragment, newsFragment, meFragment};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.fragment_container, homeFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
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
    public void setGoIndex(EventMessage eventMessage) {
        Log.d(TAG, "setGoIndex: " + eventMessage.getTag());
        if (eventMessage != null) {
            int tag = eventMessage.getTag();
            if (tag == EventMessage.EventMessageAction.TAG_GO_INFO) {
                mRbHome.performClick();
                setIndexSelected(0);
            } else if (tag == EventMessage.EventMessageAction.TAG_GO_CRANEINFO) {
                mRbShop.performClick();
                setIndexSelected(1);
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

    /**
     *MyFoodTask  测试
     */
    private class MyFoodTask extends AsyncTask<String, Void, Map<String, Object>> {

        @Override
        protected void onPreExecute() {

            pd.setMessage("Loading...");

        }

        /**
         * 通过 web Http 获取服务器数据  到Map集合
         *
         * @param params
         * @return
         */
        @Override
        protected Map<String, Object> doInBackground(String... params) {

            String path = "http://192.168.0.188:8098/HDNews/getAllnewsJson";//本地服务器请求地址
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(path);
            try {//将List传到后台Web API（C# 服务器）
                //HttpPost httpPost = new HttpPost(path);

                //UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(null, "utf-8");
                //httpPost.setEntity(entity1);

                HttpResponse resp = httpClient.execute(httpGet);//请求耗时操作 网络
                //HttpResponse resp = client.execute(get);
                int rp = resp.getStatusLine().getStatusCode();
                if (resp.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = resp.getEntity();
                    String result = EntityUtils.toString(entity);
                    Log.i("111", "result=" + result);
                    Map<String, Object> map = parseJson(result);
                    return map;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        /**
         * onPostExecute
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Map<String, Object> result) {
            pd.dismiss();
            //isNextPage = (Boolean) result.get("isNextPage");
            data.addAll((List<Food>) result.get("foodList"));
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < data.size(); i++) {
                Food f = data.get(i);
                new HomeActivity.MyFoodTask.MyImgTask().execute(f);
            }
        }

        /**
         * 将服务器返回的数据进行Json解析
         * 新闻信息
         *
         * @param json
         * @return
         * @throws Exception
         */
        protected Map<String, Object> parseJson(String json) throws Exception {

            Map<String, Object> result = new HashMap<String, Object>();
            List<Food> lists = new ArrayList<Food>();
            Food f = null;
            //JSONObject smallObj = new JSONObject(json);
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                f = new Food();
                JSONObject smallObj = array.getJSONObject(i);

                f.setHid(smallObj.getInt("hid"));
                f.setTitle(smallObj.getString("title"));
                f.setContent(smallObj.getString("content"));
                f.setImgPath(smallObj.getString("img"));
                lists.add(f);
            }
            result.put("foodList", lists);
            //data.clear();//初始化List 否则List 逐渐增加
            //data.addAll((List<Food>) result.get("foodList"));

            return result;
        }

        private class MyImgTask extends AsyncTask<Food, Void, Food> {
            /**
             * @param params
             * @return
             */
            @Override
            protected Food doInBackground(Food... params) {
                Food f = params[0];
                String imgPath = "http://192.168.0.188:8098/HDNews/getAllnewsJson" + f.getImgPath();
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(imgPath);
                try {
                    HttpResponse resp = client.execute(get);
                    if (resp.getStatusLine().getStatusCode() == 200) {

                        HttpEntity entity = resp.getEntity();
                        byte img[] = EntityUtils.toByteArray(entity);
                        f.setImgData(img);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return f;
            }

            @Override
            protected void onPostExecute(Food result) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }


}