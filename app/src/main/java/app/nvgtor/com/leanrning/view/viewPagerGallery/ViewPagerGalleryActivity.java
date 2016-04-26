package app.nvgtor.com.leanrning.view.viewPagerGallery;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.customViews.roundImageView.RoundImgActivity;
import app.nvgtor.com.leanrning.features.mdbook.BookHomeActivity;
import app.nvgtor.com.leanrning.otherTest.GestureDetectorTest.GestureDetectorActivity;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;
import app.nvgtor.com.leanrning.view.HomeActivity;

/**
 * Created by nvgtor on 2016/4/18.
 *
 */
public class ViewPagerGalleryActivity extends AppCompatActivity implements ScaleYViewPager.ViewPagerOnClickListener{

    private LayoutInflater mInflater;
    //ImageView id
    private int [] imgViewIds = {R.id.vp1_img, R.id.vp2_img, R.id.vp3_img,
            R.id.vp4_img, R.id.vp5_img, R.id.vp6_img};
    //ViewPager layout
    private int[] mVpIds = {R.layout.pic_vp_layout1, R.layout.pic_vp_layout2, R.layout.pic_vp_layout3
            , R.layout.pic_vp_layout4, R.layout.pic_vp_layout5, R.layout.pic_vp_layout6};
    //ImageView resource
    private int[] imgRersoces = {R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.pic1, R.drawable.pic2, };

    private ScaleYViewPager pager;
    private List<View> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_gallery_layout);

        mInflater = LayoutInflater.from(this);
        pager = (ScaleYViewPager) findViewById(R.id.pager);
        initImgList();
    }

    private void initImgList() {
        mList = new ArrayList<View>();
        for (int i = 0; i < mVpIds.length; i++){
            View view = mInflater.inflate(mVpIds[i], null);
            ImageView imageView = (ImageView) view.findViewById(imgViewIds[i]);
            Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), imgRersoces[i],
                    PxToDpUtil.getWindowPx(this) + pager.getScaleYPagerMargin()*2 -
                            pager.getmPaddingLeft() - pager.getmPaddingRight(),
                    PxToDpUtil.getWindowHeightPx(this) + pager.getScaleYPagerMargin()*2);

            imageView.setImageBitmap(bitmap);
            mList.add(view);
        }
        pager.setImgResList(mList);
        pager.setViewPagerOnClickListener(this);
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

    @Override
    public void onPageClickListener(int position) {
        switch (position){
            case 0:
                toHomeActivity();
                break;
            case 1:
                toBookActivity();
                break;
            case 2:
                toRoundActivity();
                break;
            case 3:
                toGestureActivity();
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    private void toGestureActivity() {
        Intent intent = new Intent(this, GestureDetectorActivity.class);
        startActivity(intent);
    }

    private void toRoundActivity() {
        Intent intent = new Intent(this, RoundImgActivity.class);
        startActivity(intent);
    }

    private void toBookActivity() {
        Intent intent = new Intent(this, BookHomeActivity.class);
        startActivity(intent);
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
