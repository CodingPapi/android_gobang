package com.example.gobang.controller;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import com.example.gobang.datastructure.Chess;
import com.example.gobang.datastructure.ChessStore;
import com.example.gobang.datastructure.ChessUpdateCallback;
import com.example.gobang.datastructure.EmptySeat;
import com.example.gobang.model.DefaultSimpleAI;
import com.example.gobang.model.Model;
import com.example.gobang.view.ChessBoardView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by gaga on 15-5-1.
 * Controller
 */
public class Controller {
    private ChessBoardView mChessBoard;
    private Model mModel;
    private ChessStore mChessStore;
    private EmptySeat mEmptySeat;
    private final Point mMinPoint = new Point(0, 0);
    private DefaultSimpleAI mSimpleAI;
    private ChessUpdateCallback mUpdateCallback = new ChessUpdateCallback() {
        @Override
        public void onChessUpdated() {
            mChessBoard.invalidate();
            mModel.updateChessData();
        }
    };

    public Controller(ChessBoardView chessBoardView) {
        mChessBoard = chessBoardView;
        mChessBoard.setLineNum(13);
        mChessBoard.setChessController(this);
        mModel = new Model(this);
        mChessStore = ChessStore.getInstance();
        mChessStore.registerUpdateCallback(mUpdateCallback);
        mSimpleAI = new DefaultSimpleAI(this);
    }

    public Point getMaxPoint() {
        Point p = new Point(mChessBoard.getLineNum(), mChessBoard.getLineNum());
        return p;
    }

    public Point getMinPoint() {
        return mMinPoint;
    }

    public int getColorOfLastChess() {
        return mChessStore.getColorOfLastChess();
    }

    public ArrayList<Chess> getChessArray() {
        return mChessStore.getChessArray();
    }

    public Stack<Chess> getChessStack() {
        return mChessStore.getChessStack();
    }

    public boolean putChess(Point point, boolean isUser) {
        Chess chess = new Chess(point, getColorOfLastChess() == Color.WHITE ? Color.BLACK : Color.WHITE);
        boolean result = mChessStore.putChess(chess);
        mSimpleAI.aiThinking(isUser,chess);
        return result;
    }

    public Chess popChess() {
        return mChessStore.popChess();
    }

    public void start() {
        mChessBoard.start();
    }

    public void stop() {
        mChessBoard.stop();
    }

    public void reset() {
        mChessStore.resetData();
        mModel.reset();
    }

    public void regress() {
        Chess chess = popChess();
        mSimpleAI.aiThinking(false, chess);
    }



    private void init() {

    }


}
