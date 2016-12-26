package com.betterda.shoppingsale.sort;

import android.view.LayoutInflater;
import android.view.View;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.sort.contract.SortContract;
import com.betterda.shoppingsale.sort.presenter.SortPresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 */

public class SortFragment extends BaseFragment<SortContract.Presenter>implements SortContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_sort,null);
    }

    @Override
    protected SortContract.Presenter onLoadPresenter() {
        return new SortPresenterImpl();
    }
}
