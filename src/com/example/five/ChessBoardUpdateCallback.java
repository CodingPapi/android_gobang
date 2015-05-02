package com.example.five;

/**
 * Created by gaga on 15-5-1.
 * Callback for chessboard view
 */
public interface ChessBoardUpdateCallback {
    void onPutChessByUser(Chess chess);
    void onUserRegress(Chess chess);
    void onReset();


}
