package app.nvgtor.com.leanrning.features.mdbook.book;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;

/**
 * Created by nvgtor on 2016/4/26.
 */
public class BookRvAdapter extends RecyclerView.Adapter<BookRvAdapter.ViewHolder>{

    private Context mContext;
    private int mBackground;
    private List<Book> mBooks = new ArrayList<Book>();
    private final TypedValue mTypedValue = new TypedValue();

    private static final int ANIMATED_ITEMS_COUNT = 4;

    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    public BookRvAdapter(Context context) {
        mContext = context;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        Book book = mBooks.get(position);
        holder.tvTitle.setText(book.getTitle());
        String desc = "作者: " + book.getAuthor()[0] + "\n副标题: " + book.getSubtitle()
                + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
        holder.tvDesc.setText(desc);
        Glide.with(holder.ivBook.getContext())
                .load(book.getImage())
                .fitCenter()
                .into(holder.ivBook);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public Book getBook(int pos) {
        return mBooks.get(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBook;
        public TextView tvTitle;
        public TextView tvDesc;

        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBook = (ImageView) itemView.findViewById(R.id.ivBook);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
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


    public void updateItems(List<Book> books, boolean animated) {
        animateItems = animated;
        lastAnimatedPosition = -1;
        mBooks.clear();
        mBooks.addAll(books);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mBooks.clear();
        notifyDataSetChanged();
    }
}
