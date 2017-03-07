package com.betterda.shoppingsale.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.javabean.Bus;
import com.betterda.shoppingsale.order.contract.OrderDetailContract;
import com.betterda.shoppingsale.javabean.OrderAll;
import com.betterda.shoppingsale.order.presenter.OrderDetailPresenterImpl;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.betterda.shoppingsale.widget.NormalTopBar;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详细
 * Created by Administrator on 2016/12/28.
 */

public class OrderDetailActivity extends BaseActivity<OrderDetailContract.Presenter> implements OrderDetailContract.View {
    @BindView(R.id.topbar_oderdetail)
    NormalTopBar mTopbarOderdetail;
    @BindView(R.id.relative_orderdetail_get)
    RelativeLayout mRelativeGet;//立即发货
    @BindView(R.id.relative_orderdetail_ti)
    RelativeLayout mRelativeTi;//确认提货
    @BindView(R.id.tv_orderdetail_state)
    TextView mTvOrderdetailState; //交易状态
    @BindView(R.id.tv_orderdetail_ordernum)
    TextView mTvOrderdetailOrdernum;//订单号
    @BindView(R.id.tv_orderdetail_shouhuoren2)
    TextView mTvOrderdetailShouhuoren2;//收货人
    @BindView(R.id.tv_orderdetail_address2)
    TextView mTvOrderdetailAddress2;//收货地址
    @BindView(R.id.tv_orderdetail_number)
    TextView mTvOrderdetailNumber;//电话号码
    @BindView(R.id.rv_orderdetail)
    RecyclerView mRvOrderdetail;
    @BindView(R.id.tv_orderdetail_yunfei)
    TextView mTvOrderdetailYunfei;//运费
    @BindView(R.id.tv_orderdetail_heji_money)
    TextView mTvOrderdetailHejiMoney;//合计
    @BindView(R.id.tv_orderdetail_amnout)
    TextView mTvOrderdetailAmnout;//商品数量
    @BindView(R.id.tv_orderdetail_fapiao)
    TextView mTvOrderdetailFapiao;//发票
    @BindView(R.id.tv_orderdetail_peisong)
    TextView mTvOrderdetailPeisong;//配送方式
    @BindView(R.id.tv_orderdetail_time)
    TextView mTvOrderdetailTime;//订单时间
    @BindView(R.id.tv_orderdetail_daijinjuan)
    TextView mTvOrderdetailDaiJinJuan;//代金卷
    @BindView(R.id.loadpager_orderdetail)
    LoadingPager mLoadpager;

    @Override
    protected OrderDetailContract.Presenter onLoadPresenter() {
        return new OrderDetailPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_orderdetail);
    }

    @Override
    public void init() {
        super.init();
        mTopbarOderdetail.setTitle("订单详细");
        mLoadpager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onError();
            }
        });
    }


    @OnClick({R.id.relative_orderdetail_get, R.id.relative_orderdetail_ti, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.relative_orderdetail_get://立即发货
                getPresenter().publish();
                break;
            case R.id.relative_orderdetail_ti://确认提货
                getPresenter().ziti();

                break;
        }
    }

    public void initRv(RecyclerView.Adapter adapter) {
        mRvOrderdetail.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvOrderdetail.setAdapter(adapter);
    }

    @Override
    public void setOrder(OrderAll orderAll) {

        mTvOrderdetailAddress2.setText(orderAll.getAddress());
        mTvOrderdetailShouhuoren2.setText(orderAll.getName());
        mTvOrderdetailNumber.setText(orderAll.getNumber());
        mTvOrderdetailOrdernum.setText(orderAll.getOrderId());
        mTvOrderdetailYunfei.setText(orderAll.getFreight());
        mTvOrderdetailPeisong.setText(orderAll.getType());
        mTvOrderdetailDaiJinJuan.setText("￥"+orderAll.getVoucher());
        mTvOrderdetailTime.setText(orderAll.getTime());
        mTvOrderdetailHejiMoney.setText("￥ "+orderAll.getMoney());  //合计
        mTvOrderdetailAmnout.setText("共" + UtilMethod.addAmount(orderAll.getBusList()) + "件商品"); //数量

        if ("待发货".equals(orderAll.getOrderStatus())) {
            if (mRelativeTi.getVisibility() != View.VISIBLE) {
                mRelativeGet.setVisibility(View.VISIBLE);
            }
        } else {
            if (mRelativeTi.getVisibility() != View.VISIBLE) {
                mRelativeGet.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public View getTextView() {
        return mRelativeGet;
    }

    @Override
    public void setTiVisable() {
        mRelativeTi.setVisibility(View.VISIBLE);
    }

    @Override
    public LoadingPager getLodapger() {
        return mLoadpager;
    }
}
