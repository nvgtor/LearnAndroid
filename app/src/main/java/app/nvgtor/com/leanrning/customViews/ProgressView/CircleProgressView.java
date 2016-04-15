package app.nvgtor.com.leanrning.customViews.ProgressView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;

/**
 * Created by nvgtor on 2016/4/11.
 */
public class CircleProgressView extends View {

    /**
     * 百分比文字颜色
     * */
    private int textColor;
    /**
     * 百分比文字大小
     * */
    private float textSize;

    private int unProgressColor;
    private int progressColor;


    private Paint circlePaint;
    private Paint arcPaint;
    private Paint textPaint;

    private float arcWidth;

    private RectF oval =new RectF(0,0,0,0);

    /**
     * 当前进度
     * */
    private int currentProgress = 0;
    /**
     * 总进度
     * */
    private int maxProgress = 100;

    private String currentDrawText;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.NumberProgress);
        textColor  = a.getColor(R.styleable.NumberProgress_textColor, Color.parseColor("#3498DB"));
        textSize = PxToDpUtil.sp2px(context, a.getDimension(R.styleable.NumberProgress_textSize, 14f));
        unProgressColor = a.getColor(R.styleable.NumberProgress_unProgressColor, Color.parseColor("#113498DB"));
        progressColor = a.getColor(R.styleable.NumberProgress_progressColor, Color.parseColor("#3498DB"));

        currentProgress = a.getInt(R.styleable.NumberProgress_currentProgress, 0);
        maxProgress = a.getInt(R.styleable.NumberProgress_maxProgress, 100);

        arcWidth = PxToDpUtil.dp2px(context,a.getDimension(R.styleable.NumberProgress_progressWidth,4));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(arcWidth);
        circlePaint.setColor(unProgressColor);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setColor(progressColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        a.recycle();
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int padding = getPaddingTop()+getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST){
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int padding = getPaddingTop()+getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST){
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return PxToDpUtil.dp2px(getContext(),100);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return PxToDpUtil.dp2px(getContext(),100);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 绘制浅色圆环
        // 1.圆心（x,y）坐标值
        float centerX = (getWidth()-getPaddingLeft()-getPaddingRight())/2.0f;
        float centerY = (getHeight() - getPaddingTop() - getPaddingBottom())/2.0f;
        // 2.圆环半径
        float radius;
        if (getWidth() >= getHeight()) {
            radius = (centerY - arcWidth / 2);
        }else{
            radius = (centerX - arcWidth / 2);
        }
        canvas.drawCircle(centerX,centerY,radius,circlePaint);

        oval.left = centerX - radius;
        oval.right = centerX + radius;
        oval.top = centerY - radius;
        oval.bottom = centerY+radius;

        canvas.drawArc(oval, 0, (float)360 * currentProgress / (float)maxProgress, false, arcPaint);

        currentDrawText = String.format("%d", currentProgress * 100 /maxProgress);
        currentDrawText = (currentDrawText)+"%";
        float drawTextWidth = textPaint.measureText(currentDrawText);

        canvas.drawText(currentDrawText,centerX-drawTextWidth/2.0f,centerY-((textPaint.descent() + textPaint.ascent()) / 2.0f),textPaint);
    }

}
