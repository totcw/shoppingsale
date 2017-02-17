package com.betterda.shoppingsale.login;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.findpwd.FindPwdActivity;
import com.betterda.shoppingsale.home.MainActivity;
import com.betterda.shoppingsale.login.contract.LoginContract;
import com.betterda.shoppingsale.login.presenter.LoginPresenterImpl;
import com.betterda.shoppingsale.register.RegisterActivity;
import com.betterda.shoppingsale.utils.CacheUtils;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.welcome.WelcomeActivity;
import com.betterda.shoppingsale.widget.NormalTopBar;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.functions.Action1;

/**
 * 登录
 * Created by Administrator on 2016/12/20.
 */

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
    @BindView(R.id.topbar_login)
    NormalTopBar mTopbarLogin;
    @BindView(R.id.et_login_number)
    EditText mEtLoginNumber;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.iv_login_jizhu)
    ImageView mIvJizhu;
    private String account;


    @Override
    protected LoginContract.Presenter onLoadPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_login);

    }

    @Override
    public void init() {
        super.init();
        mTopbarLogin.setTitle("登录");
        mTopbarLogin.setBackVisibility(false);

        boolean remember = CacheUtils.getBoolean(getmActivity(), Constants.Cache.REMEMBER, false);
        if (remember) {
            account = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
            String pwd = CacheUtils.getString(getmActivity(), account +Constants.Cache.PWD, "");
            if (!TextUtils.isEmpty(account)) {
                int indexOf = account.lastIndexOf("s");
                account = account.substring(0, indexOf);
            }
            mEtLoginNumber.setText(account);
            mEtLoginPwd.setText(pwd);
            mIvJizhu.setSelected(true);
        }
        initRxBus();
    }

    @OnClick({R.id.tv_login_pwd, R.id.bar_back, R.id.btn_login, R.id.relative_login_register,R.id.linear_login_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_pwd://忘记密码
                UiUtils.startIntent(getmActivity(), FindPwdActivity.class);
                break;
            case R.id.btn_login:
               // UiUtils.startIntent(getmActivity(), MainActivity.class);
                NetworkUtils.isNetWork(getmActivity(), mTopbarLogin, new NetworkUtils.SetDataInterface() {
                    @Override
                    public void getDataApi() {
                        getPresenter().login();
                    }
                });
                break;
            case R.id.relative_login_register:
                UiUtils.startIntent(getmActivity(), RegisterActivity.class);
                break;
            case R.id.linear_login_pwd:
                mIvJizhu.setSelected(!mIvJizhu.isSelected());
                break;

            case R.id.bar_back:
                back();
                break;
        }
    }




    private void initRxBus() {
        getRxManager().on(LoginActivity.class.getSimpleName(), new Action1<Object>() {
            @Override
            public void call(Object o) {

                finish();
            }
        });
    }



    @Override
    public String getAccount() {
        return mEtLoginNumber.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return mEtLoginPwd.getText().toString().trim();
    }

    /**
     * 是否记住密码
     * @return
     */
    public boolean isSelectJizhu() {
        return mIvJizhu.isSelected();
    }

    @Override
    public EditText getTvLogin() {
        return mEtLoginNumber;
    }

    @Override
    public EditText getTvPwd() {
        return mEtLoginPwd;
    }

    @Override
    public ImageView getImageView() {
        return mIvJizhu;
    }

    @Override
    public void onBackPressed() {
        //发送广播 关闭欢迎页面
        mRxManager.post(WelcomeActivity.class.getSimpleName(),"finish");
        super.onBackPressed();

    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void setAlias(String alias) {

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";

                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    break;
            }

        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:

                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    break;
            }
        }
    };

}
