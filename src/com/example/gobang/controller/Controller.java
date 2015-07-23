package com.example.gobang.controller;

import android.graphics.Point;
import com.example.gobang.datastructure.Chess;
import com.example.gobang.datastructure.ChessStore;
import com.example.gobang.datastructure.ChessUpdateCallback;
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
    }

    public Point getMaxPoint() {
        Point p = new Point(mChessBoard.getLineNum() - 1, mChessBoard.getLineNum() - 1);
        return p;
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

    public boolean putChess(Chess chess) {
        return mChessStore.putChess(chess);
    }

    public boolean popChess() {
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
        mChessBoard.regress();
    }



    private void init() {

    }


}
