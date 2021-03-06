package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.Utils;


/**
 * Created by yukun on 17-10-11.
 */

public class RotateLayout extends RelativeLayout {
    private Context context;
    private static final String TAG = "LuckPanLayout";
    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int radius;
    private int CircleX,CircleY;
    private Canvas canvas;
    private boolean isYellow = false;
    private int delayTime = 500;
    private RotateView rotatePan;
    private ImageView startBtn;

    private int screenWidth,screeHeight;
    private int MinValue;
    private static final String START_BTN_TAG = "startbtn";
    public static final int DEFAULT_TIME_PERIOD = 500;

    public RotateLayout(Context context) {
        super(context);
        init(context,null,0);
    }

    public RotateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public RotateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,0);
    }

    private void init(Context context, AttributeSet attrs, int i) {
        this.context = context;
//        backgroundPaint.setColor(Color.rgb(255,92,93));
        backgroundPaint.setColor(Color.parseColor("#784205"));
        whitePaint.setColor(Color.parseColor("#F5DFB9"));
//        whitePaint.setColor(Color.WHITE);
//        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setColor(Color.parseColor("#FEF8E3"));
        screeHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        //???????????????????????????????????????view???????????????
        post(new Runnable() {
            @Override
            public void run() {
                startLuckLight();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //???????????????view??????????????????????????????
        int centerX = (right - left)/2;
        int centerY = (bottom - top)/2;
        boolean panReady = false;
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            if(child instanceof RotateView){
                rotatePan = (RotateView) child;
                int panWidth = child.getWidth();
                int panHeight = child.getHeight();
                child.layout(centerX - panWidth/2 , centerY - panHeight/2,centerX + panWidth/2, centerY + panHeight/2);
                panReady = true;
            }else if(child instanceof ImageView){
//                if(TextUtilss.equals((String) child.getTag(),START_BTN_TAG)){
                    startBtn = (ImageView) child;
                    int btnWidth = child.getWidth();
                    int btnHeight = child.getHeight();
                    child.layout(centerX - btnWidth/2 , centerY - btnHeight/2 , centerX + btnWidth/2, centerY + btnHeight/2 );
//                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MinValue = Math.min(screenWidth,screeHeight);
        MinValue -= Utils.dip2px(context,20)*2;
        setMeasuredDimension(MinValue,MinValue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int MinValue = Math.min(width,height);

        radius = MinValue /2;
        CircleX = getWidth() /2;
        CircleY = getHeight() /2;
        //?????????
        canvas.drawCircle(CircleX,CircleY,radius,backgroundPaint);
        //???????????????????????????????????????
        drawSmallCircle(isYellow);
    }

    private void drawSmallCircle(boolean FirstYellow) {
        //?????????????????????????????????????????????????????????
        int pointDistance = radius - Utils.dip2px(context,10);
        //????????????20???????????????????????????18??????
        for(int i=0;i<=360;i+=20){
            //????????????????????????????????????i????????????
            int x = (int) (pointDistance * Math.sin(Utils.change(i))) + CircleX;
            int y = (int) (pointDistance * Math.cos(Utils.change(i))) + CircleY;

            //?????????????????????
            if(FirstYellow)
                canvas.drawCircle(x,y,Utils.dip2px(context,6),yellowPaint);
            else
                canvas.drawCircle(x,y,Utils.dip2px(context,6),whitePaint);
            FirstYellow = !FirstYellow;
        }
    }
    //??????boolean ???????????????????????????????????????
    private void startLuckLight(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isYellow = !isYellow;
                invalidate();
                postDelayed(this,delayTime);
            }
        },delayTime);
    }
}
