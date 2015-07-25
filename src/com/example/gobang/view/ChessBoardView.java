package com.example.gobang.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.gobang.controller.Controller;
import com.example.gobang.datastructure.Chess;

/**
 * Created by gaga on 15-4-18.
 * Chessboard Class
 */
public class ChessBoardView extends View {

    private int chessBoardWidth;
    private final static int STANDARD_LINES = 15; //This is a standard chessboard line number

    private final static int DIRECTION_LINES = 15; // We use this as chessboard line number
    public final static int MARGIN_EDGE = 30;
    private int mWidthBetweenLines;
    private int mHeightBetweenLines;
    private int mVerticalBlockNum;
    private int mHorizontalBlockNum;
    private float[] mVerticalLinePoints;
    private float[] mHorizontalLinePoints;
    private Paint mLinePaint = new Paint();
    private Paint mPointsPaint = new Paint();
    private float[] mBlackPoints; // gobang normal points on chess board
    private boolean mDrawExtraPoints;
    private Controller mController;
    private boolean mEnabled;
    private int mLineNum = DIRECTION_LINES;

    public ChessBoardView(Context context) {
        this(context, null, 0);
    }

    public ChessBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChessBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLinePaint.setColor(Color.BLUE);
        mPointsPaint.setStrokeWidth(10);
    }

    public void setChessController(Controller controller) {
        mController = controller;
        invalidate();
    }

    public int getVerticalBlockNum() {
        return mVerticalBlockNum;
    }

    public int getHorizontalBlockNum() {
        return mHorizontalBlockNum;
    }

    public void start() {
        mEnabled = true;
    }

    public void stop() {
        mEnabled = false;
    }

    public void setLineNum(int num) {
        mLineNum = num;
    }

    public int getLineNum() {
        return mLineNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initData();
        drawLines(canvas);
        drawChesses(canvas);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if(mEnabled){
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    handleActionDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initData() {
        int mBoardWidth = getWidth();
        int mBoardHeight = getHeight();
        mVerticalBlockNum = mLineNum -1;
        mHorizontalBlockNum = mLineNum -1;
        mVerticalLinePoints = new float[mLineNum * 4];
        mHorizontalLinePoints = new float[mLineNum * 4];
//        Log.d("lijia", "width:" + mBoardWidth + " height:" + mBoardHeight);
        chessBoardWidth = mBoardWidth > mBoardHeight ? mBoardHeight : mBoardWidth;
        chessBoardWidth = chessBoardWidth - MARGIN_EDGE * 2; //get width except the margin edge
        chessBoardWidth = (chessBoardWidth / (mLineNum - 1)) * (mLineNum - 1);// remove the edge
        mWidthBetweenLines = chessBoardWidth / (mLineNum - 1);
        mHeightBetweenLines = chessBoardWidth / (mLineNum - 1);
        initLinePoints();
    }

    private void handleActionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (checkTouchPosition(x, y)) {
            //convert coordinate to index
            int iX = (int) Math.rint((x - ChessBoardView.MARGIN_EDGE) / mWidthBetweenLines);
            int iY = (int) Math.rint((y - ChessBoardView.MARGIN_EDGE) / mHeightBetweenLines);
            mController.putChess(new Point(iX, iY), true);
        }
    }

    private void initLinePoints() {
        for (int i = 0; i < mLineNum * 4; i += 4) {
            mHorizontalLinePoints[i] = 0 + MARGIN_EDGE;
            mHorizontalLinePoints[i + 1] = (i / 4) * mHeightBetweenLines + MARGIN_EDGE;
            mHorizontalLinePoints[i + 2] = chessBoardWidth + MARGIN_EDGE;
            mHorizontalLinePoints[i + 3] = (i / 4) * mHeightBetweenLines + MARGIN_EDGE;
        }
        for (int i = 0; i < mLineNum * 4; i += 4) {
            mVerticalLinePoints[i] = (i / 4) * mWidthBetweenLines + MARGIN_EDGE;
            mVerticalLinePoints[i + 1] = 0 + MARGIN_EDGE;
            mVerticalLinePoints[i + 2] = (i / 4) * mWidthBetweenLines + MARGIN_EDGE;
            mVerticalLinePoints[i + 3] = chessBoardWidth + MARGIN_EDGE;
        }
        if (mLineNum == STANDARD_LINES) {
            mDrawExtraPoints = true;
        }
        mBlackPoints = new float[]{3 * mWidthBetweenLines + MARGIN_EDGE, 3 * mHeightBetweenLines + MARGIN_EDGE,
                (14 - 3) * mWidthBetweenLines + MARGIN_EDGE, 3 * mHeightBetweenLines + MARGIN_EDGE,
                3 * mWidthBetweenLines + MARGIN_EDGE, (14 - 3) * mHeightBetweenLines + MARGIN_EDGE,
                (14 - 3) * mWidthBetweenLines + MARGIN_EDGE, (14 - 3) * mHeightBetweenLines + MARGIN_EDGE};
    }

    private void drawLines(Canvas canvas) {
        canvas.drawLines(mHorizontalLinePoints, mLinePaint);
        canvas.drawLines(mVerticalLinePoints, mLinePaint);
        if (mDrawExtraPoints) {
            canvas.drawPoints(mBlackPoints, mPointsPaint);
        }
        canvas.drawPoint(mLineNum / 2 * mWidthBetweenLines + MARGIN_EDGE,
                mLineNum / 2 * mHeightBetweenLines + MARGIN_EDGE,
                mPointsPaint);// draw center point

    }

    private void drawChesses(Canvas canvas) {
        for (Chess chess : mController.getChessArray()) {// get chess data from controller
            // convert index to coordinate
            int coordinateX = chess.getIndexX() * mWidthBetweenLines + ChessBoardView.MARGIN_EDGE;
            int coordinateY = chess.getIndexY() * mHeightBetweenLines + ChessBoardView.MARGIN_EDGE;

            canvas.drawPoint(coordinateX, coordinateY, chess.getPaint());
        }
    }

    /**
     * Check if the given touch point is in the area of the chessboard
     * @param x x coordinate of the given point
     * @param y y coordinate of the given point
     * @return  return true if the point is in the chessboard
     */
    private boolean checkTouchPosition(float x, float y) {
        int iX = (int) Math.rint((x - MARGIN_EDGE) / mWidthBetweenLines);
        int iY = (int) Math.rint((y - MARGIN_EDGE) / mHeightBetweenLines);
        if (iX <= mVerticalBlockNum && iX >= 0 && iY >= 0 && iY <= mHorizontalBlockNum) {
            return true;
        }
        return false;

    }

    private boolean checkTouchPointsIndex(int x, int y) {
        if (x <= mVerticalBlockNum && x >= 0 && y >= 0 && y <= mHorizontalBlockNum) {
            return true;
        }
        return false;
    }



}
