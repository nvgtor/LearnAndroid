package app.nvgtor.com.leanrning.login.presenter;

import android.os.Handler;

import app.nvgtor.com.leanrning.login.model.User;
import app.nvgtor.com.leanrning.login.view.ILoginView;

/**
 * Created by nvgtor on 2016/4/8.
 */
public class LoginPresenter implements ILoginPresenter{
    private ILoginView mLoginView;
    private User mUser;

    public LoginPresenter(ILoginView mLoginView) {
        this.mLoginView = mLoginView;
        initUser();
    }

    private void initUser(){
        mUser = new User(mLoginView.getUsername(), mLoginView.getPassword());
    }

    @Override
    public void doLogin(String username, String password) {
        mLoginView.showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mUser.checkUserValidity(mLoginView.getUsername(), mLoginView.getPassword())){
                    mLoginView.loginSuccess();
                }else {
                    mLoginView.setPasswordError();
                }
            }
        },10);

    }

    @Override
    public void clear() {
        mLoginView.clearEditText();
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }
}
