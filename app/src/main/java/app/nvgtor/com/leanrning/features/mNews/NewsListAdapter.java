package app.nvgtor.com.leanrning.features.mNews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mNews.model.News;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;

/**
 * Created by nvgtor on 2016/5/6.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>{

    private Context mContext;
    private List<News> newsList;

    private boolean animateItems = true;
    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 5;

    public NewsListAdapter(Context mContext) {
        this.mContext = mContext;
        newsList = new ArrayList<>();
    }

    public News getNews(int pos) {
        return newsList.get(pos);
    }

    public void updateList(List<News> newsList){
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        News news = newsList.get(position);
        holder.bindData(news);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;

        public int position;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.news_title);
            tvDate = (TextView) itemView.findViewById(R.id.news_time);
        }

        public void bindData(News news){
            tvTitle.setText(news.getSubject());
            tvDate.setText(news.getAddat());
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(PxToDpUtil.getWindowHeightPx(mContext));
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }
}
