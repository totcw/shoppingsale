package com.betterda.shoppingsale.shouye;

import android.view.LayoutInflater;
import android.view.View;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.shouye.contract.ShouYeContract;
import com.betterda.shoppingsale.shouye.presenter.ShouYePresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ShouYeFragment extends BaseFragment<ShouYeContract.Presenter> implements ShouYeContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_shouye,null);
    }

    @Override
    protected ShouYeContract.Presenter onLoadPresenter() {
        return new ShouYePresenterImpl();
    }
}
