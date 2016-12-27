package com.betterda.shoppingsale.shouye;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.shouye.adapter.LunBoTuAdapter;
import com.betterda.shoppingsale.shouye.contract.ShouYeContract;
import com.betterda.shoppingsale.shouye.presenter.ShouYePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ShouYeFragment extends BaseFragment<ShouYeContract.Presenter> implements ShouYeContract.View {

    @BindView(R.id.frame_shouye_lunbotu)
    FrameLayout mFrameShouyeLunbotu; //轮播图
    @BindView(R.id.frame_shouye_second)
    FrameLayout mFrameShouyeSecond;//第二区域
    @BindView(R.id.loadpager_shouye)
    LoadingPager mLoadpagerShouye;

    private View mViewLunbotu;//轮播图的view
    private ViewPager mVpLunbotu;
    private LinearLayout mLinearFirst;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_shouye, null);
    }

    @Override
    protected ShouYeContract.Presenter onLoadPresenter() {
        return new ShouYePresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();
        //加载轮播图
        initLunbotu();
        //加载第二区域
        initSecond();
    }



    @OnClick({R.id.loadpager_shouye, R.id.frame_shouye})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loadpager_shouye:
                break;
            case R.id.frame_shouye:
                break;
        }
    }

    /**
     * 初始化轮播图
     */
    private void initLunbotu() {
        mViewLunbotu = View.inflate(getmActivity(), R.layout.view_lunbotu, null);
        ImageView mIvFirst = (ImageView) mViewLunbotu.findViewById(R.id.iv_main_first);
        mVpLunbotu = (ViewPager) mViewLunbotu.findViewById(R.id.vpager_lunbotu);
        mLinearFirst = (LinearLayout) mViewLunbotu.findViewById(R.id.ll_points);
        mFrameShouyeLunbotu.addView(mViewLunbotu);

    }

    /**
     * 加载第二区域
     */
    private void initSecond() {

    }



    /**
     * 初始化轮播图的viewpager
     *
     * @param lunBoTuAdapter
     */
    @Override
    public void initVpLunbotu(LunBoTuAdapter lunBoTuAdapter) {
        mVpLunbotu.setAdapter(lunBoTuAdapter);
    }

    @Override
    public ViewGroup getLpoint() {
        return mLinearFirst;
    }

    @Override
    public ViewPager getViewPager() {
        return mVpLunbotu;
    }


}
