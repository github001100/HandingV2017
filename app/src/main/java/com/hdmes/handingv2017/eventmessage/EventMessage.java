package com.hdmes.handingv2017.eventmessage;

import android.os.Bundle;

/**
 * Created by 刘楠 on 2016/8/28 0028.13:34
 */
public class EventMessage {

    Bundle mBundle;
    int    tag;

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public static class EventMessageAction {

        public final static int TAG_GO_INFO    = 1;//跳转个人信息
        public final static int TAG_GO_CRANEINFO = 2;//设备信息
        //public final static int TAG_GO_MESSAGE  = 3;//我的
    }
}
