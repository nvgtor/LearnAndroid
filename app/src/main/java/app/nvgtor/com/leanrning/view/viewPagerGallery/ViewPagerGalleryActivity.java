package app.nvgtor.com.leanrning.view.viewPagerGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import app.nvgtor.com.leanrning.R;

/**
 * Created by nvgtor on 2016/4/18.
 */
public class ViewPagerGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_gallery_layout);

        final ScaleYViewPager pager = (ScaleYViewPager) findViewById(R.id.pager);
    }
}
