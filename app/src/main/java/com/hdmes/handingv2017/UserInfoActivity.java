package com.hdmes.handingv2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.hdmes.handingv2017.UserInfo.UserInfoAdapter;
import com.hdmes.handingv2017.UserInfo.UserInfoData;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private ListView lv;
    private UserInfoAdapter userinfoAdapter;
    private List<UserInfoData> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        lv=(ListView) findViewById(R.id.userinfo_lv);
        userinfoAdapter=new UserInfoAdapter(this,getData());
        lv.setAdapter(userinfoAdapter);
    }

    public List<UserInfoData> getData() {
        mList=new ArrayList<UserInfoData>();

        UserInfoData userinfoData=new UserInfoData();
        userinfoData.setType("登录名");
        userinfoData.setInfo("handing");

        UserInfoData userinfoData1=new UserInfoData();
        userinfoData1.setType("注册邮箱");
        userinfoData1.setInfo("handingtech@handingtech.cn");

        UserInfoData userinfoData2=new UserInfoData();
        userinfoData2.setType("联系电话");
        userinfoData2.setInfo("+86 15137961846");

        UserInfoData userinfoData3=new UserInfoData();
        userinfoData3.setType("公司名称");
        userinfoData3.setInfo("洛阳汉鼎起重机械有限公司");

        UserInfoData userinfoData4=new UserInfoData();
        userinfoData4.setType("注册时间");
        userinfoData4.setInfo("2017年11月30日");

        UserInfoData userinfoData5=new UserInfoData();
        userinfoData5.setType("所在地");
        userinfoData5.setInfo("中国洛阳老城区唐宫东路256号");

        UserInfoData userinfoData6=new UserInfoData();
        userinfoData6.setType("备注");
        userinfoData6.setInfo("专注物料搬运设备研究...");

        mList.add(userinfoData);
        mList.add(userinfoData1);
        mList.add(userinfoData2);
        mList.add(userinfoData3);
        mList.add(userinfoData4);
        mList.add(userinfoData5);
        mList.add(userinfoData6);
        return mList;
    }
}
