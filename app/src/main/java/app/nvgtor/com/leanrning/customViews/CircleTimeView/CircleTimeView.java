package app.nvgtor.com.leanrning.customViews.CircleTimeView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nvgtor on 2016/4/11.
 */
public class CircleTimeView extends View{

    private Paint mPaint;

    public CircleTimeView(Context context) {
        this(context, null);
    }

    public CircleTimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.translate(canvas.getWidth() / 2, 200);
        canvas.drawCircle(0, 0, 100, mPaint);

        canvas.save();
        canvas.translate(-75, -75);
        Path path = new Path();
        path.addArc(new RectF(0, 0, 150, 150), -180, 180);
        Paint citePaint = new Paint(mPaint);
        citePaint.setTextSize(14);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("East Young never say never", path, 28, 0, citePaint);
        canvas.restore();

        Paint tmPaint = new Paint(mPaint);
        tmPaint.setStrokeWidth(1);

        float y = 100;
        int count = 60;

        for(int i = 0; i < count; i++) {
            if (i % 5 == 0){
                canvas.drawLine(0f, y, 0, y + 12f, mPaint);
                canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f, tmPaint);
            } else {
                canvas.drawLine(0f, y, 0f, y + 5f, tmPaint);
            }
            canvas.rotate(360 / count, 0f, 0f);
        }

        tmPaint.setColor(Color.GRAY);
        tmPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 7, tmPaint);
        tmPaint.setStyle(Paint.Style.FILL);
        tmPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, 5, tmPaint);
        canvas.drawLine(0, 10, 0, -65, mPaint);
    }
}
