package com.betterda.shoppingsale.stock.contract;

import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.widget.NormalTopBar;

/**
 * Created by Administrator on 2016/12/28.
 */

public class StockContract {

    public interface View  extends IView{
        RelativeLayout getRelativeTi();

        NormalTopBar getTopView();
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getRvAdapter();

        void onLoadMore();

        void onError();

        void comfirm();
    }

    public interface Model extends IModel {
    }


}