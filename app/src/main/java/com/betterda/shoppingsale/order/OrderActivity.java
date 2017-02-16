package com.betterda.shoppingsale.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.order.contract.OrderContract;
import com.betterda.shoppingsale.order.presenter.OrderPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单
 * Created by Administrator on 2016/12/28.
 */

public class OrderActivity extends BaseActivity<OrderContract.Presenter> implements OrderContract.View {
    @BindView(R.id.topbar_order)
    NormalTopBar mTopbarOrder;
    @BindView(R.id.layout_recycleview)
    XRecyclerView mRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLoadingpager;

    @Override
    protected OrderContract.Presenter onLoadPresenter() {
        return new OrderPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_order);
    }

    @Override
    public void init() {
        super.init();
        setTopBar();
        initRv();
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




    private void setTopBar() {
        Intent intent = getIntent();
        if (intent != null) {
            String type = intent.getStringExtra("type");
            mTopbarOrder.setTitle(type);
        }
    }

    private void initRv() {
        mRecycleview.setVisibility(View.VISIBLE);
        mRecycleview.setPullRefreshEnabled(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRecycleview.setAdapter(getPresenter().getRvAdapter());
        mRecycleview.setLoadingMoreEnabled(true);
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

    @Override
    public XRecyclerView getRv() {
        return mRecycleview;
    }
}
