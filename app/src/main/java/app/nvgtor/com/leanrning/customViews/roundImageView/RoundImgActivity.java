package app.nvgtor.com.leanrning.customViews.roundImageView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.nvgtor.com.leanrning.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nvgtor on 2016/4/27.
 */
public class RoundImgActivity extends AppCompatActivity {

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    @Bind(R.id.id_qiqiu)
    RoundImageView mQiQiu;
    @Bind(R.id.id_meinv)
    RoundImageView mMeiNv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_img_view);
        ButterKnife.bind(this);

        mQiQiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQiQiu.setType(RoundImageView.TYPE_ROUND);
            }
        });

        mMeiNv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mMeiNv.setBorderRadius(90);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
