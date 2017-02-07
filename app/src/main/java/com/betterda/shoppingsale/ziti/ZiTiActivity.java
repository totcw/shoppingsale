package com.betterda.shoppingsale.ziti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.order.OrderDetailActivity;
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
        mRecycleview.setPullRefreshEnabled(true);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRecycleview.setAdapter(getPresenter().getRvAdapter());
        mRecycleview.addItemDecoration(getPresenter().getItemDecoration());

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == 0) {

                if (data != null) {
                    String result = data.getStringExtra("result");
                    if (!TextUtils.isEmpty(result)) {
                        getData(result);
                    } else {
                        UiUtils.showToast(getmActivity(), "扫描失败");
                    }

                }
            }
        }
    }

    /**
     * 解析二维码获取到自提码和订单号
     * @param result
     */
    private void getData( String result) {
        if (TextUtils.isEmpty(result)) {
            String[] split = result.split(",");
            if (split != null && split.length > 1) {
                Intent intent = new Intent(getmActivity(), OrderDetailActivity.class);
                intent.putExtra("orderId", split[1]);
                intent.putExtra("barcode", split[0]);
                UiUtils.startIntent(getmActivity(), intent);
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UiUtils.backgroundAlpha(1.0f,getmActivity());
    }
}
