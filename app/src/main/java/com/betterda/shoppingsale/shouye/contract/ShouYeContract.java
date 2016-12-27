package com.betterda.shoppingsale.shouye.contract;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.shoppingsale.base.IModel;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.base.IView;
import com.betterda.shoppingsale.shouye.adapter.LunBoTuAdapter;
import com.betterda.shoppingsale.shouye.model.LunBoTu;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ShouYeContract {
    
public interface View extends IView{
    ViewGroup getLpoint();

    ViewPager getViewPager();

    void initVpLunbotu(LunBoTuAdapter lunBoTuAdapter);
}

public interface Presenter extends IPresenter<View>{
    List<LunBoTu> getLunBoTuList();

    void cratePoint();

    void createHandler();

    android.view.View ctreaImageView(int position);
}

public interface Model extends IModel{
}


}