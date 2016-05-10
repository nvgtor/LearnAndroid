package app.nvgtor.com.leanrning.utils.apiAsyc;

/**
 * Created by nvgtor on 2015/6/11.
 */
public interface HttpCallbackDetailListenner {

    void onFinish(String content);

    void onErrer(String error);
}
