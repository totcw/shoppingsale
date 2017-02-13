package com.betterda.shoppingsale.message.presenter;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.MeassageType;
import com.betterda.shoppingsale.message.MeassageActivity;
import com.betterda.shoppingsale.message.contract.SortContract;

import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/08
*/

public class SortPresenterImpl extends BasePresenter<SortContract.View,SortContract.Model> implements SortContract.Presenter{
    private List<MeassageType> meassageList;
    private CommonAdapter<MeassageType> meassageCommonAdapter;

    @Override
    public void start() {

    }




    @Override
    public RecyclerView.Adapter getRvAdapter() {
        meassageList = new ArrayList<>();
        meassageCommonAdapter = new CommonAdapter<MeassageType>(getView().getmActivity(), R.layout.item_rv_meassage,meassageList) {
            @Override
            public void convert(ViewHolder holder, final MeassageType meassage) {
                if (meassage != null) {
                    holder.setText(R.id.tv_item_meassage_title, meassage.getMsgType());
                    holder.setText(R.id.tv_item_meassage_time, meassage.getMsgTime());
                    holder.setText(R.id.tv_item_meassage_content, meassage.getTitle());
                    holder.setOnClickListener(R.id.linear_item_rv_meassage, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent in = new Intent(getView().getmActivity(), MeassageActivity.class);
                            in.putExtra("msgType", meassage.getMsgType());
                            UiUtils.startIntent(getView().getmActivity(), in);
                        }
                    });
                }
            }
        };

        return meassageCommonAdapter;
    }

    public void getData() {
        getView().getLodapger().setLoadVisable();
        NetworkUtils.isNetWork(getView().getmActivity(), getView().getLodapger(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                getView().getRxManager().add(NetWork.getNetService()
                .getMeassageType(getView().getAccount(),getView().getToken())
                .compose(NetWork.handleResult(new BaseCallModel<List<MeassageType>>()))
                .subscribe(new MyObserver<List<MeassageType>>() {
                    @Override
                    protected void onSuccess(List<MeassageType> data, String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("消息类型success:"+data);
                        }
                        if (data != null) {
                            if (meassageList != null && meassageCommonAdapter != null) {
                                meassageList.clear();
                                meassageList.addAll(data);
                                meassageCommonAdapter.notifyDataSetChanged();
                            }
                        }
                        UtilMethod.hideOrEmpty(data,getView().getLodapger());
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("消息类型fail:"+resultMsg);
                        }
                       UtilMethod.setLoadpagerError(getView().getLodapger());
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