package com.betterda.shoppingsale.sort.contract;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;

/**
 * Created by Administrator on 2016/12/8.
 */

public class SortContract {
    
public interface View extends IView{
}

public interface Presenter extends IPresenter<View>{
}

public interface Model extends IModel{
}


}