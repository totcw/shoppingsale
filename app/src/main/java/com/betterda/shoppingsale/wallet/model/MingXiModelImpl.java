package com.betterda.shoppingsale.wallet.model;


import com.betterda.shoppingsale.wallet.contract.MingXiContract;

import java.util.List;

/**
* Created by Administrator on 2016/12/20
*/

public class MingXiModelImpl implements MingXiContract.Model{

    public void getList(List<MingXi> list) {
        if (list != null) {
            for (int i=0;i<3;i++) {
                list.add(new MingXi());
            }
        }
    }
}