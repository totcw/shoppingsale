package com.betterda.shoppingsale.saomiao;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.javabean.TitleBean;
import com.betterda.shoppingsale.javabean.ZiTi;
import com.betterda.shoppingsale.order.OrderDetailActivity;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.betterda.shoppingsale.widget.TitleItemDecoration;
import com.betterda.shoppingsale.ziti.WriteZiTiActivity;
import com.betterda.shoppingsale.ziti.contract.ZiTiContract;
import com.betterda.shoppingsale.ziti.presenter.ZiTiPresenterImpl;
import com.betterda.shoppingsale.zxing.CaptureActivity;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入口列表
 * Created by Administrator on 2016/12/29.
 */

public class SaoMiaoActivity extends BaseActivity<ZiTiContract.Presenter> implements ZiTiContract.View {

    @BindView(R.id.layout_recycleview)
    XRecyclerView mRecycleview;
    @BindView(R.id.layout_loadingpager)
    LoadingPager mLoadingpager;
    @BindView(R.id.relative_ziti_add)
    RelativeLayout mRelativeAdd;
    private boolean isBack;//是否是从扫描页面返回
    private List<TitleBean<ZiTi>> mZiTiList;
    private CommonAdapter<TitleBean<ZiTi>> mZiTiCommonAdapter;
    private int pageNo=1;

    @Override
    protected ZiTiContract.Presenter onLoadPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_saomiao);

    }

    @Override
    public void init() {
        super.init();
        initRv();
        mLoadingpager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingpager.setLoadVisable();
                pageNo = 1;
                getData();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (!isBack) {
            mLoadingpager.setLoadVisable();
            pageNo = 1;
            getData();
        }
    }

    @OnClick({R.id.iv_ziti_back, R.id.relative_ziti_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ziti_back:
                back();
                break;
            case R.id.relative_ziti_add:
                Intent intent = new Intent(getmActivity(), CaptureActivity.class);
                UiUtils.startIntentForResult(getmActivity(), intent, 0);
                break;
        }
    }


    private void initRv() {
        mZiTiList = new ArrayList<>();
        mRecycleview.setVisibility(View.VISIBLE);
        mRecycleview.setPullRefreshEnabled(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getmActivity()));

        mRecycleview.addItemDecoration(getItemDecoration());
        mRecycleview.setLoadingMoreEnabled(true);
        mRecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                pageNo++;
                getData();
            }
        });


        mZiTiCommonAdapter = new CommonAdapter<TitleBean<ZiTi>>(getmActivity(), R.layout.item_recycleview_ziti, mZiTiList) {
            @Override
            public void convert(ViewHolder holder, TitleBean<ZiTi> ziTi) {
                if (ziTi != null) {
                    ZiTi data = ziTi.getData();
                    if (data != null) {

                        holder.setText(R.id.tv_tiem_hexiao_kahao, data.getBarCode());
                        holder.setText(R.id.tv_item_hexiao_time, data.getBarTime());
                    }
                }
            }
        };
        mRecycleview.setAdapter(mZiTiCommonAdapter);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == 0) {
                isBack = true;
                if (data != null) {
                    String result = data.getStringExtra("result");

                    parseData(result);


                }
            }
        }
    }

    /**
     * 解析二维码获取到自提码和订单号
     *
     * @param result
     */
    private void parseData(String result) {
        if (!TextUtils.isEmpty(result)) {
            String[] split = result.split(",");
            if (split != null && split.length > 1) {
                Intent intent = new Intent(getmActivity(), OrderDetailActivity.class);
                intent.putExtra("orderId", split[1]);
                intent.putExtra("barcode", split[0]);
                UiUtils.startIntent(getmActivity(), intent);
            }
        } else {
            UiUtils.showToast(getmActivity(), "扫描失败");
        }
    }


    private void getData() {

    }


    @Override
    public LoadingPager getLodapger() {
        return mLoadingpager;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isBack = false;
    }

    @Override
    public XRecyclerView getRv() {
        return null;
    }


    public RecyclerView.ItemDecoration getItemDecoration() {
        return new TitleItemDecoration<ZiTi>(getmActivity(), mZiTiList);
    }
}
