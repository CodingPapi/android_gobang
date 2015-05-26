package com.example.gobang.model;

import com.example.gobang.controller.Controller;

/**
 * Created by gaga on 15-5-1.
 * Model
 */
public class Model {
    private boolean mUserHandleWhiteNow;
    private int mBoardEdgeX;
    private int mBoardEdgeY;
    private Controller mController;


    public Model(Controller controller) {
        mController = controller;

    }

    public void setBoardEdgeX(int x) {
        mBoardEdgeX = x;
    }

    public void setBoardEdgeY(int y) {
        mBoardEdgeY = y;
    }

    public void updateChessData() {

    }

    public void reset() {

    }

    private void computerThinking() {

    }


    private void init() {

    }
}
