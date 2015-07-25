package com.example.gobang.datastructure;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import com.example.gobang.controller.Controller;
import com.example.gobang.model.ScoreTable;

import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by gaga on 15-7-15.
 */
public class EmptySeat {
    private Point mPosition;
    public int[] mScoreInDifferentDirections = new int[4];
    private Point mMaxPoint;
    private Point mMinPoint = new Point(0, 0);
    private Controller mController;
    private static ScoreTable mScoreTable = new ScoreTable();

    public EmptySeat(Controller controller,Point position) {
        setPosition(position);
        mMaxPoint = new Point(controller.getMaxPoint());
        mController = controller;
        computeScoreInAllDirections();
    }

    public void setPosition(Point p) {
        mPosition = new Point(p);
    }

    public Point getPosition() {
        return mPosition;
    }

    public int getScore() {
        int scoreSum = 0;
        for (int score : mScoreInDifferentDirections) {
            scoreSum += score;
        }
        return scoreSum;
    }

    public void setWhichPointChanged(Point pointChanged) {
        int direction = computeNeedsUpdateDirection(pointChanged);
        computeScoreInDirection(direction);
    }

    public void setDirectionToUpdate(int direction) {
        computeScoreInDirection(direction);
    }

    private void computeScoreInAllDirections() {
        for (int i = 1; i <= 4; i++) {
            computeScoreInDirection(i);
        }
    }

    private int computeScoreInDirection(int direction) {
        mScoreInDifferentDirections[direction - 1] = 0;
        Point firstPoint = getFirstPointInUpdateDirection(mPosition, direction, 4);
        Point tempPoint = new Point(firstPoint);
        for (int i = 0; i < 5; i++) {
            mScoreInDifferentDirections[direction - 1] += computeScoreInAFive(tempPoint, direction);
            tempPoint = getNextChess(tempPoint, direction);
        }
        return mScoreInDifferentDirections[direction - 1];
    }

    private int computeScoreInAFive(Point firstInFive, int direction) {
        int white = 0;
        int black = 0;
        Point tempPoint = new Point(firstInFive);
        for (int i = 0; i < 5; i++) {
            if (!seatInRegion(tempPoint, mMinPoint, mMaxPoint)) {
                return mScoreTable.getIvalidScore();
            }
            int color = getSeatColor(tempPoint);
            if (color == Color.WHITE) {
                white++;
            } else if (color == Color.BLACK) {
                black++;
            }
            tempPoint = getNextChess(tempPoint, direction);
        }

        return mScoreTable.getScore(white, black);

    }

    private int getSeatColor(Point point) {
        int color = Color.RED;//Invalid color
        int index = mController.getChessArray().indexOf(new Chess(point));
        if (index >= 0) {
            color = mController.getChessArray().get(index).getColor();
        }
        return color;
    }

    private static boolean seatInRegion(Point point,Point minPoint, Point maxPoint) {
        boolean inRegion = new Rect(minPoint.x, minPoint.y, maxPoint.x, maxPoint.y).contains(point.x, point.y);
        return inRegion;
    }

    public static boolean seatIsEmpty(Controller controller, Chess chess) {
        boolean inRegion = seatInRegion(chess.getPositionPoint(), controller.getMinPoint(), controller.getMaxPoint());
        return inRegion && controller.getChessArray().indexOf(chess) < 0;
    }

    private int computeNeedsUpdateDirection(Point pointChanged) {
        int direction = 0;
        int a = mPosition.x - pointChanged.x;
        int b = mPosition.y - pointChanged.y;
        if (a * b < 0) {
            direction = 1;//1clock direction
        } else if (a != 0 && b == 0) {
            direction = 2;//3clock direction
        } else if (a * b > 0) {
            direction = 3;//5clock direction
        } else if (a == 0 && b != 0) {
            direction = 4;//6clock direction
        }
        return direction;
    }

    public static Point getNextChess(Point p, int direction) {
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

    public static Point getFirstPointInUpdateDirection(Point target, int direction, int distance) {
        Point firstPoint = new Point(0, 0);
        switch (direction) {
            case 1://1clock direction
                firstPoint.set(target.x - distance, target.y + distance);
                break;
            case 2://3clock direction
                firstPoint.set(target.x - distance, target.y);
                break;
            case 3://5clock direction
                firstPoint.set(target.x - distance, target.y - distance);
                break;
            case 4://6clock direction
                firstPoint.set(target.x, target.y - distance);
                break;
            default:
                break;
        }
        return firstPoint;
    }

}
