package com.betterda.shoppingsale.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.Utils.StatusBarCompat;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.message.contract.SortContract;
import com.betterda.shoppingsale.message.presenter.SortPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息界面
 * Created by Administrator on 2016/12/8.
 */

public class SortFragment extends BaseFragment<SortContract.Presenter> implements SortContract.View {


    @BindView(R.id.topbar_message)
    NormalTopBar mTopbarMessage;
    @BindView(R.id.layout_recycleview)
    XRecyclerView mRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLoadingpager;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_message, null);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {//隐藏
        } else {
            getPresenter().getData();
            StatusBarCompat.setStatusBar5(getmActivity(),R.color.white);
        }
    }

    @Override
    protected SortContract.Presenter onLoadPresenter() {
        return new SortPresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();
        mTopbarMessage.setTitle("消息中心");
        mTopbarMessage.setBackVisibility(false);
        initRv();
        setLoadpagerClick();
    }

    private void setLoadpagerClick() {
        mLoadingpager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getData();
            }
        });
        mLoadingpager.setonEmptyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRv() {
        mRecycleview.setVisibility(View.VISIBLE);
        mRecycleview.setPullRefreshEnabled(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRecycleview.setAdapter(getPresenter().getRvAdapter());
    }

    @Override
    public LoadingPager getLodapger() {
        return mLoadingpager;
    }
}
