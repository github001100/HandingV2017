package com.hdmes.handingv2017;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class JisuanActivity extends AppCompatActivity {
    private Spinner type;
    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisuan);
        //初始化控件
        type = (Spinner) findViewById(R.id.spinner6);
        //绑定数据源
        String[] typeItems = getResources().getStringArray(R.array.jisuantype);
//        text = (TextView) findViewById(R.id.textView6);
        //建立Adapter并绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeItems);
        adapter.setDropDownViewResource(R.layout.dropdown_stytle);
        //绑定Adapter到控件
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        LoadJiSuan();
    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150, 150, 150));
//            ((TextView) arg0.getChildAt(0)).setTextSize(14);
//            TextView v1 =(TextView)arg1;
//            v1.setTextColor(Color.BLACK);
//            text.setText("你使用什么样的手机："+adapter.getItem(position));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }

    public void LoadJiSuan(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
       //初始化WebView
        webView=(WebView) findViewById(R.id.jisiuan_web);
        //加载网页
        webView.loadUrl("http://192.168.0.188:8098/Reports/WholeDesign/WholeDesignFirst.aspx");
        //webView.setScaleX(1);
        //webView.setScaleY(1);
        webView.setInitialScale(200);
        //初始化WebSettings
        WebSettings settings = webView.getSettings();
        //支持Js
        settings.setJavaScriptEnabled(true);
        //缩放网页至屏幕大小
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;

        switch (screenDensity){
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case 480:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
        }
        settings.setDefaultZoom(zoomDensity);

        //不用系统自带浏览器，直接在WebView中显示
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return false;
            }

        });
        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                        if (View.INVISIBLE == progressBar.getVisibility()) {
                          progressBar.setVisibility(View.VISIBLE);
                         }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        //设置WebViewClient类
        webView.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //System.out.println("开始加载了");
                //beginLoading.setText("开始加载了");
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                //endLoading.setText("结束加载了");
            }
        });
    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
