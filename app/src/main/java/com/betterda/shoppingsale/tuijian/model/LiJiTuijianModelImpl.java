package com.betterda.shoppingsale.tuijian.model;


import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.tuijian.contract.LiJiTuijianContract;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/20
*/

public class LiJiTuijianModelImpl implements LiJiTuijianContract.Model{
    public List<Share> getShareList() {
        List<Share> list = new ArrayList<>();
        list.add(new Share("微信好友", R.mipmap.wxfriend));
        list.add(new Share("朋友圈", R.mipmap.friend));
        list.add(new Share("新浪微博", R.mipmap.weibo));
        list.add(new Share("QQ", R.mipmap.qq));
        return list;
    }
}