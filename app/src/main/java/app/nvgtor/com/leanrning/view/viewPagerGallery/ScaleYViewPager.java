package app.nvgtor.com.leanrning.view.viewPagerGallery;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;

/**
 * Created by nvgtor on 2016/4/18.
 */
public class ScaleYViewPager extends ViewPager {

    private int mPgerMargin = -350;
    private int mPaddingLeft = 130;
    private int mPaddingRight = 130;

    private int mPageLimit = 3;

    private List<View> mList;
    private int [] imgviewIds = {R.id.vp1_img, R.id.vp2_img, R.id.vp3_img,
            R.id.vp4_img, R.id.vp5_img, R.id.vp6_img};
    private List<ImageView> mImgViewList;

    private DefaultViewPagerAdapter mAdapter;

    private Context mContext;
    private LayoutInflater mInflater;
    private int[] mVpIds = {R.layout.pic_vp_layout1, R.layout.pic_vp_layout2, R.layout.pic_vp_layout3
    , R.layout.pic_vp_layout4, R.layout.pic_vp_layout5, R.layout.pic_vp_layout6};
    private int[] imgRersoces = {R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.pic1, R.drawable.pic2, };

    private int mDefaultTopMargin = 16;
    private int mDefaultBottomMargin = 16;

    public ScaleYViewPager(Context context) {
        this(context, null);

    }

    public ScaleYViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
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

        mInflater = LayoutInflater.from(context);
        initImgList();

        mAdapter = new DefaultViewPagerAdapter(mList);
        setAdapter(mAdapter);
    }

    private void initImgList() {
        mList = new ArrayList<View>();
        for (int i = 0; i < mVpIds.length; i++){
            View view = mInflater.inflate(mVpIds[i], null);
            ImageView imageView = (ImageView) view.findViewById(imgviewIds[i]);
            Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), imgRersoces[i],
                    PxToDpUtil.getWindowPx(mContext) + mPgerMargin*2 - mPaddingLeft - mPaddingRight,
                    PxToDpUtil.getWindowHeightPx(mContext) + mPgerMargin*2);

            imageView.setImageBitmap(bitmap);
            mList.add(view);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * set the distance in the center view of the ViewPager
     * @param pagerDis
     */
    public void setScaleYPagerMargin(int pagerDis) {
        setPageMargin(mPgerMargin);
        mAdapter.notifyDataSetChanged();
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
                    Toast.makeText(getContext(), "i'm" + position + "pager", Toast.LENGTH_SHORT).show();
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
