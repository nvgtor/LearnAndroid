package app.nvgtor.com.leanrning.utils.api;

import java.util.List;

import app.nvgtor.com.leanrning.features.mNews.model.News;

/**
 * Created by nvgtor on 2015/5/25.
 */
public interface HttpCallbackListenner {

    void onFinish(List<News> response);

    void onErrer(String error);
}
