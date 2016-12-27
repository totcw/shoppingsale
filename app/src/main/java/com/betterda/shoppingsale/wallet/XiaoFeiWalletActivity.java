package com.betterda.shoppingsale.wallet;

import android.view.View;


import com.betterda.shoppingsale.R;

import butterknife.OnClick;

/**
 * 消费钱包
 * Created by Administrator on 2016/12/20.
 */

public class XiaoFeiWalletActivity extends CashWalletActivity {

    @Override
    public void init() {
        super.init();
        mTopbarCashwallet.setTitle("消费钱包");
        mRelativeWallet2Chongzhi.setVisibility(View.GONE);
    }

    @OnClick({ R.id.bar_action,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bar_action:
                break;
            case R.id.bar_back:
                back();
                break;

        }

    }
}
