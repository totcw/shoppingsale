package com.betterda.shoppingsale.stock;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.stock.contract.StockContract;
import com.betterda.shoppingsale.stock.presenter.StockPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品库存
 * Created by Administrator on 2016/12/28.
 */

public class StockActivity extends BaseActivity<StockContract.Presenter> implements StockContract.View {
    @BindView(R.id.topbar_stock)
    NormalTopBar mTopbarStock;
    @BindView(R.id.layout_recycleview)
    XRecyclerView mRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLoadingpager;

    @Override
    protected StockContract.Presenter onLoadPresenter() {
        return new StockPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_stock);
    }

    @Override
    public void init() {
        super.init();
        mTopbarStock.setTitle("商品库存");

        initRv();
    }



    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;

        }
    }

    private void initRv() {
        mRecycleview.setVisibility(View.VISIBLE);
        mRecycleview.setPullRefreshEnabled(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRecycleview.setAdapter(getPresenter().getRvAdapter());

    }
}
