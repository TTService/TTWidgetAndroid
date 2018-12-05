package cn.ttsource.demo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import cn.ttsource.widget.CircleMoveView;
import cn.ttsource.widget.MyTextView;
import cn.ttsource.widget.R;

/**
 * **********************************************
 * <p/>
 * Date: 2018-11-29 10:42
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief:
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {

    private MyTextView txv;
    private Button btn;
    private CircleMoveView moveView;

    GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        txv = (MyTextView) this.findViewById(R.id.txv);
        btn = (Button) this.findViewById(R.id.btnCL);
        btn.setOnClickListener(this);

        this.findViewById(R.id.btnYD).setOnClickListener(this);
        txv.setEnabled(true);
        ViewConfiguration.get(this).getScaledTouchSlop();

        moveView = (CircleMoveView) this.findViewById(R.id.circleView);


        gestureDetector = new GestureDetector(this, new TXVGestureListener());
        gestureDetector.setIsLongpressEnabled(false);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("activity >> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("activity >> onTouchEvent");
        return gestureDetector.onTouchEvent(event);
    }

    class TXVGestureListener implements OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // 内容移动
            //moveView.getSubView().scrollTo((int) (moveView.getSubView().getLeft() + distanceX), (int) (moveView.getSubView().getTop() + distanceY));

            // 平移, 实时
            //moveView.getSubView().setTranslationX(moveView.getSubView().getTranslationX() - distanceX);
            //moveView.getSubView().setTranslationY(moveView.getSubView().getTranslationY() - distanceY);

            // 动画，有延迟
            ObjectAnimator.ofFloat(moveView.getSubView(), "translationX", 0, 100).start();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCL:
                getBtnLocation();
                break;
            case R.id.btnYD:
                translation();
                break;
            default:
                break;
        }
    }

    private void getBtnLocation() {
        System.out.println("left: " + txv.getLeft());
        System.out.println("top: " + txv.getTop());
        System.out.println("right: " + txv.getRight());
        System.out.println("bottom: " + txv.getBottom());

        System.out.println("x: " + txv.getX());
        System.out.println("y: " + txv.getY());

        System.out.println("translationX: " + txv.getTranslationX());
        System.out.println("translationY: " + txv.getTranslationY());
    }

    private void translation() {
        txv.setTranslationX(txv.getX() + 10);
        txv.setTranslationY(txv.getY() + 10);
    }
}
