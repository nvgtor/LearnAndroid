package app.nvgtor.com.leanrning.utils.apiAsyc;

import org.json.JSONArray;

/**
 * Created by nvgtor on 2015/5/25.
 */
public interface HttpCallbackListenner {

    void onFinish(JSONArray jsonArray);

    void onErrer(String error);
}
