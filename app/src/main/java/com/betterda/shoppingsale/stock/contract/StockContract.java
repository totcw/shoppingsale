package com.betterda.shoppingsale.stock.contract;

import android.support.v7.widget.RecyclerView;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/28.
 */

public class StockContract {

    public interface View  extends IView{
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getRvAdapter();
    }

    public interface Model extends IModel {
    }


}