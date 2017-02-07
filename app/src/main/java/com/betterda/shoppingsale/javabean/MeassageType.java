package com.betterda.shoppingsale.javabean;

/**
 * 消息类型
 * Created by Administrator on 2017/2/7.
 */

public class MeassageType {
    private String msgType   ; //消息类型
    private String title   ; //标题
    private String msgTime   ;//时间
    private String littlePicture   ;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getLittlePicture() {
        return littlePicture;
    }

    public void setLittlePicture(String littlePicture) {
        this.littlePicture = littlePicture;
    }

    @Override
    public String toString() {
        return "MeassageType{" +
                "msgType='" + msgType + '\'' +
                ", title='" + title + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", littlePicture='" + littlePicture + '\'' +
                '}';
    }
}
