package com.example.gobang.model;

import com.example.gobang.datastructure.Chess;

import java.util.ArrayList;

/**
 * Created by gaga on 15-6-16.
 */
public interface ThinkingBrain {
    /**
     * if there is a winner
     * @return 0 there is no winner
     *         1 black wins
     *         2 white wins
     */
    int winnerShowsUp();

    /**
     * AI thinking
     */
    void aiThinking(boolean isPut, Chess chess);

    void makeDecision(boolean isUser);
}
