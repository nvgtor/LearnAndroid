package app.nvgtor.com.leanrning.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.customViews.ProgressView.CircleProgressView;
import app.nvgtor.com.leanrning.customViews.ProgressView.NumberProgress;
import app.nvgtor.com.leanrning.login.presenter.ILoginPresenter;
import app.nvgtor.com.leanrning.login.presenter.LoginPresenter;
import app.nvgtor.com.leanrning.view.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nvgtor on 2016/4/8.
 *
 */
public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    protected ILoginPresenter mLoginPresenter;

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_passwrod)
    EditText etPasswrod;
    @Bind(R.id.bt_enter)
    Button btEnter;
    @Bind(R.id.bt_clear)
    Button btClear;
    @Bind(R.id.progress)
    NumberProgress progress;

    int current = 0;
    int current1 = 0;

    @Bind(R.id.my_circle_progress)
    CircleProgressView myCircleProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        btEnter.setOnClickListener(this);
        btClear.setOnClickListener(this);
    }

    @Override
    public void clearEditText() {
        etUsername.setText("");
        etPasswrod.setText("");
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        etUsername.setError("username error");
    }

    @Override
    public void setPasswordError() {
        etPasswrod.setError("password error");
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPasswrod.getText().toString();
    }

    @Override
    public void loginSuccess() {
        Snackbar.make(btEnter, "login...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                current1++;
                if (current1 > 100) {
                    handler1.removeCallbacks(this);
                    toHomeActivity();
                } else {
                    myCircleProgress.setCurrentProgress(current1);
                    handler1.postDelayed(this, 80);
                }
            }
        }, 0);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                current++;
                if (current > 100) {
                    handler.removeCallbacks(this);
                } else {
                    progress.setCurrentProgress(current);
                    handler.postDelayed(this, 80);
                }
            }
        }, 0);
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_clear:
                mLoginPresenter.clear();
                break;
            case R.id.bt_enter:
                mLoginPresenter.doLogin(etUsername.getText().toString(),
                        etPasswrod.getText().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }
}
