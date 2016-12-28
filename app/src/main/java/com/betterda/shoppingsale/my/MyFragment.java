package com.betterda.shoppingsale.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.my.contract.MyContract;
import com.betterda.shoppingsale.my.presenter.MyPresenterImpl;
import com.betterda.shoppingsale.tuijian.LiJiTuijianActivity;
import com.betterda.shoppingsale.tuijian.MyTuijianActivity;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.WalletActivity;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyFragment extends BaseFragment<MyContract.Presenter> implements MyContract.View {


    @BindView(R.id.topbar_my)
    NormalTopBar mTopbarMy;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    protected MyContract.Presenter onLoadPresenter() {
        return new MyPresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();
        mTopbarMy.setTitle("个人中心");
        mTopbarMy.setBackVisibility(false);
    }

    @OnClick({R.id.relative_my_wallet, R.id.relative_my_member, R.id.relative_my_tuijian, R.id.relative_my_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_my_wallet:
                UiUtils.startIntent(getmActivity(), WalletActivity.class);
                break;
            case R.id.relative_my_member:
                UiUtils.startIntent(getmActivity(), MyTuijianActivity.class);
                break;
            case R.id.relative_my_tuijian:
                UiUtils.startIntent(getmActivity(), LiJiTuijianActivity.class);
                break;
            case R.id.relative_my_setting:
                break;
        }
    }
}
