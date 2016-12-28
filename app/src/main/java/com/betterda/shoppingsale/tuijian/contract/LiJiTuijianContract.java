package com.betterda.shoppingsale.tuijian.contract;

import android.support.v7.widget.RecyclerView;


import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.tuijian.model.Share;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class LiJiTuijianContract {

    public interface View extends IView {
        void close();
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getRvShareAdapter();
    }

    public interface Model extends IModel {
        List<Share> getShareList();
    }


}