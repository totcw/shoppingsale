package com.betterda.shoppingsale.tuijian.presenter;

import android.content.Intent;

import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.TuiJian;
import com.betterda.shoppingsale.tuijian.TuiJianFanxianActivity;
import com.betterda.shoppingsale.tuijian.contract.MyTuijianContract;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;


/**
 * Created by Administrator on 2016/12/20
 */

public class MyTuijianPresenterImpl extends BasePresenter<MyTuijianContract.View, MyTuijianContract.Model> implements MyTuijianContract.Presenter {
    private String money;

    @Override
    public void start() {
        getData();
    }

    private void getData() {
        NetworkUtils.isNetWork(getView().getmActivity(), getView().getLodapger(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                getView().getRxManager().add(NetWork.getNetService()
                        .getTuijianmenber(getView().getAccount(), getView().getToken())
                        .compose(NetWork.handleResult(new BaseCallModel<TuiJian>()))
                        .subscribe(new MyObserver<TuiJian>() {
                            @Override
                            protected void onSuccess(TuiJian data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("我的推荐success:" + data.toString());
                                }
                                if (data != null) {
                                    getView().setValue(data);
                                    money = data.getHeapCashback();
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("我的推荐fail:" + resultMsg);
                                }
                            }

                            @Override
                            public void onExit() {

                            }
                        }));
            }
        });
    }

    @Override
    public void destroy() {

    }

    @Override
    public void fanxian() {
        Intent intent = new Intent(getView().getmActivity(), TuiJianFanxianActivity.class);
        intent.putExtra("money",money);
        UiUtils.startIntent(getView().getmActivity(),intent);
    }
}