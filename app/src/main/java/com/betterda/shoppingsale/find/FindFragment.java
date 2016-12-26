package com.betterda.shoppingsale.find;

import android.view.LayoutInflater;
import android.view.View;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.find.contract.FindContract;
import com.betterda.shoppingsale.find.presenter.FindPresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 */

public class FindFragment extends BaseFragment<FindContract.Presenter> implements FindContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_find,null);
    }

    @Override
    protected FindContract.Presenter onLoadPresenter() {
        return new FindPresenterImpl();
    }
}
