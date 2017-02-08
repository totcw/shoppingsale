package com.betterda.shoppingsale.wallet.contract;


import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.javabean.Wallet;

/**
 * Created by Administrator on 2016/12/20.
 */

public class WalletContract {

    public interface View extends IView {
        void setValue(Wallet data);
    }

    public interface Presenter extends IPresenter<View> {
    }

    public interface Model extends IModel {
    }


}