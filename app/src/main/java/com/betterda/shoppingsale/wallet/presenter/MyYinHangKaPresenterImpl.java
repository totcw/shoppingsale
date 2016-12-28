package com.betterda.shoppingsale.wallet.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.mylibrary.ShapeLoadingDialog;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.data.BankData;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.betterda.shoppingsale.wallet.contract.MyYinHangKaContract;
import com.betterda.shoppingsale.wallet.model.BankCard;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27
 */

public class MyYinHangKaPresenterImpl extends BasePresenter<MyYinHangKaContract.View, MyYinHangKaContract.Model> implements MyYinHangKaContract.Presenter {
    private List<BankCard> mBankCardList;
    private CommonAdapter<BankCard> mBankCardCommonAdapter;
    private ShapeLoadingDialog dialog;

    @Override
    public void start() {
        getData();
    }


    @Override
    public RecyclerView.Adapter getRvAdapter() {
        mBankCardList = new ArrayList<>();
        mBankCardCommonAdapter = new CommonAdapter<BankCard>(getView().getmActivity(), R.layout.item_recycleview_yinhangka, mBankCardList) {
            @Override
            public void convert(final ViewHolder viewHolder, final BankCard bankCard) {
                if (bankCard != null) {
                    viewHolder.setText(R.id.tv_yinhangka_name, bankCard.getBank());
                    viewHolder.setText(R.id.tv_yinhangka_number, bankCard.getCardNum());
                    viewHolder.setText(R.id.tv_yinhangka_type, bankCard.getType());
                    if (BankData.getBank(bankCard.getBank()) != -1) {
                        viewHolder.setImageResource(R.id.iv_yinhangka_icon, BankData.getBank(bankCard.getBank()));
                    }
                    viewHolder.setOnClickListener(R.id.linear_yinhangka, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UiUtils.showDialog(getView().getmActivity(),"温馨提示", "确定要删除该银行卡吗?", new UiUtils.showDialogListener() {
                                @Override
                                public void exitDialog() {

                                }

                                @Override
                                public void comfirmDialog() {
                                    comfirm(bankCard);
                                }
                            });
                        }
                    });
                }
            }
        };


        return mBankCardCommonAdapter;
    }



    /**
     * 删除银行卡的确定按钮
     */

    public void comfirm(final BankCard bankCard) {

        NetworkUtils.isNetWork(getView().getmActivity(), getView().getNormalTopBar(), new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                if (dialog == null) {
                    dialog = UiUtils.createDialog(getView().getmActivity(), "正在删除...");
                }
                //开启进度显示
                UiUtils.showDialog(getView().getmActivity(), dialog);
                if (mBankCardList != null ) {
                    if (bankCard != null) {
                        getView().getRxManager().add(NetWork.getNetService()
                                .getBandDelete("", "", bankCard.getId())
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        //将对象从容器中删除
                                        if (mBankCardList != null) {
                                            mBankCardList.remove(bankCard);
                                        }
                                        //更新适配器
                                        if (mBankCardCommonAdapter != null) {
                                            mBankCardCommonAdapter.notifyDataSetChanged();
                                        }
                                        UtilMethod.hideOrEmpty(mBankCardList, getView().getLoadpager());
                                        //取消进度显示
                                        UiUtils.dissmissDialog(getView().getmActivity(), dialog);

                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        UiUtils.dissmissDialog(getView().getmActivity(), dialog);
                                        UiUtils.showToast(getView().getmActivity(), resultMsg);

                                    }

                                    @Override
                                    public void onExit() {

                                    }
                                }));


                    }

                }

            }
        });
    }

    private void getData() {
        for (int i = 0; i < 3; i++) {
            mBankCardList.add(new BankCard());
        }
    }

    @Override
    public void destroy() {
        if (BankData.bankMap != null) {
            BankData.bankMap.clear();
            BankData.bankMap = null;
        }
    }


}