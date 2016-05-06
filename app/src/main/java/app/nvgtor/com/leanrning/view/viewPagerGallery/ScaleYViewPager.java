package app.nvgtor.com.leanrning.view.viewPagerGallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;

/**
 * Created by nvgtor on 2016/4/18.
 */
public class ScaleYViewPager extends ViewPager {

    private int mPgerMargin = -350;
    private int mPaddingLeft = 130;
    private int mPaddingRight = 130;
    private int mPageLimit = 3;

    private List<View> mList;
    private DefaultViewPagerAdapter mAdapter;
    private ViewPagerOnClickListener viewPagerOnClickListener;

    private int mDefaultTopMargin = 16;
    private int mDefaultBottomMargin = 16;

    public interface ViewPagerOnClickListener{
        void onPageClickListener(int position);
    }

    public void setViewPagerOnClickListener(ViewPagerOnClickListener listener){
        viewPagerOnClickListener = listener;
    }

    public ScaleYViewPager(Context context) {
        this(context, null);
    }

    public ScaleYViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ScaleYViewPager);
        mPageLimit = a.getInteger(
                R.styleable.ScaleYViewPager_offscreenPageLimit, mPageLimit);
        mPgerMargin = a.getInteger(R.styleable.ScaleYViewPager_pagerMargin,
                mPgerMargin);
        mPaddingLeft = a.getInteger(R.styleable.ScaleYViewPager_paddingLeft,
                mPaddingLeft);
        mPaddingRight = a.getInteger(R.styleable.ScaleYViewPager_paddingRight,
                mPaddingRight);
        a.recycle();

        setOffscreenPageLimit(mPageLimit);
        setPageMargin(mPgerMargin);
        setPageTransformer(true, new ScaleYPageTransformer());

        mList = new ArrayList<View>();
        mAdapter = new DefaultViewPagerAdapter(mList);
        setAdapter(mAdapter);
    }

    /**
     * set the distance in the center view of the ViewPager
     * @param pagerDis
     */
    public void setScaleYPagerMargin(int pagerDis) {
        setPageMargin(mPgerMargin);
        mAdapter.notifyDataSetChanged();
    }

    public int getScaleYPagerMargin(){
        return mPgerMargin;
    }

    public int getmPaddingLeft() {
        return mPaddingLeft;
    }

    public void setmPaddingLeft(int mPaddingLeft) {
        this.mPaddingLeft = mPaddingLeft;
    }

    public int getmPaddingRight() {
        return mPaddingRight;
    }

    public void setmPaddingRight(int mPaddingRight) {
        this.mPaddingRight = mPaddingRight;
    }

    /**
     * set the number of view cache,better > 3
     * @param pagerDis
     */
    public void setScaleYOffscreenPageLimit(int pagerDis) {
        if (pagerDis < 3) {
            throw new IllegalArgumentException(
                    "the offscreen page limit must >3");
        }
        setOffscreenPageLimit(pagerDis);
    }

    /**
     * add some image resource to the list
     * @param list
     */
    public void setImgResList(List<View> list) {
        if (list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("the list is illegal!");
        }
    }

    /**
     * viewpager adapter
     */
    private class DefaultViewPagerAdapter extends PagerAdapter {

        private List<View> mList;

        public DefaultViewPagerAdapter(List<View> list) {
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mList.get(position);
            view.setPadding(mPaddingLeft, mDefaultTopMargin, mPaddingRight,
                    mDefaultBottomMargin);
            container.addView(view, position);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "i'm" + position + "pager", Toast.LENGTH_SHORT).show();
                    viewPagerOnClickListener.onPageClickListener(position);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
}
