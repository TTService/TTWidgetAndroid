package cn.ttsource.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * *********************************
 * Created by SinPingWu on 2017/12/28 上午10:52.
 * <p>
 * brief:
 * <p>
 * history:
 * <p>
 * *********************************
 */

public class XRecyclerAdapter extends RecyclerView.Adapter<XRecyclerViewHolder> {
    private Context mContext;
    private RecyclerView.Adapter mAdapter;

    public XRecyclerAdapter(Context context, RecyclerView.Adapter adapter) {
        this.mContext = context;
        this.mAdapter = adapter;
    }

    @Override
    public XRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
        View view = viewHolder.itemView;
        FrameLayout parentLayout = new FrameLayout(mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        parentLayout.setLayoutParams(layoutParams);

        parentLayout.addView(view);

        TextView textView = new TextView(mContext);
        textView.setText("Delete");
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.RED);

        parentLayout.addView(textView);
        XRecyclerViewHolder holder= new XRecyclerViewHolder(parentLayout);
        return holder;
    }

    @Override
    public void onBindViewHolder(XRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }
}
