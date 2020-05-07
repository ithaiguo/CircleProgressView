package com.example.circleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class CustomCircleView extends View {

    private Paint paint;
    private LinearGradient gradient;
    private RectF rectF;
    private float strokeWidth = 20;
    private float progressPercent;

    public CustomCircleView(Context context) {
        super(context);
        init();
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }


    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void initAttrs(AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
//        paint.setShader(gradient);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        gradient = new LinearGradient(getWidth(), 0, getWidth(), getHeight(), Color.rgb(240, 89, 148),
                Color.rgb(255, 255, 255), Shader.TileMode.MIRROR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        paint.setColor(Color.rgb(244,244,244));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(centerX,centerY,centerX - strokeWidth/2 ,paint);

        if (rectF == null) {//外切正方形
            rectF = new RectF(strokeWidth / 2, strokeWidth / 2, 2 * centerX - strokeWidth / 2, 2 * centerX - strokeWidth / 2);
//            rectF = new RectF(0,0,getWidth(),getHeight());
        }

        Log.d("ppp","centerX======"+centerX);
        Log.d("ppp","centerY======"+centerY);
        Log.d("ppp","rectF centerX======"+rectF.centerX());
        Log.d("ppp","rectF centerY======"+rectF.centerY());

//        paint.setShader(null);
//        paint.setColor(Color.BLUE);
//        canvas.drawRect(rectF,paint);

        paint.setShader(gradient);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF,-90,progressPercent * 3.6f,false,paint);

        paint.setShader(null);
        paint.setColor(Color.rgb(0,0,0));
        paint.setTextSize(60);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom - strokeWidth/2;
        canvas.drawText("text",centerX,centerY+distance,paint);


    }

    public void setProgress(float progress){
        this.progressPercent = progress;
        invalidate();
    }
}
