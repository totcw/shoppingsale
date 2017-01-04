package com.betterda.shoppingsale.message.presenter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.message.MeassageActivity;
import com.betterda.shoppingsale.message.contract.SortContract;
import com.betterda.shoppingsale.message.model.Meassage;
import com.betterda.shoppingsale.utils.UiUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/08
*/

public class SortPresenterImpl extends BasePresenter<SortContract.View,SortContract.Model> implements SortContract.Presenter{
    private List<Meassage> meassageList;
    private CommonAdapter<Meassage> meassageCommonAdapter;

    @Override
    public void start() {
        getData();
    }




    @Override
    public RecyclerView.Adapter getRvAdapter() {
        meassageList = new ArrayList<>();
        meassageCommonAdapter = new CommonAdapter<Meassage>(getView().getmActivity(), R.layout.item_rv_meassage,meassageList) {
            @Override
            public void convert(ViewHolder holder, Meassage meassage) {
                if (meassage != null) {
                    holder.setOnClickListener(R.id.linear_item_rv_meassage, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UiUtils.startIntent(getView().getmActivity(), MeassageActivity.class);
                        }
                    });
                }
            }
        };

        return meassageCommonAdapter;
    }

    private void getData() {
        for (int i = 0; i <3 ; i++) {
            meassageList.add(new Meassage());
        }
        meassageCommonAdapter.notifyDataSetChanged();
    }


    @Override
    public void destroy() {

    }



}