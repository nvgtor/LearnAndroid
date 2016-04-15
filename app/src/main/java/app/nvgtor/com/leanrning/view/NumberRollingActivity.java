package app.nvgtor.com.leanrning.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;

import app.nvgtor.com.leanrning.R;

/**
 * Created by ydz on 16/3/26.
 */
public class NumberRollingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_activity_layout);
        setTitle("Number Rolling");

        final TextView tv = (TextView) findViewById(R.id.account_total_income_tv);

        final DecimalFormat df = new DecimalFormat("0.00");

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 10f, -10f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setText("" + df.format((float) animation.getAnimatedValue()));
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setStartDelay(100);
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }
}
