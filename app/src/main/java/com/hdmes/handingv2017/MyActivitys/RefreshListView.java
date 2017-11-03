package com.hdmes.handingv2017.MyActivitys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.format.Time;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView.OnScrollListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdmes.handingv2017.R;

import java.text.SimpleDateFormat;

/**
 * Created by fusanjie on 2017/10/30.
 */

public class RefreshListView extends ListView implements OnScrollListener {
    View header;// 顶部提示View
    int headerHeight;//header的高度
    int firstVisibleItem;//当前界面第一个可见的item的位置
    boolean isFlag;//标志，是在当前显示的listView是在listView最顶端时按下额
    int startY;//用户按下的Y值
    int state;//当前状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELEASE = 2;//提示释放状态
    final int REFRESH = 3;//提示正在刷新状态

    private int scrollState = 1;//滚动状态为 1
    // 下拉的距离
    public float moveDeltaY = 0;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isFlag = true;//ListView最顶端按下，标志值设为真
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELEASE) {
                    state = REFRESH;
                    //加载数据
                    refreshViewByState();
                    iRefreshlistener.onRefresh();//刷新状态
                } else if (state == PULL) {
                    state = NONE;
                    isFlag = false;
                    refreshViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //
    private void onMove(MotionEvent ev) {
        //如果不是最顶端按下，则直接返回
        if (!isFlag) {
            return;
        }
        int currentY = (int) ev.getY();//当前的Y值
        int space = currentY - startY;//用户向下拉的距离
        int topPadding = space - headerHeight;//顶部提示View距顶部的距离值
        switch (state) {
            //正常状态
            case NONE:
                if (space > 0) {
                    state = PULL;//下拉的距离大于0，则将状态改为PULL(提示下拉更新)
                    refreshViewByState();//根据状态的不同更新View
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 50//下拉的距离大于header的高度加30且用户滚动屏幕，手指仍在屏幕上
                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELEASE;//将状态改为RELEASE(提示松开更新)
                    refreshViewByState();
                }
                break;
            case RELEASE:
                topPadding(topPadding);
                if (space < headerHeight + 50) {//用户下拉的距离不够
                    state = PULL;         //将状态改为PULL
                    refreshViewByState();
                } else if (space <= 0) {  //用户下拉的距离为非正值
                    state = NONE;    //将状态改为NONE
                    isFlag = false;  //标志改为false
                    refreshViewByState();
                }
                break;
        }
    }
    //

    /**
     * 根据当前状态state,改变界面显示
     * state:
     * NONE：无操作
     * PULL：下拉可以刷新
     * RELEASE：松开可以刷新
     * REFREASH：正在刷新
     */
    private void refreshViewByState() {
        //提示
        TextView tips = (TextView) header.findViewById(R.id.tips);
        //箭头
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        //进度条
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
        //箭头的动画效果1，由0度转向180度，即箭头由朝下转为朝上
        RotateAnimation animation1 = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation1.setDuration(500);
        animation1.setFillAfter(true);
        //箭头的动画效果2，由180度转向0度，即箭头由朝上转为朝下
        RotateAnimation animation2 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation2.setDuration(500);
        animation2.setFillAfter(true);

        switch (state) {
            case NONE:                     //正常状态
                arrow.clearAnimation();    //清除箭头动画效果
                topPadding(-headerHeight); //设置header距离顶部的距离
                break;

            case PULL:                                //下拉状态
                arrow.setVisibility(View.VISIBLE);    //箭头设为可见
                progress.setVisibility(View.GONE);    //进度条设为不可见
                tips.setText("下拉刷新");           //提示文字设为"下拉可以刷新"
                arrow.clearAnimation();               //清除之前的动画效果，并将其设置为动画效果2
                arrow.setAnimation(animation2);
                // 设置最后刷新时间
                TextView tvLastUpdateTime = (TextView) header
                        .findViewById(R.id.time);
                tvLastUpdateTime.setText("更新时间: " + getLastUpdateTime());
                break;

            case RELEASE:                            //下拉状态
                arrow.setVisibility(View.VISIBLE);   //箭头设为可见
                progress.setVisibility(View.GONE);   //进度条设为不可见
                tips.setText("释放立即刷新");          //提示文字设为"松开可以刷新"
                arrow.clearAnimation();              //清除之前的动画效果，并将其设置为动画效果2
                arrow.setAnimation(animation1);
                break;

            case REFRESH:                             //更新状态
                topPadding(50);                       //距离顶部的距离设置为50
                arrow.setVisibility(View.GONE);       //箭头设为不可见
                progress.setVisibility(View.VISIBLE); //进度条设为可见
                tips.setText("正在刷新...");            //提示文字设为""正在刷新..."
                arrow.clearAnimation();                //清除动画效果
                break;

        }
    }

    //
    IRefreshListener iRefreshlistener;//刷新数据的接口

    // ...
    public void setInterface(IRefreshListener listener) {
        this.iRefreshlistener = listener;
    }

    /**
     * 刷新数据接口
     *
     * @author lenovo
     */
    public interface IRefreshListener {
        public void onRefresh();
    }

    /**
     * 获得系统的最新时间
     *
     * @return
     */
    private String getLastUpdateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }

    //
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (moveDeltaY == 0)
            return;
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), moveDeltaY);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 阴影的高度为26
        LinearGradient linearGradient = new LinearGradient(0, moveDeltaY, 0, moveDeltaY - 26, 0x66000000, 0x00000000, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        paint.setStyle(Paint.Style.FILL);
        // 在moveDeltaY处往上变淡
        canvas.drawRect(rectF, paint);
    }

    //
    public void refreshComplete() {
        state = NONE;   //状态设为正常状态
        isFlag = false; //标志设为false
        int updateTime = 112;
        refreshViewByState();
        //设置提示更新时间间隔
        Time t = new Time();
        t.setToNow();

        int time = t.hour * 60 + t.minute - updateTime;
        updateTime = t.hour * 60 + t.minute;
        TextView lastUpdateTime = (TextView) findViewById(R.id.time);//时间
        lastUpdateTime.setText(time + "分钟前更新");

    }

    //
    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始换head_layout
     *
     * @param context
     */
    private void initView(Context context) {
        //LayoutInflater作用是加载布局
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout, null);

        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
    }

    /**
     * 设置顶部布局的上边距
     *
     * @param topPadding
     */
    private void topPadding(int topPadding) {
        //设置顶部提示的边距
        //除了顶部用参数值topPadding外，其他三个用header默认的值
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        //使header无效，将来调用onDraw()重绘View
        header.invalidate();
    }

    /**
     * 通知父布局，占用的宽和高
     */
    private void measureView(View view) {
        //LayoutParams are used by views to tell their parents
        //how they want to be laid out.
        //LayoutParams被view用来告诉它们的父布局它们应该被怎样安排
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            //两个参数:width,height
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //getChildMeasureSpec:获取子View的widthMeasureSpec、heightMeasureSpec值
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }
}
