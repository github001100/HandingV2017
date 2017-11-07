package com.hdmes.handingv2017.MyActivitys;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.hdmes.handingv2017.R;

public class VideoActivity extends AppCompatActivity {
    private Spinner cranes;
    private String[] typeItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        cranes=(Spinner) findViewById(R.id.spinner9);
        //绑定数据源
        typeItems = getResources().getStringArray(R.array.data);
        //建立Adapter并绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeItems);
        adapter.setDropDownViewResource(R.layout.dropdown_stytle);
        //绑定Adapter到控件
        cranes.setAdapter(adapter);
        cranes.setOnItemSelectedListener(new VideoActivity.SpinnerXMLSelectedListener());

        showView();//调用视频显示
    }
    protected  void  showView(){
        //视频1
        Uri uri = Uri.parse("rtsp://admin:hd123456@192.168.0.180:554/h264/ch33/sub/av_stream");
        VideoView videoView = (VideoView)this.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
        //视频2
        Uri uri2 = Uri.parse("rtsp://admin:hd123456@192.168.0.180:554/h264/ch34/sub/av_stream");
        VideoView videoView2 = (VideoView)this.findViewById(R.id.videoView4);
        videoView2.setMediaController(new MediaController(this));
        videoView2.setVideoURI(uri2);
        videoView2.start();
        videoView2.requestFocus();
        //视频3
        Uri uri3 = Uri.parse("rtsp://admin:hd123456@192.168.0.180:554/h264/ch35/sub/av_stream");
        VideoView videoView3 = (VideoView)this.findViewById(R.id.videoView);
        videoView3.setMediaController(new MediaController(this));
        videoView3.setVideoURI(uri3);
        videoView3.start();
        videoView3.requestFocus();
        //视频4
        Uri uri4 = Uri.parse("rtsp://admin:hd123456@192.168.0.180:554/h264/ch36/sub/av_stream");
        VideoView videoView4 = (VideoView)this.findViewById(R.id.videoView5);
        videoView4.setMediaController(new MediaController(this));
        videoView4.setVideoURI(uri4);
        videoView4.start();
        videoView4.requestFocus();
        //视频5
        Uri uri5 = Uri.parse("rtsp://admin:hd123456@192.168.0.180:554/h264/ch37/sub/av_stream");
        VideoView videoView5 = (VideoView)this.findViewById(R.id.videoView3);
        videoView5.setMediaController(new MediaController(this));
        videoView5.setVideoURI(uri5);
        videoView5.start();
        videoView5.requestFocus();
        //视频6
        Uri uri6 = Uri.parse("rtsp://admin:hd123456@192.168.0.180:554/h265/ch38/sub/av_stream");
        VideoView videoView6 = (VideoView)this.findViewById(R.id.videoView2);
        videoView6.setMediaController(new MediaController(this));
        videoView6.setVideoURI(uri6);
        videoView6.start();
        videoView6.requestFocus();
    }

    public class SpinnerXMLSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(150, 150, 150));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
