package com.example.anthonylieu.lieu18_pong;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by AnthonyLieu on 3/19/18.
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
