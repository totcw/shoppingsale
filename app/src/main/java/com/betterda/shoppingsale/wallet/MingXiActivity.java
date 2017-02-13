package com.betterda.shoppingsale.wallet;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.wallet.contract.MingXiContract;
import com.betterda.shoppingsale.wallet.presenter.MingXiPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 明细
 * Created by Administrator on 2016/12/20.
 */

public class MingXiActivity extends BaseActivity<MingXiContract.Presenter> implements MingXiContract.View {
    @BindView(R.id.topbar_mingxi)
    NormalTopBar mTopbarMingxi;
    @BindView(R.id.layout_recycleview)
    XRecyclerView mLayoutRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLayoutLoadingpager;

    @Override
    protected MingXiContract.Presenter onLoadPresenter() {
        return new MingXiPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_mingxi);
    }

    @Override
    public void init() {
        super.init();
        mTopbarMingxi.setTitle("明细");
        mLayoutLoadingpager.setonErrorClickListener(new View.OnClickListener() {
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
        mLayoutRecycleview.setVisibility(View.VISIBLE);
        mLayoutRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mLayoutRecycleview.setLoadingMoreEnabled(true);
        mLayoutRecycleview.setPullRefreshEnabled(false);
        mLayoutRecycleview.addItemDecoration(getPresenter().getItemDecoration());
        mLayoutRecycleview.setAdapter(adapter);
        mLayoutRecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        return mLayoutLoadingpager;
    }
}
