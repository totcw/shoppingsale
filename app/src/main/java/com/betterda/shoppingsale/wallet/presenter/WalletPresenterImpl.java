package com.betterda.shoppingsale.wallet.presenter;


import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.Wallet;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.wallet.contract.WalletContract;

/**
* Created by Administrator on 2016/12/20
*/

public class WalletPresenterImpl extends BasePresenter<WalletContract.View,WalletContract.Model> implements WalletContract.Presenter{

    @Override
    public void start() {
        getData();
    }

    private void getData() {
        NetworkUtils.isNetWork(getView().getmActivity(), getView().getLodapger(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                    getView().getRxManager().add(NetWork.getNetService()
                    .getWallet(getView().getAccount(),getView().getToken())
                    .compose(NetWork.handleResult(new BaseCallModel<Wallet>()))
                    .subscribe(new MyObserver<Wallet>() {
                        @Override
                        protected void onSuccess(Wallet data, String resultMsg) {
                            if (BuildConfig.LOG_DEBUG) {
                                System.out.println("我的钱包success:"+data.toString());
                            }
                            getView().setValue(data);
                        }

                        @Override
                        public void onFail(String resultMsg) {
                            if (BuildConfig.LOG_DEBUG) {
                                System.out.println("我的钱包fail:"+resultMsg);
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
}