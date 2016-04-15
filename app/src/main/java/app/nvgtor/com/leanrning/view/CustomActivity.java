package app.nvgtor.com.leanrning.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.customViews.CheckBoxSample.CheckBoxSample;
import app.nvgtor.com.leanrning.customViews.ClearableEditText.ClearableEditText;
import butterknife.ButterKnife;

public class CustomActivity extends AppCompatActivity implements
        View.OnClickListener{

    private ClearableEditText myClear_editText;
    ImageView gif_imgView;
    private CheckBoxSample checkBoxSample1;
    private CheckBoxSample checkBoxSample2;
    private CheckBoxSample checkBoxSample3;
    private CheckBoxSample checkBoxSample4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        initListener();
    }


    private void initListener() {

    }

    private void initView() {
        myClear_editText = (ClearableEditText) findViewById(R.id.myClear_editText);
        gif_imgView = (ImageView) findViewById(R.id.gif_imgView);
        Glide.with(this).load(R.drawable.loading).into(gif_imgView);

        checkBoxSample1 = (CheckBoxSample) findViewById(R.id.check1);
        checkBoxSample2 = (CheckBoxSample) findViewById(R.id.check2);
        checkBoxSample3 = (CheckBoxSample) findViewById(R.id.check3);
        checkBoxSample4 = (CheckBoxSample) findViewById(R.id.check4);

        checkBoxSample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSample1.toggle();
            }
        });
        checkBoxSample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSample2.toggle();
            }
        });
        checkBoxSample3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSample3.toggle();
            }
        });
        checkBoxSample4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSample4.toggle();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}
