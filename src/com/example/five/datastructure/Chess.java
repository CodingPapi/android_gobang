package com.example.five.datastructure;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by gaga on 15-4-29.
 */
public class Chess {
    private int mIndexX;
    private int mIndexY;
    private int mColor = Color.WHITE;//default white
    private Paint mPaint = new Paint();

    public Chess(int indexX, int indexY, int color) {
//        Log.d("lijia", "init chess x:" + indexX + " y:" + indexY + " color:" + colorWhite);
        mColor = color;
        initPaint();
        setIndex(indexX, indexY);
    }

    private void initPaint() {
        mPaint.setColor(mColor);
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

    public int getColor() {
        return mColor;
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
