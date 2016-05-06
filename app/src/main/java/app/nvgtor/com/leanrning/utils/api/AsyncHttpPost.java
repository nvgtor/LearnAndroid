package app.nvgtor.com.leanrning.utils.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import app.nvgtor.com.leanrning.features.mNews.model.News;
import app.nvgtor.com.leanrning.features.mNews.model.NewsDetail;
import cz.msebera.android.httpclient.Header;

/**
 * Created by nvgtor on 2015/5/27.
 */
public class AsyncHttpPost {

    private static String url = "http://push-mobile.twtapps.net/content/list";
    private static final String urlDetail = "http://push-mobile.twtapps.net/content/detail";

    public static void PostNewsList(int page,
                                    int ntype,
                                    final HttpCallbackListenner listenner) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("ctype", "news");
            params.put("page", page);
            params.put("ntype", ntype);
            params.put("platform", "android");
            params.put("version", 1.0);

            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[]
                        headers, byte[] responseBody) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        Log.d("JsonArray", jsonArray.toString());
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<News>>() {}.getType();
                        List<News> newsLists = gson.fromJson(jsonArray.toString(), type);
                        if (listenner != null) {
                            listenner.onFinish(newsLists);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    listenner.onErrer(responseBody.toString());
                }
            });

        }catch (Exception e) {
            if (listenner != null) {
                listenner.onErrer(e.toString());
            }
        }
    }

    public static void PostNewsDetail(String index,
                                      final HttpCallbackDetailListenner listenner) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("ctype", "news");
            params.put("index", index);
            params.put("platform", "android");
            params.put("version", 1.0);

            client.post(urlDetail, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("postJson", responseBody.toString());
                    if (listenner != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(responseBody));
                            Gson gson = new Gson();
                            NewsDetail newsDetail = gson.fromJson(jsonObject.toString(), NewsDetail.class);
                            listenner.onFinish(newsDetail.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    listenner.onErrer(responseBody.toString());
                }
            });
        }catch (Exception e) {
            if (listenner != null) {
                listenner.onErrer(e.toString());
            }
        }
    }
}
