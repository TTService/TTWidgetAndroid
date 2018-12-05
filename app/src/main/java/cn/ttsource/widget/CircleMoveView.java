package cn.ttsource.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * **********************************************
 * <p/>
 * Date: 2018-11-29 15:06
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 圆形移动控件
 *
 * 1、首先创建一个控件，内部含有一个子控件
 * 2、子控件能够移动
 * 3、限制子控件移动的最大范围
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class CircleMoveView extends FrameLayout {
    private View subView = null;

    public CircleMoveView(Context context) {
        super(context);
        initSubView();
    }

    public CircleMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSubView();
    }

    public CircleMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSubView();
    }

    public View getSubView() {
        return subView;
    }

    private void initSubView() {
        subView = new View(this.getContext());
        subView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        subView.setMinimumWidth(100);
        subView.setMinimumHeight(100);

        addView(subView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount != 1) {
            throw new IllegalStateException("There can only be one children");
        }

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        int childMeasuredWidth = measuredWidth / 3;
        int childMeasuredHeight = measuredHeight / 3;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            FrameLayout.LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            layoutParams.width = childMeasuredWidth;
            layoutParams.height = childMeasuredHeight;
            childView.setLayoutParams(layoutParams);
            // 此方法可用
            this.measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        // 让父控件自己去测量每个子控件的大小
        //this.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childView = getChildAt(0);
        int mw = childView.getMeasuredWidth();
        int mh = childView.getMeasuredHeight();

        int parentWidth = getWidth();
        int parentHeight = getHeight();

        childView.layout((parentWidth - mw) /2, (parentHeight - mh) / 2, (parentWidth + mw) / 2, (parentHeight + mh) / 2);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
