package com.betterda.shoppingsale.wallet.presenter;

import android.text.TextUtils;

import com.betterda.mylibrary.ShapeLoadingDialog;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.contract.AddBankCardContract;


/**
 * Created by Administrator on 2016/12/27
 */

public class AddBankCardPresenterImpl extends BasePresenter<AddBankCardContract.View, AddBankCardContract.Model> implements AddBankCardContract.Presenter {
    private ShapeLoadingDialog dialog;
    private String truename;   // 持卡人姓名
    private String identitycard;//身份证
    private String bank;    // 所属银行
    private String cardnum;   // 卡号
    private String number;   // 预留手机号

    @Override
    public void start() {
        dialog = UiUtils.createDialog(getView().getmActivity(), "正在提交...");
    }


    @Override
    public void setBank(String s) {
        bank = s;
    }

    /**
     * 提交数据
     */
    public void commit() {
        truename = getView().getTrueName();
        identitycard = getView().getIdentityCard();
        cardnum = getView().getCardNum();
        number = getView().getNumber();

        if (TextUtils.isEmpty(truename)) {
            UiUtils.showToast(getView().getmActivity(), "持卡人姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(identitycard)) {
            UiUtils.showToast(getView().getmActivity(), "身份证号不能为空");
            return;
        }
        if (TextUtils.isEmpty(bank)) {
            UiUtils.showToast(getView().getmActivity(), "请选择所属银行");
            return;
        }
        if (TextUtils.isEmpty(cardnum)) {
            UiUtils.showToast(getView().getmActivity(), "银行卡号不能为空");
            return;
        }
        if (TextUtils.isEmpty(number)) {
            UiUtils.showToast(getView().getmActivity(), "预留手机号码不能为空");
            return;
        }

        NetworkUtils.isNetWork(getView().getmActivity(), getView().getmEtAddbankName(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UiUtils.showDialog(getView().getmActivity(), dialog);
                NetWork.getNetService()
                        .getBandAdd(getView().getAccount(),getView().getToken(),truename,identitycard,bank,cardnum,number)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                UiUtils.showToast(getView().getmActivity(),resultMsg);
                                UiUtils.dissmissDialog(getView().getmActivity(),dialog);
                                getView().getmActivity().finish();
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                UiUtils.showToast(getView().getmActivity(),resultMsg);
                                UiUtils.dissmissDialog(getView().getmActivity(),dialog);
                            }

                            @Override
                            public void onExit() {

                            }
                        });
            }
        });
    }


    @Override
    public void destroy() {

    }


}