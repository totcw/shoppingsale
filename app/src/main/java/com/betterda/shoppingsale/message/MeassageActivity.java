package com.betterda.shoppingsale.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.message.contract.MeassageContract;
import com.betterda.shoppingsale.message.presenter.MeassagePresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 具体消息列表界面
 * Created by Administrator on 2017/1/3.
 */

public class MeassageActivity extends BaseActivity<MeassageContract.Presenter> implements MeassageContract.View {
    @BindView(R.id.topbar_meassage)
    NormalTopBar mTopbarMeassage;
    @BindView(R.id.layout_recycleview)
    XRecyclerView mRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLoadingpager;

    @Override
    protected MeassageContract.Presenter onLoadPresenter() {
        return new MeassagePresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_message);
    }

    @Override
    public void init() {
        super.init();
        mTopbarMeassage.setTitle("消息");
        mLoadingpager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onError();
            }
        });
    }

    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
         
        }
    }

    public void initRv(RecyclerView.Adapter adapter) {
        mRecycleview.setVisibility(View.VISIBLE);
        mRecycleview.setPullRefreshEnabled(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRecycleview.setLoadingMoreEnabled(true);
        mRecycleview.setAdapter(adapter);
        mRecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });
    }

    @Override
    public LoadingPager getLodapger() {
        return mLoadingpager;
    }
}
