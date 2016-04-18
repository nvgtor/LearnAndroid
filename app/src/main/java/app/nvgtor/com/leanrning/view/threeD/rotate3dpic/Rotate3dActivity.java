package app.nvgtor.com.leanrning.view.threeD.rotate3dpic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import app.nvgtor.com.leanrning.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nvgtor on 2016/4/18.
 */
public class Rotate3dActivity extends AppCompatActivity {

    @Bind(R.id.pic_list_view)
    ListView picListView;
    @Bind(R.id.picture)
    ImageView picture;
    @Bind(R.id.layout)
    RelativeLayout layout;

    /**
     * 图片列表的适配器
     */
    private PictureAdapter adapter;

    /**
     * 存放所有图片的集合
     */
    private List<Picture> picList = new ArrayList<Picture>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotate3d_layout);
        ButterKnife.bind(this);

        initPics();

        adapter = new PictureAdapter(this, 0, picList);
        picListView.setAdapter(adapter);
        picListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                picture.setImageResource(picList.get(position).getResource());

                float centerX = layout.getWidth() / 2f;
                float centerY = layout.getHeight() / 2f;
                // 构建3D旋转动画对象，旋转角度为0到90度，这使得ListView将会从可见变为不可见
                final Rotate3dAnimation rotation = new Rotate3dAnimation(0, 90, centerX, centerY,
                        310.0f, true);
                // 动画持续时间500毫秒
                rotation.setDuration(500);
                rotation.setFillAfter(true);
                rotation.setInterpolator(new AccelerateInterpolator());
                rotation.setAnimationListener(new TurnToImageView());
                layout.startAnimation(rotation);
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取布局的中心点位置，作为旋转的中心点
                float centerX = layout.getWidth() / 2f;
                float centerY = layout.getHeight() / 2f;
                // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的
                final Rotate3dAnimation rotation = new Rotate3dAnimation(360, 270, centerX,
                        centerY, 310.0f, true);
                // 动画持续时间500毫秒
                rotation.setDuration(500);
                // 动画完成后保持完成的状态
                rotation.setFillAfter(true);
                rotation.setInterpolator(new AccelerateInterpolator());
                // 设置动画的监听器
                rotation.setAnimationListener(new TurnToListView());
                layout.startAnimation(rotation);
            }
        });
    }

    class TurnToImageView implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            float centerX = layout.getWidth() / 2f;
            float centerY = layout.getHeight() / 2f;
            picListView.setVisibility(View.GONE);
            picture.setVisibility(View.VISIBLE);
            picture.requestFocus();
            Rotate3dAnimation rotation = new Rotate3dAnimation(270, 360, centerX, centerY, 310.0f, false);
            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            layout.startAnimation(rotation);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class TurnToListView implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        /**
         * 当ImageView的动画完成后，还需要再启动ListView的动画，让ListView从不可见变为可见
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = layout.getWidth() / 2f;
            float centerY = layout.getHeight() / 2f;
            // 将ImageView隐藏
            picture.setVisibility(View.GONE);
            // 将ListView显示
            picListView.setVisibility(View.VISIBLE);
            picListView.requestFocus();
            // 构建3D旋转动画对象，旋转角度为90到0度，这使得ListView将会从不可见变为可见，从而回到原点
            final Rotate3dAnimation rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(500);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            layout.startAnimation(rotation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

    }

    private void initPics() {
        Picture bird = new Picture("Bird", R.drawable.bird);
        picList.add(bird);
        Picture winter = new Picture("Winter", R.drawable.winter);
        picList.add(winter);
        Picture autumn = new Picture("Autumn", R.drawable.autumn);
        picList.add(autumn);
        Picture greatWall = new Picture("Great Wall", R.drawable.great_wall);
        picList.add(greatWall);
        Picture waterFall = new Picture("Water Fall", R.drawable.water_fall);
        picList.add(waterFall);
    }
}
