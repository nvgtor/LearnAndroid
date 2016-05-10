package app.nvgtor.com.leanrning.features.mNews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mNews.model.News;
import app.nvgtor.com.leanrning.utils.apiAsyc.AsyncHttpUtils;
import app.nvgtor.com.leanrning.utils.apiAsyc.HttpCallbackDetailListenner;
import app.nvgtor.com.leanrning.utils.netUtils.NetStates;

/**
 * Created by nvgtor on 2016/5/6.
 */
public class NewsDetailActivity extends AppCompatActivity {

    private WebView mWebView;
    private News news;
    private String index;
    private int type = -1;
    private int[] imgIds = {R.drawable.img_02, R.drawable.img_03,R.drawable.img_04,
            R.drawable.img_05,R.drawable.img_06};

    private GoogleProgressBar mGoogleProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mWebView = (WebView) findViewById(R.id.news_detail_webv);
        mGoogleProgressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        mGoogleProgressBar.setVisibility(View.VISIBLE);

        news = (News) getIntent().getSerializableExtra("news");
        index = news.getIndex();
        type = getIntent().getIntExtra("type", -1);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(news.getSubject());

        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        if (type >= 0){
            Glide.with(ivImage.getContext())
                    .load(imgIds[type-1])
                    .fitCenter()
                    .into(ivImage);
        }

        if (NetStates.isNetworkAvailable(this)){
            AsyncHttpUtils.PostNewsDetail(index, new HttpCallbackDetailListenner() {
                @Override
                public void onFinish(String content) {
                    mWebView.loadData(content, "text/html; charset=UTF-8", null);
                    mGoogleProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onErrer(String error) {
                    Toast.makeText(NewsDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                    mGoogleProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            mGoogleProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, "当前没有网络连接！", Toast.LENGTH_SHORT).show();
        }
    }
}
