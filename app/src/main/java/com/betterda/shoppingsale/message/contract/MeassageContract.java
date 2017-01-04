package com.betterda.shoppingsale.message.contract;

import android.support.v7.widget.RecyclerView;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2017/1/3.
 */

public class MeassageContract {

    public interface View extends IView{
        void initRv(RecyclerView.Adapter adapter);
    }

    public interface Presenter extends IPresenter<View> {
    }

    public interface Model extends IModel{
    }


}