package com.example.anthonylieu.lieu18_pong;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author AnthonyLieu
 * @version March 2018
 *
 * This is the Paddle function
 * It handles the parameters for the paddle in the pong game.
 */

public class Paddle {

    Paint paddleColor;
    int paddleTopX;
    int paddleTopY;
    int paddleBotX;
    int paddleBotY;
    Rect paddleBounds;

    Paddle(int paddleTopX, int paddleTopY, int paddleBotX, int paddleBotY, Paint paddleColor) {
        this.paddleColor = paddleColor;
        this.paddleTopX = paddleTopX;
        this.paddleTopY = paddleTopY;
        this.paddleBotX = paddleBotX;
        this.paddleBotY = paddleBotY;
        this.paddleBounds = new Rect(paddleTopX, paddleTopY, paddleBotX, paddleBotY);

    }
}
