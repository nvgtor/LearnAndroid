package app.nvgtor.com.leanrning.features.mdbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mdbook.book.BooksFragment;
import app.nvgtor.com.leanrning.features.mdbook.example.BottomTabActivity;
import app.nvgtor.com.leanrning.features.mdbook.widget.BackHandledFragment;

/**
 * Created by nvgtor on 2016/4/25.
 */
public class BookHomeActivity extends AppCompatActivity implements BackHandledFragment
        .BackHandlerInterface{

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;
    private BackHandledFragment selectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        //menu 点击事件
        setupDrawerContent(mNavigationView);
        //代码初始化headerView
        setUpProfileImage();
        //首页
        mToolbar.setTitle(R.string.navigation_book);
        switchToBook();
    }

    private void setUpProfileImage() {
        View headerView = mNavigationView.inflateHeaderView(R.layout.book_home_drawer_headview);
        View profileView = headerView.findViewById(R.id.profile_image);
        if (profileView != null) {
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchToBlog();
                    mDrawerLayout.closeDrawers();
                    mNavigationView.getMenu().getItem(1).setChecked(true);
                }
            });
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_item_book:
                        switchToBook();
                        break;
                    case R.id.navigation_item_example:
                        switchToExample();
                        break;
                    case R.id.navigation_item_blog:
                        switchToBlog();
                        break;
                    case R.id.navigation_item_about:
                        switchToAbout();
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void switchToAbout() {
        mToolbar.setTitle(R.string.navigation_about);
    }

    private void switchToBlog() {
        mToolbar.setTitle(R.string.navigation_my_blog);
    }

    private void switchToExample() {
        mToolbar.setTitle(R.string.navigation_example);
        Intent intent = new Intent(this, BottomTabActivity.class);
        startActivity(intent);
    }

    private void switchToBook() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BooksFragment()).commit();
        mToolbar.setTitle(R.string.navigation_book);
    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                //doExitApp();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
