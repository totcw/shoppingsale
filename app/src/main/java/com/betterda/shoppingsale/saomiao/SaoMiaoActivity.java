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
import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.Batch;
import com.betterda.shoppingsale.javabean.TitleBean;
import com.betterda.shoppingsale.javabean.ZiTi;
import com.betterda.shoppingsale.order.OrderDetailActivity;
import com.betterda.shoppingsale.stock.StockActivity;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.NetworkUtils;
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
    private List<TitleBean<Batch>> mZiTiList;
    private CommonAdapter<TitleBean<Batch>> mZiTiCommonAdapter;
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


        mZiTiCommonAdapter = new CommonAdapter<TitleBean<Batch>>(getmActivity(), R.layout.item_recycleview_ziti, mZiTiList) {
            @Override
            public void convert(ViewHolder holder, TitleBean<Batch> ziTi) {
                if (ziTi != null) {
                    final Batch data = ziTi.getData();
                    if (data != null) {

                        holder.setText(R.id.tv_tiem_hexiao_kahao, data.getBatchCode());
                        holder.setText(R.id.tv_item_hexiao_time, data.getReceiveTime());
                        holder.setOnClickListener(R.id.linear_ziti, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getmActivity(), StockActivity.class);
                                intent.putExtra("batchCode", data.getBatchCode());
                                UiUtils.startIntent(getmActivity(),intent);
                            }
                        });
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
     * 解析扫描的二维码
     * @param result
     */
    private void parseData(String result) {
        Intent intent = new Intent(getmActivity(), StockActivity.class);
        intent.putExtra("isStock", true);
        intent.putExtra("batchCode", result);
        UiUtils.startIntent(getmActivity(),intent);
    }


    private void getData() {
        NetworkUtils.isNetWork(getmActivity(), mLoadingpager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                getRxManager().add(NetWork.getNetService()
                .getBatchList(getAccount(),getToken(),pageNo+"", Constants.PAGESIZE)
                .compose(NetWork.handleResult(new BaseCallModel<List<Batch>>()))
                .subscribe(new MyObserver<List<Batch>>() {
                    @Override
                    protected void onSuccess(List<Batch> data, String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("入库:" + data);
                        }
                        if (data != null) {
                            if (mZiTiList != null && mZiTiCommonAdapter != null) {
                                if (pageNo == 1) {
                                    mZiTiList.clear();
                                    mRecycleview.setNoMore(false);
                                } else {
                                    UtilMethod.onLoadMore(data,mRecycleview);
                                }
                                for (Batch ziTi : data) {
                                    if (ziTi != null) {
                                        String time = ziTi.getReceiveTime();
                                        int indexOf = time.lastIndexOf("-");
                                        String tag = time.substring(0, indexOf);
                                        TitleBean<Batch> titleBean = new TitleBean<Batch>();
                                        titleBean.setData(ziTi);
                                        titleBean.setTag(tag);
                                        mZiTiList.add(titleBean);
                                    }
                                }
                                mZiTiCommonAdapter.notifyDataSetChanged();
                            }
                        }
                        UtilMethod.hideOrEmpty(mZiTiList,mLoadingpager);
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("入库fail:" + resultMsg);
                        }
                        UtilMethod.setLoadpagerError(mLoadingpager);
                    }

                    @Override
                    public void onExit() {
                        ExitToLogin();
                    }
                }));
            }
        });
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
        return new TitleItemDecoration<Batch>(getmActivity(), mZiTiList);
    }
}
