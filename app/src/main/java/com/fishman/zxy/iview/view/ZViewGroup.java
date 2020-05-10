package com.fishman.zxy.iview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ZViewGroup extends ViewGroup {
    private int mHeight;
    private int mWidth;

    public ZViewGroup(Context context) {
        this(context,null);
    }

    public ZViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //measureChildren(widthMeasureSpec,heightMeasureSpec);
        /**
         * 自定义ViewGroup 应该考虑的问题点
         */
        int  childCount=getChildCount();
        if(childCount==0){
           // 布局的时候告诉父容器，ViewGroup没有子控件,你设置多大就显示多大
            //当MeasureSpec 属性是 wrap_content时就直接为0
            setMeasuredDimension(MeasuredWidth_Height(widthMeasureSpec),MeasuredWidth_Height(heightMeasureSpec));
        }else{
            //当有子控件的时候
            // 如果ViewGroup 设定了指定值的大小，测量的值就是指定的大小
            // 如果ViewGorup 设定了测量模式是 wrap_content时  我们需要根据子控件的大小来确定值得大小
            int childViewWidth=0;
            int childViewHeight=0;
            int childViewMarginLeft=0;
            int childViewMarginRight=0;
            int childViewMarginTop=0;
            int childViewMarginBottom=0;
            for(int i=0;i<childCount;i++) {
                View childview = getChildAt(i);
                measureChild(childview, widthMeasureSpec, heightMeasureSpec); //测量子控件
                MarginLayoutParams mlp= (MarginLayoutParams) childview.getLayoutParams();
                childViewWidth = Math.max(childViewWidth, childview.getMeasuredWidth()); //获取子控件中宽度最大的一个值
                childViewHeight += childview.getMeasuredHeight();  //获得所有子控件高的值并相加
                childViewMarginTop+=mlp.topMargin;
                childViewMarginBottom+=mlp.bottomMargin;
                childViewMarginLeft=Math.max(childViewMarginLeft,mlp.leftMargin);
                childViewMarginRight=Math.max(childViewMarginRight,mlp.rightMargin);
            }
            mWidth=childViewWidth+childViewMarginLeft+childViewMarginRight;
            mHeight=childViewHeight+childViewMarginTop+childViewMarginBottom;
            setMeasuredDimension(MeasuredWidth_Height(widthMeasureSpec,mWidth),MeasuredWidth_Height(heightMeasureSpec,mHeight));
        }
    }
    private int MeasuredWidth_Height(int spec,int widh_height){
        int result=0;
        int specModel=MeasureSpec.getMode(spec); //获取布局的测量模式
        int specSize=MeasureSpec.getSize(spec);  //测量大小
        if(specModel==MeasureSpec.EXACTLY){
            result=specSize;
        }else {
            result=widh_height;
        }
        return result;
    }
    private int MeasuredWidth_Height(int spec){
        int result=0;
        int specModel=MeasureSpec.getMode(spec); //获取布局的测量模式
        int specSize=MeasureSpec.getSize(spec);  //测量大小
        if(specModel==MeasureSpec.EXACTLY){
            result=specSize;
        }else {
            result=0;
        }
        return result;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
             int left,top,right,bottom;
             int childCount=getChildCount();
             for(int i=0;i<childCount;i++){
                 View childview=getChildAt(i);
                 MarginLayoutParams mlp= (MarginLayoutParams) childview.getLayoutParams();
                 top=mlp.topMargin;
                 left=mlp.leftMargin;
                 right=left+mlp.rightMargin;
                 bottom=top+mlp.bottomMargin;
                 childview.layout(left,top,right,bottom);
             }
             //getMeasuredHeight 和 getHeight的区别，仅仅是时机不同而已
             // getMeasuredHeight 必须要onMeasure之后才能获取到值
             //getHeight   必须要在onLayout 之后才能获取到值
             //根据时机的问题，这俩个值可以相同可以不同

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
