package com.example.five;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by gaga on 15-4-29.
 */
public class Chess {
    private int mIndexX;
    private int mIndexY;
    private boolean mIsOccupiedByWhite;
    private boolean mIsOccupiedByBlack;
    private Paint mPaint = new Paint();

    public Chess(int indexX, int indexY, boolean colorWhite) {
        Log.d("lijia", "init chess x:" + indexX + " y:" + indexY + " color:" + colorWhite);
        setColorOfChess(colorWhite);
        setIndex(indexX, indexY);
    }

    private void setColorOfChess(boolean colorWhite) {
        mIsOccupiedByWhite = colorWhite;
        mIsOccupiedByBlack = !colorWhite;
        initPaint();
    }

    private void initPaint() {
        mPaint.setColor(mIsOccupiedByWhite ? Color.WHITE : Color.BLACK);
        mPaint.setStrokeWidth(30);
    }

    public Paint getPaint() {
        return mPaint;
    }

    public int getIndexX() {
        return mIndexX;
    }

    public int getIndexY() {
        return mIndexY;
    }

    public boolean isOccupied() {
        return mIsOccupiedByBlack || mIsOccupiedByWhite;
    }

    public boolean isBlack() {
        return mIsOccupiedByBlack;
    }

    public boolean isWhite() {
        return mIsOccupiedByWhite;
    }

    private void setIndex(int x, int y) {
        mIndexX = x;
        mIndexY = y;
    }

    @Override
    public String toString() {
//        return "IndexX: " + mIndexX + " IndexY: " + mIndexY + "CoordinateX: " + mCoordinateX + " CoordinateY: " + mCoordinateY;
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chess) {
            return (((Chess) o).getIndexX() == mIndexX && ((Chess) o).getIndexY() == mIndexY);
        }
        return false;
    }
}
