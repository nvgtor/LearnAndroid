package app.nvgtor.com.leanrning.login.view;

/**
 * Created by nvgtor on 2016/4/8.
 */
public interface ILoginView {
    void clearEditText();

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    String getUsername();

    String getPassword();

    void loginSuccess();

}
