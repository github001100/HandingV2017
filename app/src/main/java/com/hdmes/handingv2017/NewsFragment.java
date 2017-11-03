package com.hdmes.handingv2017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hdmes.handingv2017.MyActivitys.Food;
import com.hdmes.handingv2017.MyActivitys.MyAdapter;
import com.hdmes.handingv2017.MyActivitys.NewsBean;
import com.hdmes.handingv2017.MyActivitys.NewsUtils;
import com.hdmes.handingv2017.MyActivitys.RefreshListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

//    public void refreshComplete(){
//        state = NONE;   //状态设为正常状态
//        isFlag = false; //标志设为false
//        refreshViewByState();
//        //设置提示更新时间间隔
//        Time t = new Time();
//        t.setToNow();
//        time = t.hour*60+t.minute-updateTime;
//        updateTime = t.hour*60+t.minute;
//        TextView lastUpdateTime = (TextView)findViewById(R.id.time);
//        lastUpdateTime.setText(time+"分钟前更新");
//    }

    //汉鼎新闻1
    private RefreshListView lv;
    private ArrayList<NewsBean> mList;
    //新闻资讯2
    private List<Food> data = new ArrayList<Food>();
    private MyAdapter mAdapter;
    private ProgressDialog pd;
    private String HttpUrl = "http://192.168.0.188:8098";//服务器IP地址


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    /**
     * 重新方法 初始化适配器 添加数据
     */
    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new MyAdapter(getContext(), data);//数据适配

        lv.setAdapter(mAdapter);//ListView 绑定适配器
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_news, container, false);
        //汉鼎 修改后
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        lv = (RefreshListView) view.findViewById(R.id.lv);//这个是ListView
        //汉鼎 调用初始化
        initUI();
        //initData();
        //initAdapter();
        pd = new ProgressDialog(getContext());
        //mAdapter = new MyAdapter(getContext(), data);
        //lv.setCacheColorHint(Color.TRANSPARENT);
        //lv.setOverScrollMode(android.view.View.OVER_SCROLL_NEVER);
        //lv.setAdapter(mAdapter);
        //实现刷新接口
        lv.setInterface(new RefreshListView.IRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
//                new Thread(new Runnable() {
//                    public void run() {
//                        lv.setAdapter(mAdapter);
//                        mAdapter.notifyDataSetChanged();
//
//                        //lv.refreshComplete();
//                    }
//
//                }).start();

                //handler设置刷新延时效果
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //获取最新数据
                        //getRefreshData();
                        //通知界面显示
                        //MyAdapter.notifyDataSetChanged();
                        //通知listView刷新数据完毕
                        new MyNewsTask().execute();//刷新功能实现
                        lv.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        lv.refreshComplete();
                    }
                }, 2000);
            }
        });

        new MyNewsTask().execute();
        return view;

    }

    private void initAdapter() {
        lv.setAdapter(new NewsAdapter());

    }

    private void initData() {
        mList = NewsUtils.getAllNews(getContext());

    }

    /**
     * 初始化UI 点击时间
     */
    private void initUI() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (position == 0) {
                    return;
                }
                intent.setData(Uri.parse(data.get(position - 1).getContentPath()));
                startActivity(intent);
            }
        });

    }

    /**
     * 创建一个异步任务类 MyNewsTask 继承AsyncTask(Thread Runable())
     * Why 在主线程中不能直接访问网络，要开辟子线程。
     */
    private class MyNewsTask extends AsyncTask<String, Void, Map<String, Object>> {

        @Override
        protected void onPreExecute() {

            pd.setMessage("Loading...");

        }

        /**
         * 通过 web Http 获取服务器News数据  到Map集合
         *
         * @param params
         * @return
         */
        @Override
        protected Map<String, Object> doInBackground(String... params) {

            String path = HttpUrl + "/HDNews/getAllnewsJson";//本地服务器请求地址
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(path);
            try {//将List传到后台Web API（C# 服务器）
                HttpPost httpPost = new HttpPost(path);

                //UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(null, "utf-8");
                //httpPost.setEntity(entity1);

                HttpResponse resp = httpClient.execute(httpGet);//请求耗时操作 网络
                //HttpResponse resp = client.execute(get);
                int rp = resp.getStatusLine().getStatusCode();
                if (resp.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = resp.getEntity();
                    String result = EntityUtils.toString(entity);
                    Log.i("newslist", "result=" + result);
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
            data.clear();
            data.addAll((List<Food>) result.get("newsList"));
            for (int i = 0; i < data.size(); i++) {
                Food f = data.get(i);
                new MyNewsTask.MyImgTask().execute(f);
            }
            mAdapter.notifyDataSetChanged();

        }

        /**
         * 将服务器返回的数据进行Json解析
         * 新闻信息
         * 赋值 NewsBean(food)
         *
         * @param json
         * @return
         * @throws Exception
         */
        protected Map<String, Object> parseJson(String json) throws Exception {

            Map<String, Object> result = new HashMap<String, Object>();
            List<Food> lists = new ArrayList<Food>();
            Food f = null;
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                f = new Food();
                JSONObject smallObj = array.getJSONObject(i);
                //解析Json数组后赋值给News Bean
                f.setHid(smallObj.getInt("hid"));
                f.setTitle(smallObj.getString("title"));
                f.setContent(smallObj.getString("content"));
                f.setImgPath(smallObj.getString("img"));
                f.setContentPath(smallObj.getString("content_path"));
                lists.add(f);
            }

            result.put("newsList", lists);
            return result;
        }

        /**
         * 获取新闻图片信息
         */
        private class MyImgTask extends AsyncTask<Food, Void, Food> {
            /**
             * @param params
             * @return
             */
            @Override
            protected Food doInBackground(Food... params) {
                Food f = params[0];
                String imgPath = HttpUrl + "" + f.getImgPath();
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

    /**
     * 之前静态读取适配器 (已更换从服务器获取)
     */
    private class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public NewsBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.news_model, null);//news_model 新闻模板适配ListView
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsBean item = getItem(position);

            holder.tv_title.setText(item.title);
            holder.tv_des.setText(item.des);
            holder.iv_icon.setImageDrawable(item.icon);
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_title;
        TextView tv_des;
        ImageView iv_icon;

    }
}