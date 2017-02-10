package com.betterda.shoppingsale.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.contract.CashWalletContract;
import com.betterda.shoppingsale.wallet.presenter.CashWalletPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 现金钱包
 * Created by Administrator on 2016/12/20.
 */

public class CashWalletActivity extends BaseActivity<CashWalletContract.Presenter> implements CashWalletContract.View {
    @BindView(R.id.topbar_cashwallet)
    public NormalTopBar mTopbarCashwallet;
    @BindView(R.id.tv_wallet2_money)
    public TextView mTvWallet2Money;
    @BindView(R.id.relative_wallet2_chongzhi)
    public RelativeLayout mRelativeWallet2Chongzhi;
    @BindView(R.id.loadpager_cashwallet)
    public LoadingPager mLoadpagerCashwallet;
    public String money;

    @Override
    protected CashWalletContract.Presenter onLoadPresenter() {
        return new CashWalletPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_cashwallet);
    }

    @Override
    public void init() {
        super.init();
        mTopbarCashwallet.setTitle("现金钱包");
        mTopbarCashwallet.setActionText("明细");
        mTopbarCashwallet.setActionTextVisibility(true);
        Intent intent = getIntent();
        if (intent != null) {
          money =  intent.getStringExtra("money");
            if (TextUtils.isEmpty(money)) {
                money = "0";
            }
        }
        mTvWallet2Money.setText(money);
    }

    @OnClick({R.id.relative_wallet2_chongzhi, R.id.bar_back, R.id.bar_action,R.id.tv_cashwallet_shouming})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.bar_action:
                Intent intent = new Intent(getmActivity(), MingXiActivity.class);
                intent.putExtra("type", "现金钱包");
                UiUtils.startIntent(getmActivity(),intent);
                break;
            case R.id.relative_wallet2_chongzhi://提现
                Intent intent2 = new Intent(getmActivity(), TiXianActivity.class);
                intent2.putExtra("money", money);
                UiUtils.startIntent(getmActivity(),intent2);
                break;
            case R.id.tv_cashwallet_shouming://说明
                AlertDialog.Builder builder = new AlertDialog.Builder(getmActivity());
                builder.setTitle("现金钱包说明")
                        .setMessage("现金钱包是可以提现的")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                builder.show();
                break;
        }

    }
}
