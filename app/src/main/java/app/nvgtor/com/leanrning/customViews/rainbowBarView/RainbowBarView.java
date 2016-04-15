package app.nvgtor.com.leanrning.customViews.rainbowBarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;

/**
 * Created by ydz on 16/3/24.
 */
public class RainbowBarView extends View {

    private Context context;
    int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.GRAY};
    int barColor = Color.RED;
    int hSpace;
    int vSpace;
    int space;
    float startX = 0;
    float delta = 10f;
    Paint mPaint;

    public RainbowBarView(Context context) {
        this(context,null);
    }

    public RainbowBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        hSpace = PxToDpUtil.dp2px(context, 80);
        vSpace = PxToDpUtil.dp2px(context, 4);
        space = PxToDpUtil.dp2px(context, 10);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RainbowBar, 0, 0);
        hSpace = t.getDimensionPixelSize(R.styleable.RainbowBar_hSpace, hSpace);
        vSpace = t.getDimensionPixelSize(R.styleable.RainbowBar_vSpace, vSpace);
        barColor = t.getColor(R.styleable.RainbowBar_barColor, barColor);
        t.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(barColor);
        mPaint.setStrokeWidth(vSpace);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //screen width  控件宽度match parent =.=
        float sw = this.getMeasuredWidth();
        if (startX >= sw + (hSpace + space) - (sw % (hSpace + space))){
            startX = 0;
        }else {
            startX += delta;
        }
        float start = startX;
        //向前移动
        while (start < sw){
            canvas.drawLine(start, 20, start + hSpace, 20, mPaint);
            start += (hSpace + space);
        }

        start = startX - space - hSpace;
        while (start >= -hSpace){
            canvas.drawLine(start, 20, start + hSpace, 20, mPaint);
            start -= (hSpace + space);
        }

        invalidate();
    }
}
