package com.betterda.shoppingsale.shouye.presenter;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BasePresenter;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.shouye.adapter.LunBoTuAdapter;
import com.betterda.shoppingsale.shouye.contract.ShouYeContract;
import com.betterda.shoppingsale.shouye.model.LunBoTu;
import com.betterda.shoppingsale.utils.UtilMethod;
import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/08
*/

public class ShouYePresenterImpl extends BasePresenter<ShouYeContract.View,ShouYeContract.Model> implements ShouYeContract.Presenter, ViewPager.OnPageChangeListener
{

    private List<LunBoTu> stringList; //轮播图图片的容器
    private boolean isShow;//是否设置轮播图成功了
    private int currentIndex;
    private InternalHandler mHandler;

    @Override
    public void start() {
        getLunBoTuData();
    }


    /**
     * 获取轮播图的数据
     */
    private void getLunBoTuData() {

        if (isShow) {
            //如果已经加载过了就不重复加载了
            return;
        }
        stringList = new ArrayList<>();
        stringList.add(new LunBoTu("http://www.meichebang.com.cn/xsnano_web/upload/files/20161024162309xE23Av0x.png","1"));
        getView().initVpLunbotu(new LunBoTuAdapter(this));

        getView().getRxManager().add(NetWork.getNetService()
                .getAdvertisement()
                .compose(NetWork.handleResult(new BaseCallModel<LunBoTu>()))
                .subscribe(new MyObserver<LunBoTu>() {
                    @Override
                    protected void onSuccess(LunBoTu data, String resultMsg) {

                    }

                    @Override
                    public void onFail(String resultMsg) {

                    }

                    @Override
                    public void onExit() {

                    }
                }));


      /*  RequestParams params = new RequestParams(Constants.URL_LUNBO);
        params.addBodyParameter("regionId", Constants.regiondId);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (stringList != null) {
                    stringList.clear();
                }
                UtilMethod.parSerJson(s, new ParserJsonInterface() {
                    @Override
                    public void parser(Map<String, Object> map) {
                        if (map != null) {
                            LunBoTu lunBoTu = new LunBoTu();
                            lunBoTu.setUrl(UtilMethod.url(map.get("picture").toString()));
                            lunBoTu.setId(map.get("id").toString());
                            if (stringList != null) {
                                stringList.add(lunBoTu);
                            }
                        }

                    }
                });



                if (iShouyeView != null) {
                    iShouyeView.setLunBoAdapter();
                    //  iShouyeView.getViewPager().setPageTransformer(true,new CubeTransformer());
                    //设置为true,表示轮播加载成功
                    isShow = true;
                }
                if (iShouyeView != null) {
                    if (iShouyeView.getImageviewMianFirst() != null) {
                        iShouyeView.getImageviewMianFirst().setVisibility(View.INVISIBLE);
                    }

                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (iShouyeView != null) {
                    if (iShouyeView.getImageviewMianFirst() != null) {
                        iShouyeView.getImageviewMianFirst().setVisibility(View.VISIBLE);
                    }

                }

            }
        });*/
    }


    /**
     * 返回轮播图的数据
     * @return
     */
    @Override
    public List<LunBoTu> getLunBoTuList() {
        return stringList;
    }

    /**
     * 创建轮播图的点
     */
    @Override
    public void cratePoint() {
        if (null != stringList && stringList.size() > 0) {
            int size = stringList.size();
            getView().getLpoint().removeAllViews();
            // 添加图片
            for (int i = 0; i < size; i++) {
                // 设置圆点
                View viewPoint = new View(getView().getmActivity());
                viewPoint.setBackgroundResource(R.drawable.point_background);
                int weight = UtilMethod.dip2px(getView().getmActivity(), 5);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(weight, weight);
                lp.leftMargin = weight;
                viewPoint.setLayoutParams(lp);
                viewPoint.setEnabled(false);
                getView().getLpoint().addView(viewPoint);
            }
            View childAt = getView().getLpoint().getChildAt(0);
            if (null != childAt) {
                childAt.setEnabled(true);
            }
        }
    }

    /**
     * 创建轮播图的handler
     */
    @Override
    public void createHandler() {
        getView().getViewPager().setOnPageChangeListener(this);

        if (mHandler == null) {

            if (getView() != null && getView().getmActivity() != null)
                mHandler = new InternalHandler(getView().getmActivity());
        }

        mHandler.removeCallbacksAndMessages(null); // 把所有的消息和任务清空
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(0);
                }
            }
        }, 2000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != getView().getLpoint()) {
            View childAt = getView().getLpoint().getChildAt(currentIndex);
            if (null != childAt) {
                childAt.setEnabled(false);
            }
            if (stringList.size() > 0) {

                View childAt1 = getView().getLpoint().getChildAt(position % stringList.size());
                if (null != childAt1) {
                    childAt1.setEnabled(true);
                }
                currentIndex = position % stringList.size();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class InternalHandler extends Handler {

        private WeakReference<Activity> mContextWeakReference;



        public InternalHandler( Activity mContext) {
            mContextWeakReference = new WeakReference<Activity>(mContext);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != getView() && null != getView().getViewPager()) { //当fragment存在时
                int currentItem = getView().getViewPager().getCurrentItem() + 1;
                if (null != stringList) {
                    if (stringList.size() != 0 && getView().getViewPager().getAdapter() != null) {
                        //这里取余是为了当数据超过适配器的长度,时候回到第一页,避免异常. 其实也是会突然跳到第一页,只是不一般达不到这个值
                        getView().getViewPager().setCurrentItem(currentItem % getView().getViewPager().getAdapter().getCount(), true);
                    }
                }
                if (null != mHandler) {

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mHandler != null) {
                                mHandler.sendEmptyMessage(0);
                            }
                        }
                    }, 2000);
                }
            }

        }

    }

    /**
     * 触摸控制viewpager的切换
     *
     * @author Administrator
     */
    class ViewPagerTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (null != mHandler) {

                        mHandler.removeCallbacksAndMessages(null);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (null != mHandler) {

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mHandler != null) {
                                    mHandler.sendEmptyMessage(0);
                                }
                            }
                        }, 2000);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:// 加这个 防止 和下拉刷新冲突
                    if (null != mHandler) {

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mHandler != null) {
                                    mHandler.sendEmptyMessage(0);
                                }
                            }
                        }, 2000);
                    }
                    break;
            }
            return false;
        }
    }

    /**
     * 创建轮播图的view
     * @param position
     * @return
     */
    @Override
    public View ctreaImageView(int position) {
        if (null != getView() && getView().getmActivity() != null) {
            ImageView iv = (ImageView) View.inflate(getView().getmActivity(), R.layout.lunbotu, null);
            iv.setOnTouchListener(new ViewPagerTouchListener());
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // UtilMethod.Toast(iShouyeView.getmActivity(), "" + position);
                }
            });
            // 从服务器获取图片
            if (null != stringList) {
                String url = stringList.get(position).getUrl();
                if (!TextUtils.isEmpty(url)) {
                    Glide.with(getView().getmActivity()).load(url).into(iv);
                }
            }
            return iv;
        }
        return null;
    }



    @Override
    public void destroy() {


        //将handler关闭防止 内存泄漏
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null); // 把所有的消息和任务清空
            mHandler = null;
        }
    }
}