package app.nvgtor.com.leanrning.features.mNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mNews.callback.ListNewsCallback;
import app.nvgtor.com.leanrning.features.mNews.model.News;
import app.nvgtor.com.leanrning.features.mdbook.widget.RecyclerItemClickListener;
import app.nvgtor.com.leanrning.utils.apiAsyc.AsyncHttpUtils;
import app.nvgtor.com.leanrning.utils.apiAsyc.HttpCallbackListenner;
import app.nvgtor.com.leanrning.utils.cache.ACache;
import app.nvgtor.com.leanrning.utils.netUtils.NetStates;
import okhttp3.Call;

/**
 * Created by nvgtor on 2016/3/28.
 */
public class NewsListFragment extends Fragment {
    public static final String BUNDLE_TITLE = "title";
    public static final String BUNDLE_NEWS = "news";
    public static final String BUNDLE_TYPE = "news_type";
    private String mTitle = "DefaultValue";
    private int type;
    private int page = 0;
    private List<News> newsList;
    private String[] mCacheKey = {"newsTJUyaowen", "newsTJUgonggao", "newsTJUshetuan",
                                    "newsTJUyuanxi", "newsTJUshidian"};

    private XRecyclerView mRecyclerView;
    private NewsListAdapter adapter;
    GoogleProgressBar mGoogleProgressBar;

    private ACache mCache;

    public static NewsListFragment newInstance(String title, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putInt(BUNDLE_TYPE, type);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, null, false);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.news_list_rv);
        mGoogleProgressBar = (GoogleProgressBar) view.findViewById(R.id.google_progress);
        mGoogleProgressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);

        newsList = new ArrayList<News>();
        adapter = new NewsListAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);

        mCache = ACache.get(getActivity());

        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
            type = arguments.getInt(BUNDLE_TYPE);
            initData(0);
        }

       mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
           @Override
           public void onRefresh() {
               if (NetStates.isNetworkAvailable(getActivity())){
                   refreshData(0);
               } else {
                   Toast.makeText(getActivity(), "当前没有网络连接！", Toast.LENGTH_SHORT).show();
                   mRecyclerView.refreshComplete();
               }
           }

           @Override
           public void onLoadMore() {
               if (NetStates.isNetworkAvailable(getActivity())){
                   loadMoreData(++page);
               } else {
                   Toast.makeText(getActivity(), "当前没有网络连接！", Toast.LENGTH_SHORT).show();
                   mRecyclerView.loadMoreComplete();
               }

           }
       });

        return view;
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener
            = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            News news = adapter.getNews(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("news", news);
            intent.putExtra("type", type);
            getActivity().startActivity(intent);
        }
    };

    public void saveCache(JSONArray jsonArray) {
        mCache.put(mCacheKey[type-1], jsonArray);
    }

    public void readCache(String cacheKey) {
        JSONArray jsonArray = mCache.getAsJSONArray(cacheKey);
        if (jsonArray == null) {
            Toast.makeText(getActivity(), "JSONArray cache is null ...",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<News>>() {}.getType();
            List<News> response = gson.fromJson(jsonArray.toString(), type);
            if (response != null && response.size() > 0){
                newsList.clear();
                newsList.addAll(response);
                adapter.updateList(newsList);
            } else {
                Toast.makeText(getActivity(), "缓存数据解析失败", Toast.LENGTH_SHORT).show();
            }
            mGoogleProgressBar.setVisibility(View.GONE);
        }
    }

    private void initData(int page) {
        getDataWithAsycHtttp(page);
        //getDataByOkHttp(page);
    }

    private void refreshData(int page){
        AsyncHttpUtils.PostNewsList(page, type, new HttpCallbackListenner() {
            @Override
            public void onFinish(JSONArray jsonArray) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<News>>() {}.getType();
                List<News> response = gson.fromJson(jsonArray.toString(), type);
                if (response != null && response.size() > 0){
                    newsList.clear();
                    newsList.addAll(response);
                    adapter.updateList(newsList);
                } else {
                    Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onErrer(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                mRecyclerView.refreshComplete();
            }
        });

    }

    private void loadMoreData(int page){
        AsyncHttpUtils.PostNewsList(page, type, new HttpCallbackListenner() {
            @Override
            public void onFinish(JSONArray jsonArray) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<News>>() {}.getType();
                List<News> response = gson.fromJson(jsonArray.toString(), type);
                if (response != null && response.size() > 0){
                    newsList.addAll(response);
                    adapter.updateList(newsList);
                    mRecyclerView.loadMoreComplete();
                } else {
                    mRecyclerView.noMoreLoading();
                }
            }

            @Override
            public void onErrer(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    private void getDataWithAsycHtttp(int page) {
        if (NetStates.isNetworkAvailable(getActivity())){
            AsyncHttpUtils.PostNewsList(page, type, new HttpCallbackListenner() {
                @Override
                public void onFinish(JSONArray jsonArray) {
                    saveCache(jsonArray);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<News>>() {}.getType();
                    List<News> response = gson.fromJson(jsonArray.toString(), type);
                    if (response != null && response.size() > 0){
                        newsList.clear();
                        newsList.addAll(response);
                        adapter.updateList(newsList);
                    } else {
                        Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                    mGoogleProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onErrer(String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    mRecyclerView.refreshComplete();
                    mGoogleProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            readCache(mCacheKey[type-1]);
            mGoogleProgressBar.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), "当前没有网络连接,读取缓存！", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataByOkHttp(int page) {
        String url = "http://push-mobile.twtapps.net/content/list";
        if (NetStates.isNetworkAvailable(getActivity())){
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("ctype", "news")
                    .addParams("page", String.valueOf(page))
                    .addParams("ntype", String.valueOf(type))
                    .addParams("platform", "android")
                    .addParams("version", String.valueOf(1.0))
                    .build()
            .execute(new ListNewsCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    mRecyclerView.refreshComplete();
                    mGoogleProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onResponse(List<News> response) {
                    Log.d("ListNewsCallback", response.toString());
                    if (response != null && response.size() > 0){
                        newsList.clear();
                        newsList.addAll(response);
                        adapter.updateList(newsList);
                    } else {
                        Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                    mGoogleProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            //readCahe(mCacheKey[type-1]);
            mGoogleProgressBar.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), "当前没有网络连接,读取缓存！", Toast.LENGTH_SHORT).show();
        }
    }
}

