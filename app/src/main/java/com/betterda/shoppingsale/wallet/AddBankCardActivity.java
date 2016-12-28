package com.betterda.shoppingsale.wallet;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.data.BankData;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.contract.AddBankCardContract;
import com.betterda.shoppingsale.wallet.presenter.AddBankCardPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加银行卡
 * Created by Administrator on 2016/12/27.
 */

public class AddBankCardActivity extends BaseActivity<AddBankCardContract.Presenter> implements AddBankCardContract.View {
    @BindView(R.id.topbar_addbankcard)
    NormalTopBar mTopbarAddbankcard;
    @BindView(R.id.et_addbank_name)
    EditText mEtAddbankName;
    @BindView(R.id.et_addbank_identity)
    EditText mEtAddbankIdentity;
    @BindView(R.id.tv_addbank_bankname)
    TextView mTvAddbankBankname;
    @BindView(R.id.et_addbank_cardNo)
    EditText mEtAddbankCardNo;
    @BindView(R.id.et_addbank_number)
    EditText mEtAddbankNumber;

    @Override
    protected AddBankCardContract.Presenter onLoadPresenter() {
        return new AddBankCardPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_addbankcard);
    }

    @Override
    public void init() {
        super.init();
        mTopbarAddbankcard.setTitle("添加银行卡");
    }

    @OnClick({R.id.linear_addbank_bankname, R.id.btn_addbank_next,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_addbank_bankname:
                chooseBank();
                break;
            case R.id.btn_addbank_next:
                getPresenter().commit();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    /**
     * 选择银行卡
     */
    private void chooseBank() {
        final View view = View.inflate(getmActivity(), R.layout.pp_chose_bank, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_ppchosebank);
        recyclerView.setLayoutManager(new LinearLayoutManager(getmActivity()));
        recyclerView.setAdapter(new CommonAdapter<String>(getmActivity(), R.layout.item_recycleview_chose_bank, BankData.getBankList()) {

            @Override
            public void convert(ViewHolder viewHolder, final String s) {
                if (!TextUtils.isEmpty(s)) {
                    viewHolder.setText(R.id.tv_chose_bank, s);
                    viewHolder.setImageResource(R.id.iv_chose_bank, BankData.getBank(s));
                }

                viewHolder.setOnClickListener(R.id.linear_chose_bank, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTvAddbankBankname.setText(s);
                        getPresenter().setBank(s);
                        closePopupWindow();
                    }
                });
            }
        });
        setUpPopupWindow(view);
    }


    @Override
    public String getTrueName() {
        return mEtAddbankName.getText().toString().trim();
    }

    @Override
    public String getIdentityCard() {
        return mEtAddbankIdentity.getText().toString().trim();
    }

    @Override
    public String getCardNum() {
        return mEtAddbankCardNo.getText().toString().trim();
    }

    @Override
    public String getNumber() {
        return mEtAddbankNumber.getText().toString().trim();
    }

    public EditText getmEtAddbankName() {
        return mEtAddbankName;
    }


    @Override
    public void dismiss() {
        super.dismiss();
        UiUtils.backgroundAlpha(1.0f,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != BankData.bankMap) {
            BankData.bankMap.clear();
            BankData.bankMap = null;
        }
        if (null != BankData.bankList) {
            BankData.bankList.clear();
            BankData.bankList = null;
        }
    }


}
