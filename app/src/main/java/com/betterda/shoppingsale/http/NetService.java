package com.betterda.shoppingsale.http;

import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.shouye.model.LunBoTu;
import com.betterda.shoppingsale.utils.Constants;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 封装retrofit请求需要的接口
 * Created by Administrator on 2016/7/29.
 */
public interface NetService {
    /**
     * 获取广告栏
     *
     * @return
     */

    @GET(Constants.Url.URL_LUNBO)
    Observable<BaseCallModel<LunBoTu>> getAdvertisement();

}
