package com.example.anthonylieu.lieu18_pong;

import android.graphics.Paint;

/**
 * @author AnthonyLieu
 * @version March 2018
 *
 * This is Ball class for Pong
 * This handles ball parameters when new balls need to be created
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
