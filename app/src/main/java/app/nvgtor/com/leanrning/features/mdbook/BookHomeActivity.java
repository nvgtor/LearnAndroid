package app.nvgtor.com.leanrning.features.mdbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.features.mdbook.book.BooksFragment;
import app.nvgtor.com.leanrning.features.mdbook.widget.BackHandledFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nvgtor on 2016/4/25.
 */
public class BookHomeActivity extends AppCompatActivity implements BackHandledFragment
        .BackHandlerInterface{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.frame_content)
    FrameLayout frameContent;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private BackHandledFragment selectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);

        //menu 点击事件
        setupDrawerContent(navigationView);
        //代码初始化headerView
        setUpProfileImage();
        //首页
        switchToBook();
    }

    private void setUpProfileImage() {
        View headerView = navigationView.inflateHeaderView(R.layout.book_home_drawer_headview);
        View profileView = headerView.findViewById(R.id.profile_image);
        if (profileView != null) {
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchToBlog();
                    drawerLayout.closeDrawers();
                    navigationView.getMenu().getItem(1).setChecked(true);
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
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void switchToAbout() {

    }

    private void switchToBlog() {

    }

    private void switchToExample() {

    }

    private void switchToBook() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BooksFragment()).commit();
        toolbar.setTitle(R.string.navigation_book);
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
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                doExitApp();
            }
        }
    }
}
