package com.betterda.shoppingsale.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.utils.PermissionUtil;
import com.betterda.shoppingsale.utils.RxManager;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.welcome.WelcomeActivity;

import java.util.List;

import butterknife.ButterKnife;

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
