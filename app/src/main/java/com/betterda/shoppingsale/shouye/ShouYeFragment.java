package com.betterda.shoppingsale.shouye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.betterda.mylibrary.Utils.StatusBarCompat;
import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseFragment;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.ScanStock;
import com.betterda.shoppingsale.order.OrderActivity;
import com.betterda.shoppingsale.saomiao.SaoMiaoActivity;
import com.betterda.shoppingsale.shouye.adapter.LunBoTuAdapter;
import com.betterda.shoppingsale.shouye.contract.ShouYeContract;
import com.betterda.shoppingsale.shouye.presenter.ShouYePresenterImpl;
import com.betterda.shoppingsale.stock.StockActivity;
import com.betterda.shoppingsale.utils.GsonTools;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.betterda.shoppingsale.ziti.ZiTiActivity;
import com.betterda.shoppingsale.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ShouYeFragment extends BaseFragment<ShouYeContract.Presenter> implements ShouYeContract.View, View.OnClickListener {

    @BindView(R.id.frame_shouye_lunbotu)
    FrameLayout mFrameShouyeLunbotu; //轮播图
    @BindView(R.id.frame_shouye_second)
    FrameLayout mFrameShouyeSecond;//第二区域
    @BindView(R.id.loadpager_shouye)
    LoadingPager mLoadpagerShouye;

    private View mViewLunbotu;//轮播图的view
    private ViewPager mVpLunbotu;
    private LinearLayout mLinearFirst;
    private View mViewSecond; //第二区域的view
    private View mLinearSeond1;//待发货
    private View mLinearSeond2;//待收货
    private View mLinearSeond3;
    private View mLinearSeond4;
    private View mLinearSeond5;
    private View mLinearSeond6;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_shouye, null);
    }

    @Override
    protected ShouYeContract.Presenter onLoadPresenter() {
        return new ShouYePresenterImpl();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {//隐藏
        } else {
            StatusBarCompat.setStatusBar5(getmActivity(),R.color.backgroudyellow);
        }
    }


    @Override
    public void initData() {
        super.initData();
        //加载轮播图
        initLunbotu();
        //加载第二区域
        initSecond();

    }

    @Override
    public void initListenr() {
        super.initListenr();
        mLinearSeond1.setOnClickListener(this);
        mLinearSeond2.setOnClickListener(this);
        mLinearSeond3.setOnClickListener(this);
        mLinearSeond4.setOnClickListener(this);
        mLinearSeond5.setOnClickListener(this);
        mLinearSeond6.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_second1://待发货
                startToOrder("待发货");
                break;
            case R.id.linear_second2://待收货
                startToOrder("待收货");
                break;
            case R.id.linear_second3://全部订单
                startToOrder("全部");
                break;
            case R.id.linear_second4://扫码入库
                UiUtils.startIntent(getmActivity(), SaoMiaoActivity.class);
               /* Intent intent = new Intent(getmActivity(), CaptureActivity.class);
                UiUtils.startIntentForResult(getmActivity(),intent,0);*/
                break;
            case R.id.linear_second5://商品库存
                UiUtils.startIntent(getmActivity(), StockActivity.class);
                break;
            case R.id.linear_second6://扫码自提
                UiUtils.startIntent(getmActivity(), ZiTiActivity.class);
                break;
        }
    }


    /**
     * 初始化轮播图
     */
    private void initLunbotu() {
        mViewLunbotu = View.inflate(getmActivity(), R.layout.view_lunbotu, null);
        ImageView mIvFirst = (ImageView) mViewLunbotu.findViewById(R.id.iv_main_first);
        mVpLunbotu = (ViewPager) mViewLunbotu.findViewById(R.id.vpager_lunbotu);
        mLinearFirst = (LinearLayout) mViewLunbotu.findViewById(R.id.ll_points);
        mFrameShouyeLunbotu.addView(mViewLunbotu);

    }

    /**
     * 加载第二区域
     */
    private void initSecond() {
        mViewSecond = View.inflate(getmActivity(), R.layout.view_second, null);
        mLinearSeond1 = mViewSecond.findViewById(R.id.linear_second1);
        mLinearSeond2 = mViewSecond.findViewById(R.id.linear_second2);
        mLinearSeond3 = mViewSecond.findViewById(R.id.linear_second3);
        mLinearSeond4 = mViewSecond.findViewById(R.id.linear_second4);
        mLinearSeond5 = mViewSecond.findViewById(R.id.linear_second5);
        mLinearSeond6 = mViewSecond.findViewById(R.id.linear_second6);
        mFrameShouyeSecond.addView(mViewSecond);
    }

    /**
     * 跳转到订单界面
     */
    private void startToOrder(String type) {
        Intent intent = new Intent(getmActivity(), OrderActivity.class);
        if ("全部".equals(type)) {
            intent.putExtra("orderStatus", "");
        } else {
            intent.putExtra("orderStatus",type);
        }

        intent.putExtra("type",type);
        UiUtils.startIntent(getmActivity(),intent);
    }

    /**
     * 初始化轮播图的viewpager
     *
     * @param lunBoTuAdapter
     */
    @Override
    public void initVpLunbotu(LunBoTuAdapter lunBoTuAdapter) {
        mVpLunbotu.setAdapter(lunBoTuAdapter);
    }

    @Override
    public ViewGroup getLpoint() {
        return mLinearFirst;
    }

    @Override
    public ViewPager getViewPager() {
        return mVpLunbotu;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == 0) {

                if (data != null) {
                    String result = data.getStringExtra("result");
                    if (!TextUtils.isEmpty(result)) {
                        //提交扫描的json 信息给后台去入库
                        getData(result);
                    } else {
                        UiUtils.showToast(getmActivity(), "扫描失败");
                    }

                }
            }
        }
    }

    private void getData(final String result) {
  /*      ScanStock scanStock = new ScanStock();
        scanStock.setProductId("402881e859818b18015981fa8ffa0007");
        scanStock.setQuantity("10");
        ScanStock scanStock2 = new ScanStock();
        scanStock2.setProductId("402881e859904962015990bf3f1c0002");
        scanStock2.setQuantity("5");
        List<ScanStock> list = new ArrayList<>();
        list.add(scanStock);
        list.add(scanStock2);*/
        final ShapeLoadingDialog dialog = UiUtils.createDialog(getmActivity(), "正在入库");
        UiUtils.showDialog(getmActivity(),dialog);
        getRxManager().add(NetWork.getNetService()
                .scanStock(getAccount(),getToken(),result)
                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                .subscribe(new MyObserver<String>() {
                    @Override
                    protected void onSuccess(String data, String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("扫描入库:"+result);
                        }
                        UiUtils.showToast(getmActivity(),resultMsg);
                        UiUtils.dissmissDialog(getmActivity(),dialog);
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("扫描入库fail:"+resultMsg);
                        }
                        UiUtils.showToast(getmActivity(),resultMsg);
                        UiUtils.dissmissDialog(getmActivity(),dialog);
                    }

                    @Override
                    public void onExit() {

                        UiUtils.dissmissDialog(getmActivity(),dialog);
                            ExitToLogin();
                    }
                }));
    }

}
