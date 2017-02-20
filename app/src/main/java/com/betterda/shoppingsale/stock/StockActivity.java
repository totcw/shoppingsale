package com.betterda.shoppingsale.stock;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.stock.contract.StockContract;
import com.betterda.shoppingsale.stock.presenter.StockPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
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
    @BindView(R.id.relative_orderdetail_ti)
    RelativeLayout mRelativeTi;//确认入库

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
        mLoadingpager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onError();
            }
        });
    }



    @OnClick({R.id.bar_back,R.id.relative_orderdetail_ti})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.relative_orderdetail_ti://确认入库
                getPresenter().comfirm();
                break;

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
    public RelativeLayout getRelativeTi() {
        return mRelativeTi;
    }

    @Override
    public NormalTopBar getTopView() {
        return mTopbarStock;
    }
}
