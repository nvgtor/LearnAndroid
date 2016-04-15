package app.nvgtor.com.leanrning.login.presenter;

/**
 * Created by nvgtor on 2016/4/8.
 */
public interface ILoginPresenter {
    void doLogin(String username, String password);

    void clear();

    void onDestroy();
}
