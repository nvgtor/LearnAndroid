package app.nvgtor.com.leanrning.features.mNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mNews.model.News;
import app.nvgtor.com.leanrning.features.mdbook.widget.RecyclerItemClickListener;
import app.nvgtor.com.leanrning.utils.api.AsyncHttpPost;
import app.nvgtor.com.leanrning.utils.api.HttpCallbackListenner;

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

    private XRecyclerView mRecyclerView;
    private NewsListAdapter adapter;
    GoogleProgressBar mGoogleProgressBar;

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

        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
            type = arguments.getInt(BUNDLE_TYPE);
            initData(0);
        }

       mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
           @Override
           public void onRefresh() {
               refreshData(0);
           }

           @Override
           public void onLoadMore() {
               loadMoreData(++page);
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

    private void initData(int page) {
        AsyncHttpPost.PostNewsList(page, type, new HttpCallbackListenner() {
            @Override
            public void onFinish(List<News> response) {
                if (response != null && response.size() > 0){
                    newsList.clear();
                    newsList.addAll(response);
                    adapter.updateList(newsList);
                } else {
                    Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
                mRecyclerView.refreshComplete();
                mGoogleProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onErrer(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                mRecyclerView.refreshComplete();
                mGoogleProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void refreshData(int page){
        AsyncHttpPost.PostNewsList(page, type, new HttpCallbackListenner() {
            @Override
            public void onFinish(List<News> response) {
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
        AsyncHttpPost.PostNewsList(page, type, new HttpCallbackListenner() {
            @Override
            public void onFinish(List<News> response) {
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

    public static NewsListFragment newInstance(String title, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putInt(BUNDLE_TYPE, type);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

