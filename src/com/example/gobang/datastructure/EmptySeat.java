package com.example.gobang.datastructure;

import android.graphics.Color;
import android.graphics.Point;

import java.util.PriorityQueue;

/**
 * Created by gaga on 15-7-15.
 */
public class EmptySeat {
    private int mIndexX;
    private int mIndexY;
    private int mScore;
    private int mScore1Clock;
    private int mScore3Clock;
    private int mScore5Clock;
    private int mScore6Clock;
    private Point mMaxPoint;
    private Point mMinPoint = new Point(0, 0);

    public EmptySeat(Point maxPoint) {
        mMaxPoint = maxPoint;
    }

    public int getScore() {
        mScore = mScore1Clock + mScore3Clock + mScore5Clock + mScore6Clock;
        return mScore;
    }

    public int cumputeScore() {
        return 0;
    }

    private Point getNextChess(Point p, int direction) {
        Point point = new Point(0, 0);
        switch (direction) {
            case 1://1clock direction
                point.set(p.x + 1, p.y - 1);
                break;
            case 2://3clock direction
                point.set(p.x + 1, p.y);
                break;
            case 3://5clock direction
                point.set(p.x + 1, p.y + 1);
                break;
            case 4://6clock direction
                point.set(p.x, p.y + 1);
                break;
            default:
                break;
        }
        return point;
    }

    private Point getFirstPoint(Point target, int direction) {
        Point firstPoint = new Point(0,0);
        int distance = 4;
        switch (direction) {
            case 1://1clock direction
                firstPoint.set(target.x - distance, target.y + distance);
                while (firstPoint.x < mMinPoint.x || firstPoint.y > mMaxPoint.y) {
                    firstPoint.x += 1;
                    firstPoint.y -= 1;
                }
                break;
            case 2://3clock direction
                firstPoint.set(target.x - distance, target.y);
                while (firstPoint.x < mMinPoint.x) {
                    firstPoint.x += 1;
                }
                break;
            case 3://5clock direction
                firstPoint.set(target.x - distance, target.y - distance);
                while (firstPoint.x < mMinPoint.x || firstPoint.y < mMinPoint.y) {
                    firstPoint.x += 1;
                    firstPoint.y += 1;
                }
                break;
            case 4://6clock direction
                firstPoint.set(target.x, target.y - distance);
                while (firstPoint.y < mMinPoint.y) {
                    firstPoint.y += 1;
                }
                break;
            default:
                break;
        }

        return firstPoint;
    }

}
