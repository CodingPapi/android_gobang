package com.example.five;

/**
 * Created by gaga on 15-5-1.
 * Controller
 */
public class Controller {
    private ChessBoardView mChessBoard;
    private Model mModel;
    private ChessBoardUpdateCallback mChessboardUpdateCallBack = new ChessBoardUpdateCallback() {
        @Override
        public void onPutChessByUser(Chess chess) {
            mModel.userPutAChess(chess);
        }

        @Override
        public void onUserRegress(Chess chess) {
            mModel.userRegress();
        }

        @Override
        public void onReset() {
            mModel.reset();
        }
    };

    private  ModelUpdateCallback mModelUpdateCallback = new ModelUpdateCallback() {
        @Override
        public void onUIPutChess(Chess chess) {
            mChessBoard.putChess(chess);
        }

        @Override
        public void onUIRegress() {
            mChessBoard.regress();
        }

        @Override
        public void onGameEnd() {
            mChessBoard.stop();
        }
    };

    public Controller(ChessBoardView chessBoardView) {
        mChessBoard = chessBoardView;
        mModel = new Model();
        mModel.setBoardEdgeX(mChessBoard.getVerticalBlockNum());
        mModel.setBoardEdgeY(mChessBoard.getHorizontalBlockNum());
        chessBoardView.setUpdateCallback(mChessboardUpdateCallBack);
        mModel.setUpdateCallback(mModelUpdateCallback);
    }

    public void start() {
        mChessBoard.start();
    }

    public void stop() {
        mChessBoard.stop();
    }

    public void reset() {
        mChessBoard.reset();
        mModel.reset();
    }



    private void init() {

    }


}
