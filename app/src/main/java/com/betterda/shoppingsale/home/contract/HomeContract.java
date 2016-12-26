package com.betterda.shoppingsale.home.contract;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * home的mvp接口管理
 * Created by Administrator on 2016/12/5.
 */

public class HomeContract {
    
public interface View extends IView{
}

public interface  Presenter extends IPresenter<View>{


    void switchToFragment(int id);
}

public interface Model extends IModel{
}


}