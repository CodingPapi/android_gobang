package com.example.five;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {
    private ChessBoardView mBoard;
    private Controller mController;
    private Button mButtonStart;
    private Button mButtonStop;
    private Button mButtonReset;
    private Button mButtonRegress;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mBoard = (ChessBoardView) findViewById(R.id.chessboard);
        mController = new Controller(mBoard);
        mButtonStart = (Button) findViewById(R.id.button_start);
        mButtonStop = (Button) findViewById(R.id.button_stop);
        mButtonReset = (Button) findViewById(R.id.button_reset);
        mButtonRegress = (Button) findViewById(R.id.button_regress);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.start();
            }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.stop();
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.reset();
            }
        });
        mButtonRegress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.regress();
            }
        });

    }
}
