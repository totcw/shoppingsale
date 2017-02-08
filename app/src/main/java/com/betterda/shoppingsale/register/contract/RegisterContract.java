package com.betterda.shoppingsale.register.contract;

import android.view.View;
import android.widget.EditText;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/29.
 */

public class RegisterContract {

    public interface View extends IView{
        android.view.View getTextview();

        EditText getName();

        EditText getNumber();

        EditText getAddressDetail();

        EditText getLeaglPerson();
    }

    public interface Presenter extends IPresenter<View>{
        void setAddress(String s, String s1, String s2);

        void setimageUrl(int number, String data);

        void commit();
    }

    public interface Model extends IModel {
    }


}