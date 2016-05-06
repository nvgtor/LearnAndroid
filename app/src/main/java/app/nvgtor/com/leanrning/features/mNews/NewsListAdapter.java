package app.nvgtor.com.leanrning.features.mNews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mNews.model.News;

/**
 * Created by nvgtor on 2016/5/6.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>{

    private Context mContext;
    private List<News> newsList;

    public NewsListAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    public News getNews(int pos) {
        return newsList.get(pos);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
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
}
