package com.example.five;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by gaga on 15-4-18.
 * Chessboard Class
 */
public class ChessBoardView extends View {

    private int chessBoardWidth;
    private final static int STANDARD_LINES = 15; //This is a standard chessboard line number

    private final static int DIRECTION_LINES = 15; // We use this as chessboard line number
    public final static int MARGIN_EDGE = 30;
    private int mVerticalBlockNum = DIRECTION_LINES -1;
    private int mHorizontalBlockNum = DIRECTION_LINES -1;
    private int mWidthBetweenLines;
    private int mHeightBetweenLines;
    private float[] mVerticalLinePoints = new float[DIRECTION_LINES * 4];
    private float[] mHorizontalLinePoints = new float[DIRECTION_LINES * 4];
    private Paint mLinePaint = new Paint();
    private Paint mPointsPaint = new Paint();
    private float[] mBlackPoints; // five normal points on chess board
    private boolean mUserHandleWhiteNow;
    private boolean mDrawExtraPoints;
    private ArrayList<Chess> mChessArray = new ArrayList<Chess>();
    private Stack<Chess> mChessStack = new Stack<Chess>();
    private boolean mEnabled;

    private ChessBoardUpdateCallback mUpdateCallback;

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

    public void startOrStop(boolean enable) {
        mEnabled = enable;
    }

    public boolean regress() {
        if (!mChessStack.empty()) {
            Chess chess = mChessStack.pop();
            if (mUpdateCallback != null) {
                mUpdateCallback.onRemoveChess(chess);
            }
            mChessArray.remove(chess);
            invalidate();
            return true;
        }
        return false;
    }

    public boolean putChess(Chess chess) {
        return false;
    }

    public void setUpdateCallback(ChessBoardUpdateCallback callback) {
        mUpdateCallback = callback;
    }

    public void reset() {
        mChessArray.clear();
        mChessStack.clear();
        invalidate();
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
        chessBoardWidth = mBoardWidth > mBoardHeight ? mBoardHeight : mBoardWidth;
        chessBoardWidth = chessBoardWidth - MARGIN_EDGE * 2; //get width except the margin edge
        chessBoardWidth = (chessBoardWidth / (DIRECTION_LINES - 1)) * (DIRECTION_LINES - 1);// remove the edge
        mWidthBetweenLines = chessBoardWidth / (DIRECTION_LINES - 1);
        mHeightBetweenLines = chessBoardWidth / (DIRECTION_LINES - 1);
        initLinePoints();
    }

    private void handleActionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (checkTouchPosition(x, y)) {
            Chess chess = new Chess(x, y, mUserHandleWhiteNow, mWidthBetweenLines, mHeightBetweenLines);
            mChessArray.add(chess);
            mChessStack.push(chess);
            mUserHandleWhiteNow = !mUserHandleWhiteNow;
            if (mUpdateCallback != null) {
                mUpdateCallback.onPutChess(chess);
            }
            invalidate();
        }
    }

    private void initLinePoints() {
        for (int i = 0; i < DIRECTION_LINES * 4; i += 4) {
            mHorizontalLinePoints[i] = 0 + MARGIN_EDGE;
            mHorizontalLinePoints[i + 1] = (i / 4) * mHeightBetweenLines + MARGIN_EDGE;
            mHorizontalLinePoints[i + 2] = chessBoardWidth + MARGIN_EDGE;
            mHorizontalLinePoints[i + 3] = (i / 4) * mHeightBetweenLines + MARGIN_EDGE;
        }
        for (int i = 0; i < DIRECTION_LINES * 4; i += 4) {
            mVerticalLinePoints[i] = (i / 4) * mWidthBetweenLines + MARGIN_EDGE;
            mVerticalLinePoints[i + 1] = 0 + MARGIN_EDGE;
            mVerticalLinePoints[i + 2] = (i / 4) * mWidthBetweenLines + MARGIN_EDGE;
            mVerticalLinePoints[i + 3] = chessBoardWidth + MARGIN_EDGE;
        }
        if (DIRECTION_LINES == STANDARD_LINES) {
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
        canvas.drawPoint(DIRECTION_LINES / 2 * mWidthBetweenLines + MARGIN_EDGE,
                DIRECTION_LINES / 2 * mHeightBetweenLines + MARGIN_EDGE,
                mPointsPaint);// draw center point

    }

    private void drawChesses(Canvas canvas) {
//        while (!mChessStack.empty()) {
//            canvas.drawPoint(mChessStack.peek().getCoordinateX(), mChessStack.peek().getCoordinateY(), mChessStack.peek().getPaint());
//            mChessStack.pop();
//        }
        for (Chess chess : mChessArray) {
            canvas.drawPoint(chess.getCoordinateX(), chess.getCoordinateY(), chess.getPaint());
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
