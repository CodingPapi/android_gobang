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
    private float mCoordinateX;
    private float mCoordinateY;
    private boolean mIsOccupiedByWhite;
    private boolean mIsOccupiedByBlack;
    private int mWidthBetweenLines;
    private int mHeightBetweenLines;
    private Paint mPaint = new Paint();

    public Chess(int indexX, int indexY, boolean colorWhite, int perWidth, int perHeight) {
        mWidthBetweenLines = perWidth;
        mHeightBetweenLines = perHeight;
        setColorOfChess(colorWhite);
        setIndex(indexX, indexY);
    }

    public Chess(float x, float y, boolean colorWhite, int perWidth, int perHeight) {
        Log.d("lijia", "init chess x:" + x + " y:" + y + " color:" + colorWhite + " perwidth:" + perHeight);
        mWidthBetweenLines = perWidth;
        mHeightBetweenLines = perHeight;
        setColorOfChess(colorWhite);
        setCoordinate(x, y);
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

    public float getCoordinateX() {
        return mCoordinateX;
    }

    public float getCoordinateY() {
        return mCoordinateY;
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

    public void setCoordinate(float x, float y) {
        int iX = (int) Math.rint((x - ChessBoardView.MARGIN_EDGE) / mWidthBetweenLines);
        int iY = (int) Math.rint((y - ChessBoardView.MARGIN_EDGE) / mHeightBetweenLines);
        mCoordinateX = iX * mWidthBetweenLines + ChessBoardView.MARGIN_EDGE;
        mCoordinateY = iY * mHeightBetweenLines + ChessBoardView.MARGIN_EDGE;
        mIndexX = iX;
        mIndexY = iY;
    }

    private void setIndex(int x, int y) {
        mIndexX = x;
        mIndexY = y;
        mCoordinateX = x * mWidthBetweenLines + ChessBoardView.MARGIN_EDGE;
        mCoordinateY = y * mHeightBetweenLines + ChessBoardView.MARGIN_EDGE;
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
