package com.betterda.shoppingsale.login.contract;


import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/20.
 */

public class LoginContract {
    
public interface View extends IView {

    String getAccount();

    String getPwd();
}

public interface Presenter extends IPresenter<View> {
    void login();

    void loginThree(String s, String uid);
}

public interface Model extends IModel {
}


}