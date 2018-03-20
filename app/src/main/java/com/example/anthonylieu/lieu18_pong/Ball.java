package com.example.anthonylieu.lieu18_pong;

import android.graphics.Paint;

/**
 * Created by AnthonyLieu on 3/19/18.
 */

public class Ball {
    Paint ballColor;
    int ballRad;
    int ballCX;
    int ballCY;

    Ball(int ballCX, int ballCY, int ballRad, Paint ballColor) {
        this.ballCX = ballCX;
        this.ballCY = ballCY;
        this.ballRad = ballRad;
        this.ballColor = ballColor;
    }
}
