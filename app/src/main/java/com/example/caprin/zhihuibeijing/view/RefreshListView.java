package com.example.caprin.zhihuibeijing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.caprin.zhihuibeijing.Base.TabDetailPager;
import com.example.caprin.zhihuibeijing.R;

/**
 * Created by caprin on 16-11-17.
 */
public class RefreshListView extends ListView {
    private int startY;
    private int mHeaderViewHeight;
    private int mCurrentState;
    private View mHeaderView;
    private static final int STATE_PULL_REFRESH = 0;
    private static final int STATE_RELEASE_REFRESH = 1;
    private static final int STATE_REFRESHING = 2;

    private ImageView mArr;
    private ProgressBar mProgress;
    private TextView tvTitle;
    private TextView tvTime;
    private RotateAnimation animUp;
    private RotateAnimation animDown;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initHeaderView();
    }


    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
        this.addHeaderView(mHeaderView);

        mHeaderView.measure(10, 10);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();

        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

        mArr = (ImageView) mHeaderView.findViewById(R.id.tv_arr);
        mProgress = (ProgressBar) mHeaderView.findViewById(R.id.tv_progress);
        tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_refresh);
        tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);

        initArrowAnim();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getRawY();
                }
                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }
                int endY = (int) ev.getRawY();
                int dy = endY - startY;

                if (dy > mHeaderViewHeight + 200) {
                    dy = mHeaderViewHeight + 200;
                }

                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    int padding = dy - mHeaderViewHeight;
                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentState != STATE_RELEASE_REFRESH) {
                        mCurrentState = STATE_RELEASE_REFRESH;
                        refreshState();
                    } else if (padding < 0 && mCurrentState != STATE_PULL_REFRESH) {
                        mCurrentState = STATE_PULL_REFRESH;
                        refreshState();
                    }

                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                startY = -1;

                if (mCurrentState == STATE_RELEASE_REFRESH) {
                    mCurrentState = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    refreshState();
                } else if (mCurrentState == STATE_PULL_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                }
                break;

            default:
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                mProgress.setVisibility(INVISIBLE);
                mArr.setVisibility(VISIBLE);
                mArr.startAnimation(animDown);
                break;
            case STATE_RELEASE_REFRESH:
                tvTitle.setText("松开刷新");
                mProgress.setVisibility(INVISIBLE);
                mArr.setVisibility(VISIBLE);
                mArr.startAnimation(animUp);
                break;

            case STATE_REFRESHING:
                tvTitle.setText("正在刷新...");
                mProgress.setVisibility(VISIBLE);
                mArr.clearAnimation();
                mArr.setVisibility(INVISIBLE);
                mListener.onRefresh();
                break;

            default:
                break;
        }
    }

    private void initArrowAnim() {
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);

        animUp.setDuration(300);
        animUp.setFillAfter(true);

        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);

        animDown.setDuration(300);
        animDown.setFillAfter(true);
    }

    public void setOnRefreshListener(onRefreshListener listener) {
        mListener = listener;
    }

    onRefreshListener mListener;

    public interface onRefreshListener {
        public void onRefresh();
    }

    public void onRefreshComplete(boolean success) {
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

}
