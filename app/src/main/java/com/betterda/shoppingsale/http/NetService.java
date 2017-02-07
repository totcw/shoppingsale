package com.betterda.shoppingsale.http;

import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.javabean.MeassageContent;
import com.betterda.shoppingsale.javabean.MeassageType;
import com.betterda.shoppingsale.javabean.Stock;
import com.betterda.shoppingsale.javabean.UserInfo;
import com.betterda.shoppingsale.javabean.OrderAll;

import com.betterda.shoppingsale.shouye.model.LunBoTu;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.wallet.model.BankCard;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 封装retrofit请求需要的接口
 * Created by Administrator on 2016/7/29.
 */
public interface NetService {

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_LOGIN)
    Observable<BaseCallModel<UserInfo>> getLogin(@Field("account") String account,
                                                 @Field("password") String password);

    /**
     * 三方登录
     *
     * @param account
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_LOGIN)
    Observable<BaseCallModel<UserInfo>> getLoginThree(@Field("account") String account, @Field("type") String type);


    /**
     * 忘记密码
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_PWD_UPDATE)
    Observable<BaseCallModel<String>> getPwdUpdate(@Field("account") String account,
                                                   @Field("password") String password);


    /**
     * 获取广告栏
     *
     * @return
     */

    @GET(Constants.Url.URL_LUNBO)
    Observable<BaseCallModel<LunBoTu>> getAdvertisement();

    /**
     * 银行卡删除
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_LUNBO)
    Observable<BaseCallModel<String>> getBandDelete(@Field("account") String account,
                                                    @Field("token") String token,
                                                    @Field("id") String id);


    /**
     * 银行卡添加
     *
     * @param account
     * @param token
     * @param truename     持卡人姓名
     * @param identitycard 身份证
     * @param bank         所属银行
     * @param cardnum      卡号
     * @param number       预留手机号
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_LUNBO)
    Observable<BaseCallModel<String>> getBandAdd(@Field("account") String account,
                                                 @Field("token") String token,
                                                 @Field("cardName") String truename,
                                                 @Field("identityCard") String identitycard,
                                                 @Field("bank") String bank,
                                                 @Field("cardNum") String cardnum,
                                                 @Field("number") String number
    );

    /**
     * 银行卡查询
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_LUNBO)
    Observable<BaseCallModel<List<BankCard>>> getBandGet(@Field("account") String account,

                                                         @Field("token") String token
    );


    /**
     * 获取订单列表
     *
     * @param account
     * @param token
     * @param orderStatus 订单状态
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_ORDER)
    Observable<BaseCallModel<List<OrderAll>>> getOrder(@Field("account") String account,
                                                       @Field("token") String token,
                                                       @Field("orderStatus") String orderStatus,
                                                       @Field("pageNo") String pageNo,
                                                       @Field("pageSize") String pageSize
    );

    /**
     * 获取订单详情
     *
     * @param account
     * @param token
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_ORDERDETAIL)
    Observable<BaseCallModel<OrderAll>> getOrderDetail(@Field("account") String account,
                                                       @Field("token") String token,
                                                       @Field("orderNum") String orderId
    );

    /**
     * 立即发货
     *
     * @param account
     * @param token
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_ORDERDETAIL)
    Observable<BaseCallModel<String>> publish(@Field("account") String account,
                                              @Field("token") String token,
                                              @Field("orderNum") String orderId
    );

    /**
     * 确认自提
     *
     * @param account
     * @param token
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_COMFIRMZITI)
    Observable<BaseCallModel<String>> comfirmZiti(@Field("account") String account,
                                                  @Field("token") String token,
                                                  @Field("barCode") String barCode,
                                                  @Field("orderNum") String orderId
    );

    /**
     * 根据自提码获取订单号
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_ORDERNUM)
    Observable<BaseCallModel<String>> comfirmZiti(@Field("account") String account,
                                                  @Field("token") String token,
                                                  @Field("barCode") String barCode
    );

    /**
     * 发货
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_ORDERSEND)
    Observable<BaseCallModel<String>> sendOrder(@Field("account") String account,
                                                @Field("token") String token,
                                                @Field("orderNum") String barCode
    );

    /**
     * 扫码入库
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_SCANSTOCK)
    Observable<BaseCallModel<String>> scanStock(@Field("account") String account,
                                                @Field("token") String token,
                                                @Field("jsonData") String jsonData
    );

    /**
     * 商品库存
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_STOCK)
    Observable<BaseCallModel<List<Stock>>> stock(@Field("account") String account,
                                                 @Field("token") String token
    );

    /**
     * 获取消息列表
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_MEASSAGELIST)
    Observable<BaseCallModel<List<MeassageContent>>> getMeassageList(@Field("account") String account,
                                                                     @Field("token") String token,
                                                                     @Field("msgType") String msgType,
                                                                     @Field("pageNo") String pageNo,
                                                                     @Field("pageSize") String pageSize
    );

    /**
     * 获取消息类型
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_MEASSAGETYPE)
    Observable<BaseCallModel<List<MeassageType>>> getMeassageType(@Field("account") String account,
                                                                  @Field("token") String token
    );


}
