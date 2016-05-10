package app.nvgtor.com.leanrning.features.mdbook.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mdbook.BookDetailActivity;
import app.nvgtor.com.leanrning.features.mdbook.widget.RecyclerItemClickListener;
import app.nvgtor.com.leanrning.utils.cache.ACache;
import app.nvgtor.com.leanrning.utils.netUtils.NetStates;
import okhttp3.Call;

/**
 * Created by nvgtor on 2016/4/25.
 */
public class BooksFragment extends Fragment {

    private static final String CACHE_KEY = "mBooksCache";

    private XRecyclerView mRecyclerView;
    private GoogleProgressBar mProgressBar;
    private FloatingActionButton mFabButton;

    private BookRvAdapter mAdapter;
    private static final int ANIM_DURATION_FAB = 400;

    private ACache mCache;

    List<Book> allBookList = new ArrayList<>();
    private String keyworlds = "";
    private int total;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, null);

        mCache = ACache.get(getActivity());

        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);
        mProgressBar = (GoogleProgressBar) view.findViewById(R.id.google_progress);
        mFabButton = (FloatingActionButton) view.findViewById(R.id.fab_normal);

        //RecyclerView 优化
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new BookRvAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);

        setUpFAB(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFabButton.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        if (NetStates.isNetworkAvailable(getActivity())){
            doSearch(getString(R.string.default_search_keyword), 0, true, false);
        } else {
            Toast.makeText(getActivity(), "没有连接网络,读取缓存...", Toast.LENGTH_SHORT).show();
            readCache(CACHE_KEY);
            mProgressBar.setVisibility(View.GONE);
        }

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            int start = 0;

            @Override
            public void onRefresh() {
                if (keyworlds != null && !keyworlds.equals(""))
                    doSearch(keyworlds, 0, false, false);
                else
                    doSearch("龙族", 0, false, false);
            }

            @Override
            public void onLoadMore() {
                if (mAdapter.getItemCount() < total){
                    start += 10;
                    if (keyworlds != null && !keyworlds.equals(""))
                        doSearch(keyworlds, start, false, true);
                    else
                        doSearch("龙族", start, false, true);
                } else {
                    mRecyclerView.noMoreLoading();
                }
            }
        });

    }

    class BookList implements Serializable{
        private static final long serialVersionUID = 1L;

        public List<Book> books;

        public BookList(List<Book> books) {
            this.books = books;
        }

        public List<Book> getBooks() {
            return books;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }
    }

    public void saveCache(String books) {
        mCache.put(CACHE_KEY, books);
    }


    public void readCache(String cacheKey) {
        String bookString = mCache.getAsString(CACHE_KEY);
        if (bookString != null){
            JSONObject jsonObject = null;
            Gson gson = new Gson();
            try {
                jsonObject = new JSONObject(bookString);
                JSONArray jsonArray = jsonObject.getJSONArray("books");
                List<Book> bookList = gson.fromJson(jsonArray.toString(),
                        new TypeToken<List<Book>>(){}.getType());
                mProgressBar.setVisibility(View.GONE);
                startFABAnimation();
                mAdapter.updateItems(bookList, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "cache is null ...", Toast.LENGTH_SHORT).show();
        }
    }

    private void doSearch(String keyword, int start, boolean readyCache, boolean flag) {
        if (readyCache){
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

        //mAdapter.clearItems();

        searchBooksOkHttp(keyword, start, readyCache, flag);

        /*Book.searchBooks(keyword, new Book.IBookResponse<List<Book>>() {
            @Override
            public void onData(List<Book> books) {
                if (books != null && books.size() > 0){
                    //saveCache(books);
                    startFABAnimation();
                    mAdapter.updateItems(books, true);
                } else {
                    Toast.makeText(getActivity(), "没有搜索到结果！", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void searchBooksOkHttp(String keyword, final int start, final boolean readyCache, final boolean flag) {
        String url = "https://api.douban.com/v2/book/search";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("q", keyword)
                .addParams("start", String.valueOf(start))
                .addParams("count", String.valueOf(10))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d("hyOkHttpGetError", e.toString());
                        if (flag){
                            mRecyclerView.loadMoreComplete();
                        } else {
                            mRecyclerView.refreshComplete();
                        }
                        if (readyCache){
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("hyOkHttpGet", response);
                        if (readyCache){
                            saveCache(response);
                            mProgressBar.setVisibility(View.GONE);
                        }
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("books");
                            total = jsonObject.getInt("total");
                            List<Book> bookList = gson.fromJson(jsonArray.toString(),
                                    new TypeToken<List<Book>>(){}.getType());
                            startFABAnimation();
                            allBookList.addAll(bookList);
                            if (flag){
                                mAdapter.updateItems(allBookList, true);
                            } else {
                                mAdapter.updateItems(bookList, true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (flag){
                            mRecyclerView.loadMoreComplete();
                        } else {
                            mRecyclerView.refreshComplete();
                        }
                    }
                });
    }

    private void startFABAnimation() {
        mFabButton.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(500)
                .setDuration(ANIM_DURATION_FAB)
                .start();
    }

    private void setUpFAB(View view) {
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.search)
                        //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    doSearch(input.toString(), 20, false, false);
                                    keyworlds = input.toString();
                                }
                            }
                        }).show();
            }
        });
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener
            = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Book book = mAdapter.getBook(position);
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra("book", book);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
