package com.betterda.shoppingsale.ziti;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/29.
 */

public class WriteZiTiActivity extends BaseActivity {
    @BindView(R.id.topbar_writezitima)
    NormalTopBar mTopbarWritezitima;
    @BindView(R.id.et_wiritezitima_number)
    EditText mEtWiritezitimaNumber;

    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_writezitima);
    }

    @Override
    public void init() {
        super.init();
        mTopbarWritezitima.setTitle("自提码核对");
    }

    @OnClick({R.id.bar_back, R.id.btn_writezitima_comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.btn_writezitima_comfirm:
                getData();
                break;
        }
    }

    private void getData() {
        final String barcode = mEtWiritezitimaNumber.getText().toString().trim();
        if (TextUtils.isEmpty(barcode)) {
            UiUtils.showToast(getmActivity(),"请输入自提码");
            return;
        }
        NetworkUtils.isNetWork(getmActivity(), mEtWiritezitimaNumber, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                getRxManager().add(NetWork.getNetService()
                        .comfirmZiti(getAccount(),getToken(),barcode)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("通过自提码获取订单号success:"+data);
                                }
                                //TODO 去订单详情
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("通过自提码获取订单号fail:"+resultMsg);
                                }
                            }

                            @Override
                            public void onExit() {

                            }
                        }));
            }
        });
    }


}
