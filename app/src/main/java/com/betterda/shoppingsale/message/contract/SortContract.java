package com.betterda.shoppingsale.message.contract;

import android.support.v7.widget.RecyclerView;

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
    RecyclerView.Adapter getRvAdapter();

    void getData();
}

public interface Model extends IModel{
}


}