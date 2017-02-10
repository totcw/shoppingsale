package com.betterda.shoppingsale.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.login.LoginActivity;
import com.betterda.shoppingsale.utils.CacheUtils;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.RxManager;
import com.betterda.shoppingsale.utils.UiUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/5.
 */

public abstract class BaseFragment <P extends IPresenter> extends Fragment implements IView {
    private Activity mActivity;
    protected P mPresenter;
    protected RxManager mRxManager;
    private AlertDialog.Builder builder;
    private boolean isDismiss;//token 失效对话框 是否已经显示
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity; //在这里获取到acitiviy,防止内存不够,activity被销毁,调用getactivity方法时返回null
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater);
        ButterKnife.bind(this, view);
        mRxManager = new RxManager();
        mPresenter = onLoadPresenter();
        if(getPresenter() != null) {
            getPresenter().attachView(this);
        }
        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initData();
        initListenr();
        if(getPresenter() != null) {
            //开始presenter的逻辑
            getPresenter().start();
        }
    }


    protected abstract P onLoadPresenter();

    /**
     * 设置监听
     */
    public void initListenr() {

    }


    /**
     * 子类必须实现此方法, 返回一个View对象, 作为当前Fragment的布局来展示.
     *
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 如果子类需要初始化自己的数据, 把此方法给覆盖.
     */
    public void initData() {

    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public Activity getmActivity() {
        return mActivity;
    }


    public void log(String msg) {
        Log.i("BaseFragment", msg);
    }

    @Override
    public RxManager getRxManager() {
        return mRxManager;
    }

    public LoadingPager getLodapger(){
        return null;
    };
    /**
     * 获取帐号
     * @return
     */
    public String getAccount() {
        return CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
    }

    /**
     * 获取token
     * @return
     */
    public String getToken() {
        return  CacheUtils.getString(getmActivity(), getAccount()+Constants.Cache.TOKEN, "");
    }


    /**
     * 强制跳转到登录界面
     */
    public void ExitToLogin() {
        if (!isDismiss) {
            isDismiss = true;
            if (builder == null) {
                builder = new AlertDialog.Builder(getmActivity());
            }
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isDismiss = false;
                    builder = null;
                }
            });

            builder.setTitle("温馨提示")
                    .setMessage("您的帐号已在别处登录,请重新登录")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //CacheUtils.putBoolean(getmActivity(), Constants.Cache.ISLOGIN, false);
                            // CacheUtils.putString(getmActivity(), Constants.Cache.ACCOUNT, "");
                            UiUtils.startIntent(getmActivity(), LoginActivity.class);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }


    }

    @Override
    public void onDestroyView() {
        if (getPresenter() != null) {
            //解除presenter和view的关联
            getPresenter().detachView();
            //调用presenter的销毁方法
            getPresenter().destroy();
        }
        mRxManager.clear();
        super.onDestroyView();
    }
}
