package com.example.gobang.model;

/**
 * Created by gaga on 15-7-24.
 */
public class ScoreTable {
    private int[] mTupleScoreTable = new int[11];

    public ScoreTable() {
        mTupleScoreTable[0] = 7;// blank
        mTupleScoreTable[1] = 35;//B
        mTupleScoreTable[2] = 800;//BB
        mTupleScoreTable[3] = 15000;//BBB
        mTupleScoreTable[4] = 800000;//BBBB
        mTupleScoreTable[5] = 15;//W
        mTupleScoreTable[6] = 400;//WW
        mTupleScoreTable[7] = 1800;//WWW
        mTupleScoreTable[8] = 100000;//WWWW
        mTupleScoreTable[9] = 0;//VIRTUAL
        mTupleScoreTable[10] = 0;//POLLUTED
    }

    public int getScore(int whiteCount, int blackCount) {
        if (whiteCount == 0) {
            return mTupleScoreTable[blackCount];
        } else if (blackCount == 0) {
            return mTupleScoreTable[whiteCount + 4];
        } else {
            return mTupleScoreTable[10];
        }
    }

    public int getIvalidScore() {
        return mTupleScoreTable[9];//0
    }

}
