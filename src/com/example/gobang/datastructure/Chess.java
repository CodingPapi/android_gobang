package com.example.gobang.datastructure;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by gaga on 15-4-29.
 */
public class Chess {
    private int mIndexX;
    private int mIndexY;
    private Point mPosition = new Point();
    private int mColor = Color.WHITE;//default white
    private Paint mPaint = new Paint();

    public Chess(Point point) {
        this(point, Color.RED);
    }

    public Chess(Point point, int color) {
        this(point.x, point.y, color);
    }
    public Chess(int indexX, int indexY, int color) {
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
        mPosition.set(x, y);
    }

    public Point getPositionPoint() {
        return mPosition;
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
