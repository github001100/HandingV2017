package com.hdmes.handingv2017.MyActivitys;

/**
 * 汉鼎 新闻HDNews Bean/登录
 * Created by Administrator on 2017/9/11 0011.
 */

import java.io.Serializable;

public class Food implements Serializable {

    private int hid;     //编号

    private String title;//标题

    private String content;//内容/登录状态也用到啦

    private String imgPath;
    private String contentPath;// 内容地址

    private byte imgData[];

    /*  构造方法
        public Food(int id, String name, String desc, String imgPath) {
            super();
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.imgPath = imgPath;
        }*/
    public int getHid() {
        return hid;
    }

    public void setHid(int id) {
        this.hid = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }
}
