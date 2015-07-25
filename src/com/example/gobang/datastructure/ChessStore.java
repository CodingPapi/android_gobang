package com.example.gobang.datastructure;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by gaga on 15-5-24.
 */
public class ChessStore {
    private boolean DEBUG = true;
    private String TAG = "ChessStore";
    private ArrayList<Chess> mChessArray = new ArrayList<Chess>();
    private Stack<Chess> mChessStack = new Stack<Chess>();

    private static final int MSG_CHESS_UPDATE = 123;

    private static class MyHandler extends Handler {
        WeakReference<ChessStore> mParent;

        MyHandler(ChessStore store) {
            mParent = new WeakReference<ChessStore>(store);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHESS_UPDATE:
                    mParent.get().handleChessUpdate();
                    break;
            }
        }
    }

    private MyHandler myHandler = new MyHandler(this);

    private static ChessStore sChessStore;
    private final ArrayList<WeakReference<ChessUpdateCallback>>
            mListOfUpdateCallbacks = new ArrayList<WeakReference<ChessUpdateCallback>>();

    public static ChessStore getInstance() {
        if (sChessStore == null) {
            sChessStore = new ChessStore();
        }
        return sChessStore;
    }

    public void resetData() {
        mChessArray.clear();
        mChessStack.clear();
        myHandler.sendEmptyMessage(MSG_CHESS_UPDATE);
    }

    public Stack<Chess> getChessStack() {
        return mChessStack;
    }

    public ArrayList<Chess> getChessArray() {
        return mChessArray;
    }

    public int getColorOfLastChess() {
        return  mChessStack.empty() ? Color.WHITE : mChessStack.peek().getColor();
        //when the stack is empty,wo assume that last is white,so we can first put a black chess
    }

    public boolean putChess(Chess chess) {
        if (mChessArray.indexOf(chess) < 0) {
            mChessArray.add(chess);
            mChessStack.push(chess);
            myHandler.sendEmptyMessage(MSG_CHESS_UPDATE);
            return true;
        }
        return false;
    }

    public Chess popChess() {
        Chess chess = null;
        if (!mChessStack.empty()) {
            chess = mChessStack.pop();
            mChessArray.remove(chess);
            myHandler.sendEmptyMessage(MSG_CHESS_UPDATE);
        }
        return chess;
    }

    public void registerUpdateCallback(ChessUpdateCallback callback) {
        if (DEBUG) Log.v(TAG, "*** register callback for " + callback);
        // Prevent adding duplicate callbacks
        for (int i = 0; i < mListOfUpdateCallbacks.size(); i++) {
            if (mListOfUpdateCallbacks.get(i).get() == callback) {
                if (DEBUG) Log.e(TAG, "Object tried to add another callback",
                        new Exception("Called by"));
                return;
            }
        }
        mListOfUpdateCallbacks.add(new WeakReference<ChessUpdateCallback>(callback));
        removeCallback(null); // remove unused references
        sendUpdates(callback);
    }

    /**
     * Remove the given observer's callback.
     *
     * @param callback The callback to remove
     */
    public void removeCallback(ChessUpdateCallback callback) {
        if (DEBUG) Log.v(TAG, "*** unregister callback for " + callback);
        for (int i = mListOfUpdateCallbacks.size() - 1; i >= 0; i--) {
            if (mListOfUpdateCallbacks.get(i).get() == callback) {
                mListOfUpdateCallbacks.remove(i);
            }
        }
    }

    private void sendUpdates(ChessUpdateCallback callback) {
        // Notify listener of the current state
        callback.onChessUpdated();
    }

    private void handleChessUpdate() {
        for (int i = 0; i < mListOfUpdateCallbacks.size(); i++) {
            ChessUpdateCallback cb = mListOfUpdateCallbacks.get(i).get();
            if (cb != null) {
                cb.onChessUpdated();
            }
        }
    }




    private ChessStore() {

    }
}
