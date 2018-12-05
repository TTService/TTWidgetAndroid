package cn.ttsource.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;

/**
 * *********************************
 * Created by SinPingWu on 2017/12/28 上午10:23.
 * <p>
 * brief:
 * <p>
 * history:
 * <p>
 * *********************************
 */

public class XRecyclerView extends RecyclerView {

    private View mRootView;
    private View mPrevRootView;
    private View mScrolledView;

    private int mPrevX = 0;
    private int mPrevY = 0;

    /**
     * 记录水平和垂直滑动的总距离
     */
    private int mScrollTotalX = 0;
    private int mScrollTotalY = 0;

    final int MAX_WIDTH = 100;
    private int MAX_WIDTH_DIMEN;

    private OverScroller mOpenScroller;
    private OverScroller mCloseScroller;

    public XRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mOpenScroller = new OverScroller(context, new LinearInterpolator());
        mCloseScroller = new OverScroller(context, new AccelerateInterpolator());

        MAX_WIDTH_DIMEN = (int) (context.getResources().getDisplayMetrics().density * MAX_WIDTH + 0.5);

        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mScrollTotalX = 0;
                mScrollTotalY = 0;

                //根据点击的坐标获取哪个 Item 被点击了
                View view = findChildViewUnder(x, y);
                if (view == null) {
                    // 该事件不进行消费，返回上层控件进行处理
                    return false;
                }

                //获取布局 Item 视图
                final XRecyclerViewHolder viewHolder = (XRecyclerViewHolder) getChildViewHolder(view);
                View itemView = viewHolder.itemView;

                // 判断本次点击视图是否为上次点击视图，如果是，则重置位置，并清空本次及上次的点击视图
                if (itemView.equals(mPrevRootView)) {
                    mScrolledView = mPrevRootView;
                    mPrevRootView = mRootView = null;
                    mCloseScroller.startScroll(mScrolledView.getScrollX(), 0, -mScrolledView.getScrollX(), 0);
                    invalidate();
                    return true;
                }

                // 如果本次点击的视图，不是上次点击的视图，则恢复上一次点击的视图
                if (mPrevRootView != null) {
                    mScrolledView = mPrevRootView;
                    mPrevRootView = null;
                    mCloseScroller.startScroll(mScrolledView.getScrollX(), 0, -mScrolledView.getScrollX(), 0);
                    invalidate();
                }

                // 保存本次点击视图的对象
                mRootView = itemView;

                if (mOnItemClickListener != null && viewHolder.mDeleteView != null) {
                    viewHolder.mDeleteView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemDelete(viewHolder.getAdapterPosition());
                        }
                    });
                }
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                if (mRootView == null)
                    return false;

                mScrollTotalX += Math.abs(mPrevX - x);
                mScrollTotalY += Math.abs(mPrevY - y);

                if (Math.abs(mPrevX - x) > 0 && Math.abs(mPrevX - x) > Math.abs(mPrevY - y)) {
                    int scrollX = mRootView.getScrollX();
                    int newScrollX = scrollX + mPrevX - x;

                    if (newScrollX < 0)
                        newScrollX = 0;
                    else if (newScrollX > MAX_WIDTH_DIMEN)
                        newScrollX = MAX_WIDTH_DIMEN;

                    mRootView.scrollTo(newScrollX, 0);
                }
            }
            break;

            case MotionEvent.ACTION_UP: {
                if (mRootView == null)
                    return false;

                // 判断是否有滑动，如果没有滑动则触发点击事件
                int scrollX = mRootView.getScrollX();
                if (mScrollTotalX < 1 && mScrollTotalY < 1) {
                    // 触发点击事件
                    int position = getChildAdapterPosition(mRootView);
                    if (mOnItemClickListener != null && position >= 0) {
                        mOnItemClickListener.onItemClickLister(mRootView, position);
                    }

                    performClick();
                    return true;
                }

                int newScrollX;
                if (scrollX > MAX_WIDTH_DIMEN / 2) {
                    newScrollX = MAX_WIDTH_DIMEN;
                    mPrevRootView = mRootView;
                } else
                    newScrollX = 0;
                mOpenScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
                invalidate();
            }
            break;
        }
        mPrevX = x;
        mPrevY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mOpenScroller.computeScrollOffset()) {
            if (mRootView != null)
                mRootView.scrollTo(mOpenScroller.getCurrX(), mOpenScroller.getCurrY());
        }

        if (mCloseScroller.computeScrollOffset()) {
            if (mScrolledView != null)
                mScrolledView.scrollTo(mCloseScroller.getCurrX(), mCloseScroller.getCurrY());
        }

        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClickLister(View itemView, int position);
        void onItemDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
