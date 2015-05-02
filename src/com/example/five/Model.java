package com.example.five;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by gaga on 15-5-1.
 * Model
 */
public class Model {
    private ModelUpdateCallback mUpdateCallback;
    private Stack<Chess> mChessStack = new Stack<Chess>();
    private ArrayList<Chess> mChessArray = new ArrayList<Chess>();
    private boolean mUserHandleWhiteNow;
    private int mBoardEdgeX;
    private int mBoardEdgeY;
    private Controller mController;



    public Model(Controller controller) {
        mController = controller;

    }

    public void setUpdateCallback(ModelUpdateCallback callback) {
        mUpdateCallback = callback;
    }

    public void setBoardEdgeX(int x) {
        mBoardEdgeX = x;
    }

    public void setBoardEdgeY(int y) {
        mBoardEdgeY = y;
    }

    public void userPutAChess(Chess chess) {
        mChessStack.push(chess);
        mChessArray.add(chess);
        computerThinking();

    }

    public void userRegress() {
        if(!mChessStack.empty()){
            Chess chess = mChessStack.pop();
            mChessArray.remove(chess);
//        computerThinking();
        }

    }

    public void reset() {
        mChessStack.clear();
        mChessArray.clear();
    }

    private void computerThinking() {

    }



    private void init() {

    }
}
