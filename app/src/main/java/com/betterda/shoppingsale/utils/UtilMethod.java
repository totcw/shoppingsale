package com.betterda.shoppingsale.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.betterda.mylibrary.LoadingPager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lyf
 */
public class UtilMethod {

    /**
     * get mobile device id
     *
     * @return
     */
    public static String getUdId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * get app version name and version code
     */
    public static String getAppVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName + "-" + versionCode;
    }

    /**
     * get app version name
     */
    public static String getAppVersionName(Context context) {
        String versionName = "0.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * get app version code
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
            return versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     * get mobile model
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * get mobile phone number and replace china number
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tel = phoneMgr.getLine1Number();
        if (tel != null) {
            tel = tel.replace("+86", "").trim();
        }
        return tel;
    }


    /**
     * close the soft keyboard
     *
     * @param context
     */
    public static void closeKeyBox(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        final View v = ((Activity) context).getWindow().peekDecorView();
        imm.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static int getWeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        return screenWidth;

    }

    /**
     * 获取屏幕的高度
     *
     * @param activity
     * @return
     */
    public static int getHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.heightPixels;
        return screenWidth;

    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int statusHeight(Activity activity) {
        //获取状态栏高度
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
   // 获取是否存在NavigationBar：

    public static boolean checkDeviceHasNavigationBar(Context context) {

        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }


        return hasNavigationBar;
    }

    /**
     * 获取底部导航的高度
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {

        if (!checkDeviceHasNavigationBar(context)) { //如果没显示
            return 0;
        }

        final boolean isMeiZu = Build.MANUFACTURER.equals("Meizu");

        final boolean autoHideSmartBar = Settings.System.getInt(context.getContentResolver(),
                "mz_smartbar_auto_hide", 0) == 1;

        if (isMeiZu) {
            if (autoHideSmartBar) {
                return 0;
            } else {
                try {
                    Class c = Class.forName("com.android.internal.R$dimen");
                    Object obj = c.newInstance();
                    Field field = c.getField("mz_action_button_min_height");
                    int height = Integer.parseInt(field.get(obj).toString());
                    return context.getResources().getDimensionPixelSize(height);
                } catch (Throwable e) { // 不自动隐藏smartbar同时又没有smartbar高度字段供访问，取系统navigationbar的高度
                    return getNormalNavigationBarHeight(context);
                }
            }
        } else {
            return getNormalNavigationBarHeight(context);
        }
    }

    protected static int getNormalNavigationBarHeight(final Context ctx) {
        try {
            final Resources res = ctx.getResources();
            int rid = res.getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid > 0) {
                boolean flag = res.getBoolean(rid);
                if (flag) {
                    int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        return res.getDimensionPixelSize(resourceId);
                    }
                }
            }
        } catch (Throwable e) {
        }
        return 0;
    }



    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }





    /**
     * 获取当前的时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrdentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 获取当前的时间格式 yyyy-MM-dd
     */
    public static String getCurrdentTime2() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        String time = format.format(date);
        return time;
    }



    /**
     * float格式化保留2位
     *
     * @param money
     */
    public static String FloatFormat(float money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        return p;
    }

    /**
     * float格式化保留1位
     *
     * @param money
     */
    public static String FloatFormat1(float money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        return p;
    }


    /**
     * 去掉付款金额的小数点转化为分
     *
     * @param money
     * @return
     */
    public static String deletePoint(String money) {

        //去掉点
        int index = money.indexOf(".");
        String money1 = money.substring(0, index);
        String money2 = money.substring(index + 1);

        //先判断整数部分是不是0
        if ("0".equals(money1) || "".equals(money1)) {
            //如果整数部分为0,就判断小数部分是不是0开头
            if (!TextUtils.isEmpty(money2)) {


                if (money2.startsWith("0")) {
                    //如果是以0开头就取后一位数
                    int indexF = money2.indexOf("0");
                    int length = money2.length();
                    money2 = money2.substring(indexF + 1, length);
                }

                //如果不为0且长度是1就补0
                if (!"0".equals(money2) && money2.length() == 1) {
                    money2 = money2 + "0";
                }
            }
            //整数部分为0就取小数部分的值
            money = money2;
        } else {
            //如果整数部分不是0直接拼接即可
            money = money1 + money2;
        }
        return money;
    }


    /**
     * 判断数据是否为空
     * @param listLocation
     * @param loadingPagerLocation
     */
    public static void hideOrEmpty(List listLocation, LoadingPager loadingPagerLocation) {
        if (listLocation != null&&loadingPagerLocation!=null) {
            if (listLocation.size() > 0) {
                loadingPagerLocation.hide();
            } else {
                loadingPagerLocation.setEmptyVisable();
            }
        } else {
            loadingPagerLocation.setEmptyVisable();
        }
    }





}
