package com.example.gobang.model;

import android.graphics.Point;
import android.util.ArrayMap;
import android.util.Log;
import com.example.gobang.controller.Controller;
import com.example.gobang.datastructure.Chess;
import com.example.gobang.datastructure.EmptySeat;

import java.util.*;

/**
 * Created by gaga on 15-7-15.
 */
public class DefaultSimpleAI implements ThinkingBrain {
    private final Controller mController;
    private ArrayMap<Point, EmptySeat> mStoreOfEmptySeats = new ArrayMap<Point, EmptySeat>();
    private final Comparator<EmptySeat> mRankingComparator = new Comparator<EmptySeat>() {

        @Override
        public int compare(EmptySeat a, EmptySeat b) {
            int aScore = a.getScore();
            int bScore = b.getScore();
            return aScore <= bScore ? -1 : 1;
        }
    };


    public DefaultSimpleAI(Controller controller) {
        mController = controller;
    }

    @Override
    public int winnerShowsUp() {
        return 0;
    }

    @Override
    public void aiThinking(boolean isUser, Chess chess) {
        mStoreOfEmptySeats.remove(chess.getPositionPoint());
        thinkingForComputeAffects(chess.getPositionPoint());
        makeDecision(isUser);
    }

    @Override
    public void makeDecision(boolean isUser) {
        if (mStoreOfEmptySeats.isEmpty()) {
//            mController.putChess()
        }
        Collection<EmptySeat> valueList = mStoreOfEmptySeats.values();
//        Collections.sort(valueList,mRankingComparator);
        int max = 0;
        EmptySeat tempSeat = null;
        for (EmptySeat seat : valueList) {
            final int score = seat.getScore();
            if (max < score) {
                max = score;
                tempSeat = seat;
            }
        }
        if (tempSeat != null) {
            if(isUser){
                mController.putChess(tempSeat.getPosition(), !isUser);
            }
        }
    }

    private void updateChessMap() {

    }

    private void thinkingForComputeAffects(Point point) {
        for (int i = 1; i <= 4; i++) {//four directions
            Point tempPoint = EmptySeat.getFirstPointInUpdateDirection(point, i, 4);
            for (int j = 1; j <= 9; j++) {//we need update 9 seats score in a direction
                if (!mStoreOfEmptySeats.containsKey(tempPoint)) {
                    boolean isEmpty = EmptySeat.seatIsEmpty(mController, new Chess(tempPoint));
                    if (isEmpty) {
                        mStoreOfEmptySeats.put(tempPoint, new EmptySeat(mController, tempPoint));
                    }
                } else {
                    mStoreOfEmptySeats.get(tempPoint).setDirectionToUpdate(i);
                }
                tempPoint = EmptySeat.getNextChess(tempPoint, i);
            }
        }
    }


}
