package com.betterda.shoppingsale.ziti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.betterda.shoppingsale.widget.NormalTopBar;
import com.betterda.shoppingsale.ziti.contract.ZiTiContract;
import com.betterda.shoppingsale.ziti.presenter.ZiTiPresenterImpl;
import com.betterda.shoppingsale.zxing.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自提列表
 * Created by Administrator on 2016/12/29.
 */

public class ZiTiActivity extends BaseActivity<ZiTiContract.Presenter> implements ZiTiContract.View {

    @BindView(R.id.layout_recycleview)
    XRecyclerView mRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLoadingpager;
    @BindView(R.id.relative_ziti_add)
    RelativeLayout mRelativeAdd;

    @Override
    protected ZiTiContract.Presenter onLoadPresenter() {
        return new ZiTiPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_ziti);

    }

    @Override
    public void init() {
        super.init();

        initRv();
    }


    @OnClick({R.id.iv_ziti_back, R.id.relative_ziti_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ziti_back:
                back();
                break;
            case R.id.relative_ziti_add:
                shoAdd();
                break;
        }
    }



    private void initRv() {
        mRecycleview.setVisibility(View.VISIBLE);
        mRecycleview.setPullRefreshEnabled(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRecycleview.setAdapter(getPresenter().getRvAdapter());

    }
    private void shoAdd() {
        View view1 = View.inflate(getmActivity(), R.layout.pp_ziti, null);
        TextView tvSao = (TextView) view1.findViewById(R.id.tv_pp_ziti_sao);
        TextView tvWrite = (TextView) view1.findViewById(R.id.tv_pp_ziti_write);
        tvSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmActivity(), CaptureActivity.class);
                UiUtils.startIntentForResult(getmActivity(),intent,0);
                closePopupWindow();
            }
        });
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.startIntent(getmActivity(),WriteZiTiActivity.class);
                closePopupWindow();
            }
        });

        setUpPopupWindow(view1,mRelativeAdd, UtilMethod.dip2px(getmActivity(),110),UtilMethod.dip2px(getmActivity(),96));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UiUtils.backgroundAlpha(1.0f,getmActivity());
    }
}
