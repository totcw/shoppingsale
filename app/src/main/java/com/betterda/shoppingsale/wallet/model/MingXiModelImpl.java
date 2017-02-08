package com.betterda.shoppingsale.wallet.model;


import com.betterda.shoppingsale.javabean.MingXi;
import com.betterda.shoppingsale.javabean.TitleBean;
import com.betterda.shoppingsale.wallet.contract.MingXiContract;

import java.util.List;

/**
* Created by Administrator on 2016/12/20
*/

public class MingXiModelImpl implements MingXiContract.Model{

    public void getList(List<TitleBean<MingXi>> list) {
        if (list != null) {
            for (int i=1;i<14;i++) {
                TitleBean<MingXi> mingXiTitleBean = new TitleBean<>();
                mingXiTitleBean.setTag(i+"月");
                list.add(mingXiTitleBean);
            }
        }
    }
}