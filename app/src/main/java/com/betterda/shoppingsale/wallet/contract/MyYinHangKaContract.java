package com.betterda.shoppingsale.wallet.contract;

import android.support.v7.widget.RecyclerView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.widget.NormalTopBar;


/**
 * Created by Administrator on 2016/12/27.
 */

public class MyYinHangKaContract {

    public interface View extends IView {
        NormalTopBar getNormalTopBar();

        LoadingPager getLoadpager();
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getRvAdapter();

        void onStart();

        void onError();
    }

    public interface Model extends IModel {
    }


}