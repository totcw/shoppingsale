package com.betterda.shoppingsale.wallet.contract;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/28.
 */

public class TiXianContract {

    public interface View extends IView {
        String getMoney();
    }

    public interface Presenter extends IPresenter<View>{
        //全部提取
        void getAll();

        /**
         * 提现
         */
        void commit();

        void setBank(String bank, String bankCrad);
    }

    public interface Model extends IModel {
    }


}