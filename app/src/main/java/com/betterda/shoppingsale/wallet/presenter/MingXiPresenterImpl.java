package com.betterda.shoppingsale.wallet.presenter;

import android.support.v7.widget.RecyclerView;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.javabean.TitleBean;
import com.betterda.shoppingsale.wallet.contract.MingXiContract;
import com.betterda.shoppingsale.wallet.model.MingXi;
import com.betterda.shoppingsale.wallet.model.MingXiModelImpl;
import com.betterda.shoppingsale.widget.TitleItemDecoration;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/20
*/

public class MingXiPresenterImpl extends BasePresenter<MingXiContract.View,MingXiContract.Model> implements MingXiContract.Presenter{
    private List<TitleBean<MingXi>> mingXiList;
    private CommonAdapter<TitleBean<MingXi>> mingXiCommonAdapter;

    @Override
    public void start() {
        attachModel(new MingXiModelImpl());
        initRv();
        getData();
    }



    private void initRv() {
        mingXiList = new ArrayList<>();
        mingXiCommonAdapter = new CommonAdapter<TitleBean<MingXi>>(getView().getmActivity(), R.layout.item_recycleview_mingxi,mingXiList) {
            @Override
            public void convert(ViewHolder holder, TitleBean<MingXi> mingXi) {

            }
        };
        getView().initRv(mingXiCommonAdapter);
    }

    private void getData() {
        getModel().getList(mingXiList);
        mingXiCommonAdapter.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new TitleItemDecoration<MingXi>(getView().getmActivity(),mingXiList);
    }

    @Override
    public void loadMore() {
        for (int i = 0; i < 3; i++) {
                TitleBean<MingXi> mingXiTitleBean = new TitleBean<>();
                mingXiTitleBean.setTag(i+"月");
                mingXiList.add(mingXiTitleBean);
        }
    }

    @Override
    public void destroy() {

    }


}