package com.example.five;

/**
 * Created by gaga on 15-5-1.
 * Callback for chessboard view
 */
public interface ChessBoardUpdateCallback {
    void onPutChess(Chess chess);
    void onRemoveChess(Chess chess);
    void onReset();


}
