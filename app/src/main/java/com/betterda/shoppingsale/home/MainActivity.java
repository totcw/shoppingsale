package com.betterda.shoppingsale.home;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.betterda.mylibrary.Utils.StatusBarCompat;
import com.betterda.mylibrary.view.IndicatorView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.home.contract.HomeContract;
import com.betterda.shoppingsale.home.presenter.HomePresenterImpl;
import com.betterda.shoppingsale.welcome.WelcomeActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */

public class MainActivity extends BaseActivity<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.idv_activity_main_shouye)
    IndicatorView mIdvShouye;
    @BindView(R.id.idv_activity_main_sort)
    IndicatorView mIdvSort;
    @BindView(R.id.idv_activity_main_my)
    IndicatorView mIdvMy;
    @BindView(R.id.linear_activity_main)
    LinearLayout mLinearActivityMain;
    @BindView(R.id.frame_activity_main)
    FrameLayout mFrameActivityMain;

    @Override
    public void initView() {
        super.initView();
        StatusBarCompat.setStatusBar5(getmActivity(), R.color.backgroudyellow);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected HomeContract.Presenter onLoadPresenter() {
        return new HomePresenterImpl();
    }

    @Override
    public void init() {
        super.init();
        initIdv();
        getPresenter().start();

    }


    @OnClick({R.id.idv_activity_main_shouye, R.id.idv_activity_main_sort,  R.id.idv_activity_main_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idv_activity_main_shouye:
                switchTo(mIdvShouye);
                getPresenter().switchToFragment(R.id.idv_activity_main_shouye);
                break;
            case R.id.idv_activity_main_sort:
                switchTo(mIdvSort);
                getPresenter().switchToFragment(R.id.idv_activity_main_sort);
                break;

            case R.id.idv_activity_main_my:
                switchTo(mIdvMy);
                getPresenter().switchToFragment(R.id.idv_activity_main_my);
                break;
        }
    }

    /**
     * 初始化底部导航
     */
    private void initIdv() {
        mIdvShouye.setIvBackground(R.mipmap.activity_main_shouye_normal,R.mipmap.activity_main_shouye_pressed);
        mIdvSort.setIvBackground(R.mipmap.activity_main_sort_normal,R.mipmap.activity_main_sort_pressed);
        mIdvMy.setIvBackground(R.mipmap.activity_main_my_nomal,R.mipmap.activity_main_my_pressed);

        mIdvShouye.setLineBackground(getResources().getColor(R.color.activityMainNormal),getResources().getColor(R.color.activityMainPressed));
        mIdvSort.setLineBackground(getResources().getColor(R.color.activityMainNormal),getResources().getColor(R.color.activityMainPressed));
        mIdvMy.setLineBackground(getResources().getColor(R.color.activityMainNormal),getResources().getColor(R.color.activityMainPressed));

        mIdvShouye.setTitle("首页");
        mIdvSort.setTitle("消息");
        mIdvMy.setTitle("个人");

        mIdvShouye.setTabSelected(true);
    }

    /**
     * 设置底部导航的选中状态
     * @param idv
     */
    public void switchTo(IndicatorView idv) {

        if (null != mIdvShouye && null != mIdvSort  && null != idv && mIdvMy != null) {

            mIdvShouye.setTabSelected(false);
            mIdvSort.setTabSelected(false);
            mIdvMy.setTabSelected(false);
            idv.setTabSelected(true);

        }

    }

    @Override
    public void onBackPressed() {
        //发送广播 关闭欢迎页面
        mRxManager.post(WelcomeActivity.class.getSimpleName(),"finish");
        super.onBackPressed();

    }
}
