package com.betterda.shoppingsale.login.presenter;

import android.text.TextUtils;

import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.UserInfo;
import com.betterda.shoppingsale.login.contract.LoginContract;
import com.betterda.shoppingsale.utils.UiUtils;


/**
* Created by Administrator on 2016/12/20
*/

public class LoginPresenterImpl  extends BasePresenter<LoginContract.View,LoginContract.Model> implements LoginContract.Presenter{


    @Override
    public void start() {

    }

    /**
     * 登录
     */
    @Override
    public void login() {
        String account = getView().getAccount();
        String pwd = getView().getPwd();
        if (TextUtils.isEmpty(account)) {
            UiUtils.showToast(getView().getmActivity(),"帐号不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            UiUtils.showToast(getView().getmActivity(),"密码不能为空");
            return;
        }

        getView().getRxManager()
                .add(NetWork.getNetService()
                    .getLogin(account,pwd)
                    .compose(NetWork.handleResult(new BaseCallModel<UserInfo>()))
                    .subscribe(new MyObserver<UserInfo>() {
                        @Override
                        protected void onSuccess(UserInfo data, String resultMsg) {

                        }

                        @Override
                        public void onFail(String resultMsg) {
                                UiUtils.showToast(getView().getmActivity(),resultMsg);
                        }

                        @Override
                        public void onExit() {

                        }
                    }));

    }

    @Override
    public void loginThree(String s,String uid) {
        getView().getRxManager().add(NetWork.getNetService()
        .getLoginThree(uid, s)
        .compose(NetWork.handleResult(new BaseCallModel<UserInfo>()))
        .subscribe(new MyObserver<UserInfo>() {
            @Override
            protected void onSuccess(UserInfo data, String resultMsg) {

            }

            @Override
            public void onFail(String resultMsg) {

            }

            @Override
            public void onExit() {

            }
        }));
    }

    @Override
    public void destroy() {

    }


}