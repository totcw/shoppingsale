package com.betterda.shoppingsale.tuijian.contract;


import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/20.
 */

public class MyTuijianContract {

    public interface View extends IView {
    }

    public interface Presenter extends IPresenter<View> {
    }

    public interface Model extends IModel {
    }


}