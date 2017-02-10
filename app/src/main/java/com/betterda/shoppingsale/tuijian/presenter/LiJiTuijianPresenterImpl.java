package com.betterda.shoppingsale.tuijian.presenter;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.tuijian.contract.LiJiTuijianContract;
import com.betterda.shoppingsale.tuijian.model.LiJiTuijianModelImpl;
import com.betterda.shoppingsale.tuijian.model.Share;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

/**
 * Created by Administrator on 2016/12/20
 */

public class LiJiTuijianPresenterImpl extends BasePresenter<LiJiTuijianContract.View, LiJiTuijianContract.Model> implements LiJiTuijianContract.Presenter {

    @Override
    public void start() {
        attachModel(new LiJiTuijianModelImpl());
        getData();
    }


    @Override
    public RecyclerView.Adapter getRvShareAdapter() {
        CommonAdapter<Share> commonAdapter = new CommonAdapter<Share>(getView().getmActivity(), R.layout.item_rv_productdetail_share, getModel().getShareList()) {
            @Override
            public void convert(final ViewHolder holder, final Share share) {
                if (share != null) {
                    holder.setText(R.id.tv_item_productdetail_share, share.getName());
                    holder.setImageResource(R.id.iv_item_productdetail_share, share.getResId());
                    holder.setOnClickListener(R.id.linear_item_productdetail_share, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //要判断网络
                            NetworkUtils.isNetWork(getView().getmActivity(), getView().getImageview(), new NetworkUtils.SetDataInterface() {
                                @Override
                                public void getDataApi() {

                                    share(holder.getAdapterPosition());
                                    getView().close();
                                }
                            });

                        }
                    });
                }
            }
        };

        return commonAdapter;
    }

    /**
     * 分享
     *
     * @param share
     */
    private void share(int share) {
        SHARE_MEDIA platform = SHARE_MEDIA.SINA;
        switch (share) {
            case 0:
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case 1:
                platform = SHARE_MEDIA.WEIXIN_CIRCLE;
                break;
            case 2:
                platform = SHARE_MEDIA.SINA;
                break;
            case 3:
                platform = SHARE_MEDIA.QQ;
                break;
        }

        new ShareAction(getView().getmActivity()).setPlatform(platform)
                .withText("hello")
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        UiUtils.showToast(getView().getmActivity(),"分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        UiUtils.showToast(getView().getmActivity(),"分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        UiUtils.showToast(getView().getmActivity(),"取消分享");
                    }
                })
                .share();
    }


    private void getData() {
        getView().getLodapger().setLoadVisable();
        NetworkUtils.isNetWork(getView().getmActivity(), getView().getLodapger(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                getView().getRxManager().add(NetWork.getNetService()
                        .Tuijian(getView().getAccount(), getView().getToken())
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("立即推荐success:" + data.toString());
                                }
                                getView().setUrl(Constants.Url.URL + data);
                                getView().getLodapger().hide();
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("立即推荐fail:" + resultMsg);
                                }
                                getView().getLodapger().setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                getView().ExitToLogin();
                            }
                        }));
            }
        });
    }

    @Override
    public void destroy() {

    }
}