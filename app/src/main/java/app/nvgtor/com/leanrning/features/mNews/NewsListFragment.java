package app.nvgtor.com.leanrning.features.mNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private List<News> newsList;

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, null, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.news_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                onItemClickListener));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
            type = arguments.getInt(BUNDLE_TYPE);
            initData();
        }


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

    private void initData() {
        AsyncHttpPost.PostNewsList(0, type, new HttpCallbackListenner() {
            @Override
            public void onFinish(List<News> response) {
                newsList = response;
                adapter = new NewsListAdapter(getActivity(), newsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onErrer(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
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

