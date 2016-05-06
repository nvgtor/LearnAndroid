package app.nvgtor.com.leanrning.features.mNews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.customViews.ViewPagerIndicator.ViewPagerIndicator;

/**
 * Created by nvgtor on 2016/3/28.
 */
public class NewsVPIndicatorActivity extends FragmentActivity {
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("天大要闻", "公告", "社团风采", "院系动态",
            "视点观察");

    private ViewPagerIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vp_indicator);

        initView();
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,0);

    }

    private void initDatas() {
        for (int i = 0; i < mDatas.size(); i++){
            NewsListFragment fragment = NewsListFragment.newInstance(mDatas.get(i), i+1);
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount()
            {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                return mTabContents.get(position);
            }
        };
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

}
