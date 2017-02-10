package com.betterda.shoppingsale.findpwd.presenter;

import android.text.TextUtils;

import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.findpwd.contract.FindPwdContract;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.utils.UiUtils;


/**
 * Created by Administrator on 2016/12/20
 */

public class FindPwdPresenterImpl extends BasePresenter<FindPwdContract.View, FindPwdContract.Model> implements FindPwdContract.Presenter {

    @Override
    public void start() {

    }

    @Override
    public void countDown() {
        //用正则判断是否是手机号码
        // if (!TextUtils.isEmpty(number)) {
        //  boolean ismobile = number.matches("^1(3[0-9]|4[57]|5[0-9]|8[0-9]|7[0678])\\d{8}$");

        //显示倒计时
        getView().showCountDown();

        // }
    }

    @Override
    public void register() {
        String account = getView().getAccount();
        String pwd = getView().getPwd();
        String pwd2 = getView().getPwd2();
        String yzm = getView().getYzm();

        if (TextUtils.isEmpty(account)) {
            UiUtils.showToast(getView().getmActivity(), "手机号不能为空");
            return;
        }
      /*  if (TextUtils.isEmpty(yzm)) {
            UiUtils.showToast(getView().getmActivity(), "验证码不能为空");
            return;
        }*/
        if (TextUtils.isEmpty(pwd)) {
            UiUtils.showToast(getView().getmActivity(), "密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd2)) {
            UiUtils.showToast(getView().getmActivity(), "再次输入不能为空");
            return;
        }
        if (!pwd.equals(pwd2)) {
            UiUtils.showToast(getView().getmActivity(), "两次输入的密码不同");
            return;
        }

        getView().getRxManager().add(NetWork.getNetService()
                                        .getPwdUpdate(account+"s",pwd,"merchant")
                                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                        .subscribe(new MyObserver<String>() {
                                            @Override
                                            protected void onSuccess(String data, String resultMsg) {
                                                    UiUtils.showToast(getView().getmActivity(),resultMsg);
                                                    getView().getmActivity().finish();
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
    public void destroy() {

    }

}