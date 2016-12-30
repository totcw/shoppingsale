package com.betterda.shoppingsale.tuijian.presenter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.tuijian.contract.LiJiTuijianContract;
import com.betterda.shoppingsale.tuijian.model.LiJiTuijianModelImpl;
import com.betterda.shoppingsale.tuijian.model.Share;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;


/**
* Created by Administrator on 2016/12/20
*/

public class LiJiTuijianPresenterImpl extends BasePresenter<LiJiTuijianContract.View,LiJiTuijianContract.Model> implements LiJiTuijianContract.Presenter{

    @Override
    public void start() {
        attachModel(new LiJiTuijianModelImpl());
    }

    @Override
    public RecyclerView.Adapter getRvShareAdapter() {
        CommonAdapter<Share> commonAdapter = new CommonAdapter<Share>(getView().getmActivity(), R.layout.item_rv_productdetail_share,getModel().getShareList()) {
            @Override
            public void convert(ViewHolder holder, final Share share) {
                if (share != null) {
                    holder.setText(R.id.tv_item_productdetail_share, share.getName());
                    holder.setImageResource(R.id.iv_item_productdetail_share, share.getResId());
                    holder.setOnClickListener(R.id.linear_item_productdetail_share, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //要判断网络
                            new ShareAction(getView().getmActivity()).setPlatform(SHARE_MEDIA.SINA)
                                    .withText("hello")
                                    .setCallback(new UMShareListener() {
                                        @Override
                                        public void onResult(SHARE_MEDIA share_media) {
                                            System.out.println("成功:"+share_media.toString());
                                        }

                                        @Override
                                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                            System.out.println("错误:"+share_media.toString());
                                        }

                                        @Override
                                        public void onCancel(SHARE_MEDIA share_media) {
                                            System.out.println("取消:"+share_media.toString());
                                        }
                                    })
                                    .share();
                            getView().close();
                        }
                    });
                }
            }
        };

        return commonAdapter;
    }

    @Override
    public void destroy() {

    }


}