package app.nvgtor.com.leanrning.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.Locale;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.view.threeD.rotate3dpic.Rotate3dActivity;
import app.nvgtor.com.leanrning.view.threeD.threeDSliding.ThreeDSlidingActivity;

/**
 * Created by ydz on 16/3/26.
 */
public class HomeActivity extends AppCompatActivity implements
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private TextView hello_tv;
    private TextView world_tv;
    float curTranslationX;

    // SVG
    private static final String SVG_PATH = "M 42.266949,70.444915 C 87.351695,30.995763 104.25847,28.177966 104.25847,28.177966 l 87.3517,36.631356 8.45339,14.088983 L 166.25,104.25847 50.720339,140.88983 c 0,0 -45.0847458,180.33898 -39.449153,194.42797 5.635594,14.08898 67.627119,183.15678 67.627119,183.15678 l 16.90678,81.7161 c 0,0 98.622885,19.72457 115.529665,22.54237 16.90678,2.8178 70.44491,-22.54237 78.8983,-33.81356 8.45339,-11.27118 76.08051,-107.07627 33.81356,-126.80085 -42.26695,-19.72457 -132.43644,-56.35593 -132.43644,-56.35593 0,0 -33.81356,-73.26271 -19.72458,-73.26271 14.08899,0 132.43644,73.26271 138.07204,33.81356 5.63559,-39.44915 19.72457,-169.0678 19.72457,-169.0678 0,0 28.17797,-25.36017 -28.17796,-19.72457 -56.35593,5.63559 -95.80509,11.27118 -95.80509,11.27118 l 42.26695,-87.35169 8.45339,-28.177968";
    private static final int[] START_POINT = new int[]{300, 270};
    private static final int[] BOTTOM_POINT = new int[]{300, 400};
    private static final int[] LEFT_CONTROL_POINT = new int[]{450, 200};
    private static final int[] RIGHT_CONTROL_POINT = new int[]{150, 200};

    ImageView image;
    ImageView mountain;
    TextView text;
    TextView percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        initView();
        initListener();
    }


    private void initListener() {
        fab.setOnClickListener(this);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        hello_tv = (TextView) findViewById(R.id.hello_tv);
        world_tv = (TextView) findViewById(R.id.world_tv);
        curTranslationX = hello_tv.getTranslationX();

        image = (ImageView) findViewById(R.id.image);
        mountain = (ImageView) findViewById(R.id.mountain);
        text = (TextView) findViewById(R.id.text);
        percent = (TextView) findViewById(R.id.percent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //helloWorldAnim();
        //Animator animator = AnimatorInflater.loadAnimator(this, R.animator.hello_animator);
        //animator.setTarget(world_tv);
        //animator.start();

        //simpleAnimation();
        animateParallel();
        //animateSequentially();
    }

    protected void simpleAnimation() {
        ViewAnimator.animate(mountain)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .andAnimate(text)
                .translationX(-200, 0)
                .interpolator(new DecelerateInterpolator())
                .duration(2000)

                .thenAnimate(mountain)
                .scale(1f, 0.5f, 1f)
                .interpolator(new AccelerateInterpolator())
                .duration(1000)

                .start();
    }

    protected void animateParallel() {
        ViewAnimator.animate(mountain, image)
                .dp().translationY(-1000, 0)
                .alpha(0, 1)
                .andAnimate(percent)
                .scale(0, 1)

                .andAnimate(text)
                .textColor(Color.BLACK, Color.WHITE)
                .backgroundColor(Color.WHITE, Color.BLACK)

                .waitForHeight()
                .duration(2000)

                .thenAnimate(percent)
                .custom(new AnimationListener.Update<TextView>() {
                    @Override
                    public void update(TextView view, float value) {
                        view.setText(String.format(Locale.US, "%.02f%%", value));
                    }
                }, 0, 1)

                .andAnimate(image)
                .rotation(0, 360)

                .duration(5000)

                .start();
    }

    protected void animateSequentially() {
        ViewAnimator.animate(image)
                .dp().width(100f, 150f)
                .alpha(1, 0.1f)
                .interpolator(new DecelerateInterpolator())
                .duration(800)
                .thenAnimate(image)
                .dp().width(150f, 100f)
                .alpha(0.1f, 1f)
                .interpolator(new AccelerateInterpolator())
                .duration(1200)
                .start();

        ViewAnimator
                .animate(image).scaleX(0, 1).scaleY(0, 1).alpha(0, 1).descelerate().duration(500)
                .thenAnimate(image).scaleX(1, 0).scaleY(1, 0).alpha(1, 0).accelerate().duration(500);
    }

    private void helloWorldAnim(){
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(hello_tv, "translationX", -700f, 0f);
        moveIn.setDuration(2000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(hello_tv, "alpha", 0f, 1f);
        fadeIn.setDuration(2000);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(hello_tv, "rotation", 0f, 360f);
        rotate.setDuration(3000);
        ObjectAnimator scale = ObjectAnimator.ofFloat(hello_tv, "scaleY", 1f, 3f, 1f);
        scale.setDuration(3000);
        ObjectAnimator moveOut = ObjectAnimator.ofFloat(hello_tv, "translationX", 0f, 700f);
        moveOut.setDuration(2000);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(hello_tv, "alpha", 1f, 0f);
        fadeOut.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(moveIn).with(fadeIn).before(rotate);
        animatorSet.play(moveOut).with(fadeOut).after(rotate);
        animatorSet.play(rotate).with(scale);
        animatorSet.start();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_aty_life:
                Intent lifeAty = new Intent(this, TestLifeOfActivity.class);
                startActivity(lifeAty);
                break;
            case R.id.nav_number_roll:
                Intent intent = new Intent(this, NumberRollingActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_custom:
                Intent intent1 = new Intent(this, CustomActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_point_anim:
                Intent intent2 = new Intent(this, PointAnimActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_vp_indicator:
                Intent vpIndicatorIntent = new Intent(this, ViewPagerIndicatorActivity.class);
                startActivity(vpIndicatorIntent);
                break;
            case R.id.nav_rotate:
                Intent rotateIntent = new Intent(this, Rotate3dActivity.class);
                startActivity(rotateIntent);
                break;
            case R.id.nav_threedsliding:
                Intent threeDSlindgingIntent = new Intent(this, ThreeDSlidingActivity.class);
                startActivity(threeDSlindgingIntent);
                break;
        }
        drawer.closeDrawers();
        return true;
    }

}
