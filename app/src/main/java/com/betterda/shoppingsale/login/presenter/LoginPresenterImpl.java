package com.betterda.shoppingsale.login.presenter;

import android.text.TextUtils;

import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.home.MainActivity;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.UserInfo;
import com.betterda.shoppingsale.login.contract.LoginContract;
import com.betterda.shoppingsale.utils.CacheUtils;
import com.betterda.shoppingsale.utils.Constants;
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
        final String pwd = getView().getPwd();
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
                            if (BuildConfig.LOG_DEBUG) {
                                System.out.println("登录success:" + data.toString());
                            }
                            if (data != null) {
                                //设置为登录状态
                               // CacheUtils.putBoolean(getView().getmActivity(), Constants.Cache.ISLOGIN, true);
                                //缓存手机号
                                CacheUtils.putString(getView().getmActivity(), Constants.Cache.ACCOUNT, data.getAccount());
                                CacheUtils.putString(getView().getmActivity(), data.getAccount()+Constants.Cache.PWD, pwd);
                                //缓存token
                                CacheUtils.putString(getView().getmActivity(), data.getAccount() + Constants.Cache.TOKEN, data.getToken());
                                //缓存昵称
                               // CacheUtils.putString(getView().getmActivity(), data.getAccount() + Constants.Cache.NICKNAME, data.getNickName());
                                //缓存头像
                               // CacheUtils.putString(getView().getmActivity(), data.getAccount() + Constants.Cache.TOUXIANG, data.getPhoto());
                                UiUtils.startIntent(getView().getmActivity(), MainActivity.class);
                                getView().getmActivity().finish();
                                UiUtils.showToast(getView().getmActivity(),resultMsg);
                            } else {
                                UiUtils.showToast(getView().getmActivity(), "登录失败,获取信息失败");
                            }
                        }

                        @Override
                        public void onFail(String resultMsg) {
                            if (BuildConfig.LOG_DEBUG) {
                                System.out.println("登录fa:" + resultMsg);
                            }
                                UiUtils.showToast(getView().getmActivity(),resultMsg);
                        }

                        @Override
                        public void onExit() {
                            if (BuildConfig.LOG_DEBUG) {
                                System.out.println("token" );
                            }
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