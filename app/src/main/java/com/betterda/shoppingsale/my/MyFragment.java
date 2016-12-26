package com.betterda.shoppingsale.my;

import android.view.LayoutInflater;
import android.view.View;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.my.contract.MyContract;
import com.betterda.shoppingsale.my.presenter.MyPresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyFragment extends BaseFragment<MyContract.Presenter> implements MyContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my,null);
    }
    @Override
    protected MyContract.Presenter onLoadPresenter() {
        return new MyPresenterImpl();
    }
}
