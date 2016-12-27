package com.betterda.shoppingsale.base;

import android.app.Activity;
import android.content.Context;

import com.betterda.shoppingsale.utils.RxManager;

/**
 * view的基类接口
 * Created by Administrator on 2016/12/2.
 */

public interface IView {
    Activity getmActivity();

    Context getContext();

    RxManager getRxManager();
}
