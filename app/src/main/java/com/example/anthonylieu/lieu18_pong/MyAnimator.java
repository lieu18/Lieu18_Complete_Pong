package com.example.anthonylieu.lieu18_pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by AnthonyLieu on 3/19/18.
 */

public class MyAnimator implements Animator {

    private Random gen = new Random();

    // Mine
    // TODO: Random Ball Starting position and direction, but must start in middle like actual game
    // TODO: Random Ball Start speed and decaying ball speed
    private int countX = 60; // starting X position of Ball
    private int countY = 60; // starting Y position of Ball
    private int dir;

    private boolean NW = true;
    private boolean NE = false;
    private boolean SW = false;
    private boolean SE = false;

    private boolean ballOut = false;

    private Paddle humanPaddle;
    private Ball ball;

    private int radius = 50;
    private int ballXSpd = gen.nextInt(15) + 5;
    private int ballYSpd = gen.nextInt(15) + 5;



    /**
     * Interval between animation frames: .01 seconds
     *
     * @return the time interval between frames, in milliseconds.
     */
    @Override
    public int interval() {
        return 10;
    }

    /**
     * The background color: Black.
     *
     * @return the background color onto which we will draw the image.
     */
    @Override
    public int backgroundColor() {
        return Color.rgb(0, 0, 0);
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    @Override
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    @Override
    public boolean doQuit() {
        return false;
    }

    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    @Override
    public void tick(Canvas g) {

        int boardHeight = g.getHeight();
        int boardWidth = g.getWidth();
        int midHeight = g.getHeight()/2;
        int midWidth = g.getWidth()/2;

        int humanPaddleLeft = boardWidth - 60;
        int humanPaddleTop = midHeight - 150;
        int humanPaddleRight = boardWidth - 10;
        int humanPaddleBot = midHeight + 150;

        // Multiplying countX or countY changes the speed of the ball in its respected X or Y direction
        // % by the board dimensions loops the ball back into the board.
        // Don't need the % to loop the ball back anymore.
        int ballX = (countX*ballXSpd);    //%boardWidth;
        int ballY = (countY*ballYSpd);    //%boardHeight;


        // Determines how the ball moves depending on its direction
        if (NW) {
            countX--;
            countY--;
        }
        if (NE) {
            countX++;
            countY--;
        }
        if (SW) {
            countX--;
            countY++;
        }
        if (SE) {
            countX++;
            countY++;
        }


       // if (ballX < 0) ballX += boardWidth;
       // if (ballY < 0) ballY += boardHeight;

        // This causes a slight bug in the sense that the ball will be past the board's dimensions but proceeds
        // bounce back once it's y coordinates matches.
        // I should check for the dimensions to be on the screen. If they aren't then the game should stop until
        // a new ball is somehow introduced.
        // TODO: Once ball disappears a new ball should be randomly generated to replace it.


        if ((ballX - 60) > boardWidth) {
            ballOut = true;
        }

        else if (((ballX + 60) >= humanPaddleLeft) && (ballY >= humanPaddleTop) && (ballY <= humanPaddleBot)) {
            if (NE) {
                NW = true;
                NE = false;
                SW = false;
                SE = false;
            }
            if (SE) {
                NW = false;
                NE = false;
                SW = true;
                SE = false;
            }
        }

        else if ((ballX - 60) <= 30) { // hits left side
            if (NW) {
                NW = false;
                NE = true;
                SW = false;
                SE = false;
            }
            if (SW) {
                NW = false;
                NE = false;
                SW = false;
                SE = true;
            }
        }
        else if ((ballY + 60) >= boardHeight - 30) { // hits bottom
            if (SW) {
                NW = true;
                NE = false;
                SW = false;
                SE = false;
            }
            if (SE) {
                NW = false;
                NE = true;
                SW = false;
                SE = false;
            }
        }
        else if ((ballY - 60) <= 30) { // hits top
            if (NW) {
                NW = false;
                NE = false;
                SW = true;
                SE = false;
            }
            if (NE) {
                NW = false;
                NE = false;
                SW = false;
                SE = true;
            }
        }

        if (ballOut) {
            ballOut = false;
            countX = 60;
            countY = 60;
            ballXSpd = gen.nextInt(15) + 5;
            ballYSpd = gen.nextInt(15) + 5;
            dir = gen.nextInt(3);
            if (dir == 0) {
                NW = true;
                NE = false;
                SW = false;
                SE = false;
            }
            else if (dir == 1) {
                NW = false;
                NE = true;
                SW = false;
                SE = false;
            }
            else if (dir == 2) {
                NW = false;
                NE = false;
                SW = true;
                SE = false;
            }
            else {
                NW = false;
                NE = false;
                SW = false;
                SE = true;
            }
        }

        /*
		 * Cite This
		 * https://stackoverflow.com/questions/16528572/draw-dash-line-on-a-canvas
		 */
        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStrokeWidth(10f);
        // Middle segmented yellow line to divide the board in half.
        yellowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        yellowPaint.setPathEffect(new DashPathEffect(new float[] {50,10}, 0));
        g.drawLine(midWidth, 0f, midWidth, boardHeight, yellowPaint);



        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        // Three white "walls" of the game.
        g.drawRect(0f, 0f, boardWidth, 30f, whitePaint); // top
        g.drawRect(0f, (boardHeight - 30), boardWidth, boardHeight, whitePaint); // bottom
        g.drawRect(0f, 0f, 30f, boardHeight, whitePaint); // right



        // Blue paddle on the left side
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        humanPaddle = new Paddle(humanPaddleLeft, humanPaddleTop, humanPaddleRight, humanPaddleBot, bluePaint);
        g.drawRect(humanPaddle.paddleBounds, humanPaddle.paddleColor);


        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        ball = new Ball(ballX, ballY, radius, greenPaint);
        g.drawCircle(ball.ballCX, ball.ballCY, ball.ballRad, ball.ballColor);


    }

    /**
     *
     */
    @Override
    public void onTouch(MotionEvent event) {

    }
}
