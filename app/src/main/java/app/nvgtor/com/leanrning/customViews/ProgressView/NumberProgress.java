package app.nvgtor.com.leanrning.customViews.ProgressView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import app.nvgtor.com.leanrning.R;
import app.nvgtor.com.leanrning.utils.PxToDpUtil;

/**
 * Created by nvgtor on 2016/4/11.
 */
public class NumberProgress extends View {

    private float textSize;
    private int textColor;
    private float reachedHeight;
    private int reachedColor;
    private float unReachedHeight;
    private int unReachedColor;
    private int currentProgress;
    private int maxProgress;

    private Paint reachedPaint;
    private Paint unReachedPaint;
    private Paint textPaint;
    private String currentDrawText;

    /**
     * reach of rect
     * */
    private RectF reachedRectF = new RectF(0,0,0,0);

    /**
     * unReach of rect
     * */
    private RectF unReachedRectF = new RectF(0,0,0,0);

    private boolean drawUnreachedBar = true;

    private boolean drawReachedBar = true;
    private float drawTextStart,drawTextEnd;

    public NumberProgress(Context context) {
        this(context, null);
    }

    public NumberProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public NumberProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberProgress);
        textColor = a.getColor(R.styleable.NumberProgress_textColor, 0xff3498d8);
        textSize = PxToDpUtil.sp2px(context, a.getDimension(R.styleable.NumberProgress_textSize, 14f));
        reachedHeight = PxToDpUtil.dp2px(context, a.getDimension(R.styleable.NumberProgress_reachedHeight, 1.25f));
        reachedColor = a.getColor(R.styleable.NumberProgress_reachedColor, 0xff3498d8);
        unReachedHeight = PxToDpUtil.dp2px(context, a.getDimension(R.styleable.NumberProgress_unReachedHeight, 1.25f));
        unReachedColor = a.getColor(R.styleable.NumberProgress_unReachedColor, 0xffcccccc);

        currentProgress = a.getInt(R.styleable.NumberProgress_currentProgress, 0);
        maxProgress = a.getInt(R.styleable.NumberProgress_maxProgress, 100);

        reachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        reachedPaint.setColor(reachedColor);
        unReachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unReachedPaint.setColor(unReachedColor);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);

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
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int padding = getPaddingLeft() + getPaddingRight();
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        } else {
            result = getSuggestedMinimumWidth();
            result += padding;
            if (mode == MeasureSpec.AT_MOST){
                result = Math.max(result, size);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int padding = getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        } else {
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }


    @Override
    protected int getSuggestedMinimumWidth() {
        //return super.getSuggestedMinimumWidth();
        return (int)textSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        //return super.getSuggestedMinimumHeight();
        return Math.max((int)textSize, Math.max((int)reachedHeight, (int)unReachedHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        calculateDrawRectF();
        if (drawReachedBar) {
            canvas.drawRect(reachedRectF, reachedPaint);
        }

        canvas.drawText(currentDrawText, drawTextStart, drawTextEnd, textPaint);

        if (drawUnreachedBar) {
            canvas.drawRect(unReachedRectF, unReachedPaint);
        }
    }

    private void calculateDrawRectF() {
        currentDrawText = String.format("%d", currentProgress * 100 / maxProgress);
        currentDrawText = currentDrawText + "%";
        float drawTextWidth = textPaint.measureText(currentDrawText);

        if (currentProgress == 0){
            drawReachedBar = false;
            drawTextStart = getPaddingLeft();
            reachedRectF.right = 0;
        } else {
            drawReachedBar = true;
            reachedRectF.left = getPaddingLeft();
            reachedRectF.top = getHeight() / 2.0f - reachedHeight / 2.0f;
            reachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) /
                    (maxProgress * 1.0f) * (float)currentProgress;
            reachedRectF.bottom = getHeight() / 2.0f + reachedHeight / 2.0f;
            drawTextStart = reachedRectF.right;
        }
        if ((drawTextStart + drawTextWidth) >= (getWidth() - getPaddingRight())){
            drawTextStart = getWidth() - drawTextWidth - getPaddingRight();
            reachedRectF.right = drawTextStart;
        }

        drawTextEnd = (int)((getHeight() / 2.0f) - ((textPaint.descent() + textPaint.ascent()) / 2.0f));
        float unReachedBarStart = reachedRectF.right + drawTextWidth;
        if (unReachedBarStart >= getWidth() - getPaddingRight()){
            drawUnreachedBar = false;
        }else {
            drawUnreachedBar = true;
            unReachedRectF.left = unReachedBarStart;
            unReachedRectF.right = getWidth() - getPaddingRight();
            unReachedRectF.top = getHeight() / 2.0f - unReachedHeight / 2.0f;
            unReachedRectF.bottom = getHeight() / 2.0f + unReachedHeight / 2.0f;
        }
    }
}
