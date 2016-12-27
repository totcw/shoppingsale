package com.betterda.shoppingsale.wallet.presenter;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.wallet.contract.MingXiContract;
import com.betterda.shoppingsale.wallet.model.MingXi;
import com.betterda.shoppingsale.wallet.model.MingXiModelImpl;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/20
*/

public class MingXiPresenterImpl extends BasePresenter<MingXiContract.View,MingXiContract.Model> implements MingXiContract.Presenter{
    private List<MingXi> mingXiList;
    private CommonAdapter<MingXi> mingXiCommonAdapter;

    @Override
    public void start() {
        attachModel(new MingXiModelImpl());
        initRv();
        getData();
    }



    private void initRv() {
        mingXiList = new ArrayList<>();
        mingXiCommonAdapter = new CommonAdapter<MingXi>(getView().getmActivity(), R.layout.item_recycleview_mingxi,mingXiList) {
            @Override
            public void convert(ViewHolder holder, MingXi mingXi) {

            }
        };
        getView().initRv(mingXiCommonAdapter);
    }

    private void getData() {
        getModel().getList(mingXiList);
        mingXiCommonAdapter.notifyDataSetChanged();
    }


    @Override
    public void destroy() {

    }


}