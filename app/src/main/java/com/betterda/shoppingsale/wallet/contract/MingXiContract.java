package com.betterda.shoppingsale.wallet.contract;

import android.support.v7.widget.RecyclerView;


import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.javabean.TitleBean;
import com.betterda.shoppingsale.wallet.model.MingXi;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class MingXiContract  {
    
public interface View extends IView {
    void initRv(RecyclerView.Adapter adapter);
}

public interface Presenter extends IPresenter<View> {
    RecyclerView.ItemDecoration getItemDecoration();

    void loadMore();
}

public interface Model extends IModel {
    void getList(List<TitleBean<MingXi>> list);
}


}