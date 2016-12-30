package com.betterda.shoppingsale.application;

import android.app.Activity;
import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf
 */
public class MyApplication extends Application {

    private List<Activity> list;
    private static  MyApplication instance ;

    @Override
    public void onCreate() {
        super.onCreate();

        if (null == list) {
            list = new ArrayList<>();
        }
        instance = this;
        //友盟
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx5baf9605cac65d91", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3364100731","0fc3bda279afd92ad476d49d92cba33c");
        PlatformConfig.setQQZone("101375552", "7aafc9a0d3c62dd92af2922d7e3eabda");
        Config.REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
        //对应平台没有安装的时候跳转转到应用商店下载
        Config.isJumptoAppStore = true;
    }

    /**
     * 将activity添加到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null != list) {
            list.add(activity);
        }

    }

    /**
     * 退出程序
     */
    public void exitProgress() {

        if (null != list) {

            for (Activity activity : list) {
                activity.finish();
            }
            //将容器清空
            list.clear();


        }


    }

    /**
     * 当activity销毁时调用该方法,防止内存泄漏
     *
     * @param activity
     */
    public void removeAcitivty(Activity activity) {
        if (null != list && activity != null) {

            list.remove(activity);
        }
    }


    public static MyApplication getInstance() {
        return instance;
    }
}
