package com.betterda.shoppingsale.wallet.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;


import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.MingXi;
import com.betterda.shoppingsale.javabean.TitleBean;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.betterda.shoppingsale.wallet.contract.MingXiContract;
import com.betterda.shoppingsale.wallet.model.MingXiModelImpl;
import com.betterda.shoppingsale.widget.TitleItemDecoration;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20
 */

public class MingXiPresenterImpl extends BasePresenter<MingXiContract.View, MingXiContract.Model> implements MingXiContract.Presenter {
    private List<TitleBean<MingXi>> mingXiList;
    private CommonAdapter<TitleBean<MingXi>> mingXiCommonAdapter;
    public String wateType;
    private int pageNo=1;

    @Override
    public void start() {
        attachModel(new MingXiModelImpl());
        initRv();
        getintent();
        getView().getLodapger().setLoadVisable();
        getData();
    }

    private void getintent() {
        Intent intent = getView().getmActivity().getIntent();
        if (intent != null) {
            wateType = intent.getStringExtra("type");
        }
    }


    private void initRv() {
        mingXiList = new ArrayList<>();
        mingXiCommonAdapter = new CommonAdapter<TitleBean<MingXi>>(getView().getmActivity(), R.layout.item_recycleview_mingxi, mingXiList) {
            @Override
            public void convert(ViewHolder holder, TitleBean<MingXi> mingXi) {
                if (mingXi != null) {
                    MingXi mingXi1 = mingXi.getData();
                    if (mingXi1 != null) {
                        holder.setText(R.id.tv_mingxi_type, mingXi1.getSource());
                        holder.setText(R.id.tv_mingxi_time, mingXi1.getTime());
                        holder.setText(R.id.tv_mingxi_money, mingXi1.getSum());
                        holder.setText(R.id.tv_mingxi_money2, mingXi1.getMoney());
                    }
                }
            }
        };
        getView().initRv(mingXiCommonAdapter);
    }

    private void getData() {
        if (wateType == null) {
            NetworkUtils.isNetWork(getView().getmActivity(), getView().getLodapger(), new NetworkUtils.SetDataInterface() {

                @Override
                public void getDataApi() {
                    getView().getRxManager().add(NetWork.getNetService()
                            .getTuijianMingxi(getView().getAccount(), getView().getToken(),pageNo+"", Constants.PAGESIZE)
                            .compose(NetWork.handleResult(new BaseCallModel<List<MingXi>>()))
                            .subscribe(new MyObserver<List<MingXi>>() {
                                @Override
                                protected void onSuccess(List<MingXi> data, String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("推荐返现明细:" + data.toString() + resultMsg);
                                    }
                                    if (data != null) {

                                        if (mingXiList != null && mingXiCommonAdapter != null) {
                                            if (pageNo == 1) {
                                                mingXiList.clear();
                                                getView().getRv().setNoMore(false);
                                            } else {
                                                UtilMethod.onLoadMore(data, getView().getRv());
                                            }
                                            for (MingXi mingXi : data) {
                                                if (mingXi != null) {
                                                    String time = mingXi.getTime();
                                                    int indexOf = time.lastIndexOf("-");
                                                    String tag = time.substring(0, indexOf);
                                                    TitleBean<MingXi> titleBean = new TitleBean<MingXi>();
                                                    titleBean.setData(mingXi);
                                                    titleBean.setTag(tag);
                                                    mingXiList.add(titleBean);
                                                }
                                            }

                                            mingXiCommonAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    UtilMethod.hideOrEmpty(mingXiList,getView().getLodapger());

                                }

                                @Override
                                public void onFail(String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("推荐明细fail:" + resultMsg);
                                    }
                                    UtilMethod.setLoadpagerError(getView().getLodapger());
                                }

                                @Override
                                public void onExit() {
                                    getView().ExitToLogin();
                                }
                            }));
                }
            });
        } else {
            NetworkUtils.isNetWork(getView().getmActivity(), getView().getLodapger(), new NetworkUtils.SetDataInterface() {

                @Override
                public void getDataApi() {
                    getView().getRxManager().add(NetWork.getNetService()
                            .getWalletMingXi(getView().getAccount(), getView().getToken(), wateType,pageNo+"", Constants.PAGESIZE)
                            .compose(NetWork.handleResult(new BaseCallModel<List<MingXi>>()))
                            .subscribe(new MyObserver<List<MingXi>>() {
                                @Override
                                protected void onSuccess(List<MingXi> data, String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("交易明细:" + data.toString() + resultMsg);
                                    }
                                    if (data != null) {
                                        if (mingXiList != null && mingXiCommonAdapter != null) {
                                            if (pageNo == 1) {
                                                mingXiList.clear();
                                                getView().getRv().setNoMore(false);
                                            } else {
                                                UtilMethod.onLoadMore(data,getView().getRv());
                                            }
                                            for (MingXi mingXi : data) {
                                                if (mingXi != null) {
                                                    String time = mingXi.getTime();
                                                    int indexOf = time.lastIndexOf("-");
                                                    String tag = time.substring(0, indexOf);
                                                    TitleBean<MingXi> titleBean = new TitleBean<MingXi>();
                                                    titleBean.setData(mingXi);
                                                    titleBean.setTag(tag);
                                                    mingXiList.add(titleBean);
                                                }
                                            }

                                            mingXiCommonAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    UtilMethod.hideOrEmpty(mingXiList,getView().getLodapger());
                                }

                                @Override
                                public void onFail(String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("交易明细fail:" + resultMsg);
                                    }
                                    UtilMethod.setLoadpagerError(getView().getLodapger());
                                }

                                @Override
                                public void onExit() {
                                    getView().ExitToLogin();
                                }
                            }));
                }
            });
        }

    }


    @Override
    public void destroy() {

    }


    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new TitleItemDecoration<MingXi>(getView().getmActivity(), mingXiList);
    }



    @Override
    public void onLoadMore() {
        pageNo++;
        getData();
    }

    @Override
    public void onError() {
        getView().getLodapger().setLoadVisable();
        pageNo = 1;
        getData();
    }


}