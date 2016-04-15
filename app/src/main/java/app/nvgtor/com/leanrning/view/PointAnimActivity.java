package app.nvgtor.com.leanrning.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import app.nvgtor.com.leanrning.R;

/**
 * Created by ydz on 16/3/26.
 */
public class PointAnimActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.point_anim_layout);
        setTitle("Point anim");
    }
}
