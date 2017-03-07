package com.betterda.shoppingsale.utils;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String CACHE_FILE_NAME = "betterdaShoppingsale"; //缓存目录
    public static final String PHOTOPATH= Environment.getExternalStorageDirectory().getPath()+"/shoppingsale/photo/";//存图片的路径
    public static final Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.png")); //保存拍照照片的uri
    public static final int PAGESIZE2 = 10;//分页加载的个数
    public static final String PAGESIZE = PAGESIZE2+"";//分页加载的个数
    public static String PHOTONAME="photo"; //存放照片的名字
    public static final int PHOTOZOOM = 1;// 相机选取
    public static final int PHOTOHRAPH = 2;// 相机拍照
    public static final String IMAGE_UNSPECIFIED = "image/*";

    public static class Url {

        public  final static String URL="http://192.168.1.145:8080/WinePIN/";
        public static final String URL_REGISTER = "appAPI.do?api/merchant/register";
        public static final String URL_LOGIN = "appAPI.do?api/merchant/login";
        public static final String URL_PWD_UPDATE = "appAPI.do?api/account/update";
        public static final String URL_LUNBO = URL+"appAPI.do?api/indeximages/get";//轮播广告
        public static final String URL_ADD_BANK = "appAPI.do?api/account/bank/add";//添加银行卡
        public static final String URL_GET_BANK = "appAPI.do?api/account/bank/get";//获取银行卡列表
        public static final String URL_DEL_BANK = "appAPI.do?api/account/bank/del";//删除银行卡
        public static final String URL_GET_ORDER = "appAPI.do?api/merchant/order/get";//获取订单
        public static final String URL_ORDERSEND = "appAPI.do?api/merchant/deliver";//发货
        public static final String URL_GET_ORDERDETAIL = "appAPI.do?api/merchant/orderDetail/get";//订单详情
        public static final String URL_GET_COMFIRMZITI = "appAPI.do?api/order/product/provide";//确认自提
        public static final String URL_GET_ORDERNUM = "appAPI.do?api/barCode/scan";//根据自提码获取订单号
        public static final String URL_GET_ORDERLIST = "appAPI.do?api/barOrders/get";//自提列表
        public static final String URL_SCANSTOCK = "appAPI.do?api/merchant/inventory/add";//扫码入库
        public static final String URL_STOCK = "appAPI.do?api/merchant/inventory/get";//商品库存
        public static final String URL_GET_MEASSAGELIST = "appAPI.do?api/messageList/get";//获取消息列表
        public static final String URL_GET_MEASSAGETYPE = "appAPI.do?api/messageType/get";//获取消息类型
        public static final String URL_UPLOAD = "appAPI.do?api/merchant/android/img/upload";//上传图片
        public static final String URL_GET_FANXIANMONEY = "appAPI.do?api/account/recommend/mype/get";//获取推荐返现的金额
        public static final String URL_TUIJIAN = "appAPI.do?api/account/code/get";//立即推荐
        public static final String URL_TUIJIAN_MINGXI = "appAPI.do?api/account/recommend/detail/get";//推荐返现明细
        public static final String URL_GET_WALLET = "appAPI.do?api/account/wallet/get";//我的钱包
        public static final String URL_GET_WALLETMINGXI = "appAPI.do?api/account/wallet/detail/get";//钱包的交易明细
        public static final String URL_GET_CASH = "appAPI.do?api/account/cash/withdraw";//提现
        public static final String URL_BATCHLIST = "appAPI.do?api/merchant/batchList/get";//获取入库记录
        public static final String URL_BATCHDETAIL = "appAPI.do?api/merchant/batchDetail/get";//获取配货详情

    }

    public class WeiXin {
        public static final String APP_ID ="";
    }

    public class Cache {
        public static final String ACCOUNT = "account";
        public static final String PWD = "password";
        public static final String TOKEN = "token";
        public static final String REMEMBER = "remember";//是否记住密码
    }
}
