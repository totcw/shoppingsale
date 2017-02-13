package com.betterda.shoppingsale.order.contract;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.javabean.OrderAll;

/**
 * Created by Administrator on 2016/12/28.
 */

public class OrderDetailContract {

    public interface View extends IView{
        void initRv(RecyclerView.Adapter adapter);


        //设置订单信息
        void setOrder(OrderAll orderAll);

        android.view.View getTextView();

        void setTiVisable();
    }

    public interface Presenter extends IPresenter<View> {
        //立即发货
        void publish();
        //确认提货
        void ziti();

        void onError();
    }

    public interface Model extends IModel{
    }


}