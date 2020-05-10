package com.fishman.zxy.iview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.fishman.zxy.iview.R;

public class ZView extends View {
    private  Paint mPaint;
    private float textsize;
    private int color;
    private String text;
    private Rect textRect;
    private int width;
    private int height;
    public ZView(Context context) {
        this(context,null);
    }

    public ZView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ZViewAttrs,defStyleAttr,0);
        textsize =array.getDimension(R.styleable.ZViewAttrs_textSize,50);
        color=array.getInteger(R.styleable.ZViewAttrs_textColor,R.color.colorPrimary);
        text=array.getString(R.styleable.ZViewAttrs_text);
        mPaint.setTextSize(textsize);
        mPaint.setColor(color);
        textRect=new Rect();
        mPaint.getTextBounds(text,0,text.length(),textRect);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specmodle=MeasureSpec.getMode(widthMeasureSpec);
        int specwidth=MeasureSpec.getSize(widthMeasureSpec);
        if(specmodle==MeasureSpec.EXACTLY){
            width=specwidth;
        }else{
            //at_most  模式
            width=textRect.width()+getPaddingLeft()+getPaddingRight();

        }
        specmodle=MeasureSpec.getMode(heightMeasureSpec);
        int specheight=MeasureSpec.getSize(heightMeasureSpec);
        if(specmodle==MeasureSpec.EXACTLY){
            height=specheight;
        }else{
            //at_most  模式
            height=textRect.height()+getPaddingTop()+getPaddingBottom();

        }
        //确定控件最终的大小
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text,0+getPaddingLeft(),textRect.height()+getPaddingTop(),mPaint);
    }
}
