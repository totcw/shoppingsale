package com.betterda.shoppingsale.login.presenter;

import android.text.TextUtils;

import com.betterda.mylibrary.ShapeLoadingDialog;
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
import com.betterda.shoppingsale.utils.NetworkUtils;
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
        final String account = getView().getTvLogin().getText().toString().trim();
        final String pwd = getView().getTvPwd().getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            UiUtils.showToast(getView().getmActivity(),"帐号不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            UiUtils.showToast(getView().getmActivity(),"密码不能为空");
            return;
        }

        NetworkUtils.isNetWork(getView().getmActivity(), getView().getTvPwd(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                final ShapeLoadingDialog dialog = UiUtils.createDialog(getView().getmActivity(), "正在登录...");
                UiUtils.showDialog(getView().getmActivity(),dialog);
                getView().getRxManager()
                        .add(NetWork.getNetService()
                                .getLogin(account+"s",pwd,"merchant")
                                .compose(NetWork.handleResult(new BaseCallModel<UserInfo>()))
                                .subscribe(new MyObserver<UserInfo>() {
                                    @Override
                                    protected void onSuccess(UserInfo data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("登录success:" + data.toString());
                                        }
                                        UiUtils.dissmissDialog(getView().getmActivity(),dialog);
                                        if (data != null) {
                                            boolean selected = getView().getImageView().isSelected();
                                            if (selected) {
                                                //是否记住密码
                                                CacheUtils.putBoolean(getView().getmActivity(), Constants.Cache.REMEMBER, true);
                                            } else {
                                                //是否记住密码
                                                CacheUtils.putBoolean(getView().getmActivity(), Constants.Cache.REMEMBER, false);
                                            }

                                            //缓存手机号
                                            CacheUtils.putString(getView().getmActivity(), Constants.Cache.ACCOUNT, data.getAccount());
                                            CacheUtils.putString(getView().getmActivity(), data.getAccount()+Constants.Cache.PWD, pwd);
                                            //缓存token
                                            CacheUtils.putString(getView().getmActivity(), data.getAccount() + Constants.Cache.TOKEN, data.getToken());
                                            UiUtils.startIntent(getView().getmActivity(), MainActivity.class);
                                            //  getView().getmActivity().finish();
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
                                        UiUtils.dissmissDialog(getView().getmActivity(),dialog);
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
        });


    }



    @Override
    public void loginThree(String s,String uid) {

    }

    @Override
    public void destroy() {

    }


}