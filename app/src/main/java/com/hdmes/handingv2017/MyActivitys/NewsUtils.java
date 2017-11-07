package com.hdmes.handingv2017.MyActivitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hdmes.handingv2017.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/24.
 */

public class NewsUtils {

    private static List<Food> data = new ArrayList<Food>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // TODO
            // UI界面的更新等相关操作
        }
    };

    /**
     * @param context 上下文环境
     * @return 新闻集合
     */
    public static ArrayList<NewsBean> getAllNews(Context context) {
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsBean newsBean1 = new NewsBean();
            newsBean1.title = "汉鼎公司关于国庆放假的通知";
            newsBean1.des = "10月1-8日，汉鼎公司各部门放假，车间一线员工另行通知，加班，以下为值班人员安排";
            newsBean1.icon = context.getResources().getDrawable(R.drawable.wuhan);
            newsBean1.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101020.html#p=1";
            arrayList.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.title = "汉鼎起重机械公司中秋节领月饼";
            newsBean2.des = "公司按各个部门到3108室，领取，过期不候.过期不候.过期不候2107.8.15。";
            newsBean2.icon = context.getResources().getDrawable(R.drawable.eyu);
            newsBean2.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101024.html#p=1";
            arrayList.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.title = "汉鼎全球智能起重机控制系统v2.0公测";
            newsBean3.des = "近日，汉鼎公司（互联网+）开发一套智能起重管理平台，实现起重机信息采集与控制。";
            newsBean3.icon = context.getResources().getDrawable(R.drawable.qihuan);
            newsBean3.news_url = "http://192.168.0.188:8098/product/Strength";
            arrayList.add(newsBean3);
        }

        return arrayList;
    }

    /**
     * @param context 上下文环境
     * @return 备品备件集合
     */
    public static ArrayList<NewsBean> getAllPeiJian(Context context) {
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsBean newsBean1 = new NewsBean();
            newsBean1.title = "1200.00";
            newsBean1.des = "参数：";
            newsBean1.icon = context.getResources().getDrawable(R.mipmap.peijian_);
            arrayList.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.title = "1200.00";
            newsBean2.des = "参数：";
            newsBean2.icon = context.getResources().getDrawable(R.mipmap.peijian_);
            arrayList.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.title = "1200.00";
            newsBean3.des = "参数：";
            newsBean3.icon = context.getResources().getDrawable(R.mipmap.peijian_);
            arrayList.add(newsBean3);
        }

        return arrayList;
    }
    /**
     * 通过 web Http 获取服务器数据  到Map集合
     *
     * @param params
     * @return
     */
    protected static Map<String, Object> doInBackground(String... params) {

        String path = "http://192.168.0.188:8098/HDNews/getAllnewsJson";//本地服务器请求地址
        HttpClient httpClient = new DefaultHttpClient();//创建HTTP协议代理
        HttpGet httpGet = new HttpGet(path);
        try {//将List传到后台Web API（C# 服务器）
            HttpPost httpPost = new HttpPost(path);//POST提交方式
            //UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(null, "utf-8");//参数?
            //httpPost.setEntity(entity1);
            //HttpResponse resp = httpClient.execute(httpPost);//请求耗时操作 网络

            HttpResponse resp = httpClient.execute(httpGet);
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
     * 将服务器返回的数据进行Json解析
     * 登录信息
     *
     * @param json
     * @return
     * @throws Exception
     */
    protected static Map<String, Object> parseJson(String json) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Food> lists = new ArrayList<Food>();
        Food f = null;
        f = new Food();
        JSONObject smallObj = new JSONObject(json);
        //实体entity赋值
        //f.setId(smallObj.getInt("Stated"));
        f.setTitle(smallObj.getString("title"));
        f.setContent(smallObj.getString("content"));
        f.setImgPath(smallObj.getString("img"));
        lists.add(f);

        result.put("foodList", lists);
        data.clear();//初始化List 否则List 逐渐增加
        data.addAll((List<Food>) result.get("foodList"));
        Food st = data.get(0);
        String Stated = st.getContent();//Stated返回状态 success Or error


        return result;
    }

    /**
     * 网络操作相关的子线程
     */
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    });
}
