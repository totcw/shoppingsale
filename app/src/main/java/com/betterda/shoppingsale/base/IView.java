package com.betterda.shoppingsale.base;

import android.app.Activity;
import android.content.Context;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.utils.RxManager;

/**
 * view的基类接口
 * Created by Administrator on 2016/12/2.
 */

public interface IView {
    Activity getmActivity();

    Context getContext();

    RxManager getRxManager();
    LoadingPager getLodapger();
    /**
     * 获取帐号
     * @return
     */
    String getAccount();

    /**
     * 获取token
     * @return
     */
    String getToken();
    //token失效 跳转到登录界面
    void ExitToLogin();

}
