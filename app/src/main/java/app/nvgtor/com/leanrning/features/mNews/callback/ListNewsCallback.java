package app.nvgtor.com.leanrning.features.mNews.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import app.nvgtor.com.leanrning.features.mNews.model.News;
import okhttp3.Response;

/**
 * Created by nvgtor on 2016/5/10.
 */
public abstract class ListNewsCallback extends Callback<List<News>> {
    @Override
    public List<News> parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        List<News> newsList = new Gson().fromJson(string, List.class);
        return newsList;
    }
}
