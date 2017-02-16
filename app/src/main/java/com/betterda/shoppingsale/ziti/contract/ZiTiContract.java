package com.betterda.shoppingsale.ziti.contract;

import android.support.v7.widget.RecyclerView;

import com.betterda.mylibrary.xrecycleview.XRecyclerView;
import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/29.
 */

public class ZiTiContract {

    public interface View extends IView{
        XRecyclerView getRv();
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getRvAdapter();

        RecyclerView.ItemDecoration getItemDecoration();

        void onStart();

        void onLoadMore();

        void onError();
    }

    public interface Model extends IModel {
    }


}