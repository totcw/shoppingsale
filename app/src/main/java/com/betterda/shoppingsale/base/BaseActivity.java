package com.betterda.shoppingsale.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.login.LoginActivity;
import com.betterda.shoppingsale.utils.CacheUtils;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.PermissionUtil;
import com.betterda.shoppingsale.utils.RxManager;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.welcome.WelcomeActivity;

import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 基类
 * Created by Administrator on 2016/12/2.
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE};
    protected P mPresenter;
    protected RxManager mRxManager;
    private PopupWindow popupWindow;
    private AlertDialog.Builder builder;
    private boolean isDismiss;//token 失效对话框 是否已经显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRxManager = new RxManager();

        mPresenter = onLoadPresenter();
        if(getPresenter() != null) {
            getPresenter().attachView(this);
        }

        initView();
        ButterKnife.bind(this);
        initListener();
        init();

        if(getPresenter() != null) {
            //开始presenter的逻辑
            getPresenter().start();
        }
        setLoadpagerBackgroud();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //统一检查权限
        PermissionUtil.checkPermission(getmActivity(), REQUEST_PERMISSIONS,  new PermissionUtil.permissionInterface() {
            @Override
            public void success() {

            }

            @Override
            public void fail(List<String> permissions) {

                //没有权限就回到欢迎页面
                UiUtils.startIntent(getmActivity(), WelcomeActivity.class);

            }
        });
    }


    /**
     * 处理业务逻辑
     */
    public void init() {

    }

    /**
     * 设置监听
     */
    public void initListener() {

    }

    /**
     * 初始化view
     */
    public void initView() {

    }


    public P getPresenter() {
        return mPresenter;
    }


    /**
     * 加载presenter
     * @return
     */
    protected abstract P onLoadPresenter();

    /**
     * 关闭activity的方法
     */
    public void back() {

            finish();
        overridePendingTransition(R.anim.activity_slide_finish_in,R.anim.activity_slide_finish_out);

    }


    public Activity getmActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    public void log(String msg) {
        Log.i("BaseActivity", msg);
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

    public void setLoadpagerBackgroud() {
        if (getLodapger() != null) {
            getLodapger().setEmptyBackground(R.mipmap.load_empty);
            getLodapger().setErrorBackground(R.mipmap.load_error);
            getLodapger().setLoadBackground(R.drawable.loadinganim);
        }
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
                            setAlias("");
                            //CacheUtils.putBoolean(getmActivity(), Constants.Cache.ISLOGIN, false);
                           CacheUtils.putString(getmActivity(), Constants.Cache.ACCOUNT, "");
                            UiUtils.startIntent(getmActivity(), LoginActivity.class);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }


    }


    public void setUpPopupWindow(View view){
        setUpPopupWindow(view,null,-1,-2);
    }


    /**
     * 初始化并显示PopupWindow
     *
     * @param view     要显示的界面
     */
    public void setUpPopupWindow(View view,View showview,int width,int height) {
        // 如果activity不在运行 就返回
        if (this.isFinishing()) {
            return;
        }

        popupWindow = new PopupWindow(view, width, height);
        // 设置点到外面可以取消,下面这2句要一起
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        popupWindow.setFocusable(true);

        UiUtils.backgroundAlpha(0.5f, getmActivity());
        //设置可以触摸
        popupWindow.setTouchable(true);

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                if (showview == null) {
                    //设置动画
                    popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                } else {
                    popupWindow.showAsDropDown(showview);
                }


            }
        }
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

                dismiss();
                popupWindow = null;
            }
        });




    }

    /**
     * popupwindow消失回调方法
     */
    public void dismiss() {

    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    /**
     * 关闭popupwindow
     */
    public void closePopupWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void setAlias(String alias) {

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";

                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    break;
            }

        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:

                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UiUtils.setOverdepengingOut(this);
    }

    @Override
    protected void onDestroy() {

        if (getPresenter() != null) {
            //解除presenter和view的关联
            getPresenter().detachView();
            //调用presenter的销毁方法
            getPresenter().destroy();
        }
        mRxManager.clear();
        closePopupWindow();

        super.onDestroy();
    }

}
