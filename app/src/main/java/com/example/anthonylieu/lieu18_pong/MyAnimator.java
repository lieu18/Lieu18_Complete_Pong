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

    // instance variables
    private Random gen = new Random(); // For random number generation

    private int countX = 60; // starting X position of Ball
    private int countY = 60; // starting Y position of Ball
    private int dir; // Used later for new direction of ball

    private boolean NW = true; // NorthWest Movement
    private boolean NE = false; // NorthEast Movement
    private boolean SW = false; // SouthWest Movement
    private boolean SE = false; // SouthEast Movement

    private boolean ballOut = false; // Ball out of screen

    private Paddle humanPaddle; // stationary paddle
    private Ball ball; // ball

    private int radius = 50; // ball radius
    private int ballXSpd = 15;//gen.nextInt(20) + 10; // random ball x speed ranging from 10 to 20
    private int ballYSpd = 15;//gen.nextInt(20) + 10; // random ball y speed ranging from 10 to 20
    private MainActivity pong_main; // for constructor

    /*
     *
     */
    public MyAnimator (MainActivity pong_main) {
        this.pong_main = pong_main; // Grabs an instance of main activity to reference its variables
    }




    /**
     * Interval between animation frames: .001 seconds
     *
     * @return the time interval between frames, in milliseconds.
     */
    @Override
    public int interval() {
        return 1;
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

        int boardHeight = g.getHeight(); // board height dimensions
        int boardWidth = g.getWidth(); // board width dimensions
        int midHeight = g.getHeight()/2; // middle board height coordinate
        int midWidth = g.getWidth()/2; // middle board width coordinate

        int paddleSize; // Paddle Size

        if (pong_main.difficultyLevels == 0) { // Easy Level
            paddleSize = 200;
        }
        else if (pong_main.difficultyLevels == 1) { // Medium Level
            paddleSize = 150;
        }
        else if (pong_main.difficultyLevels == 2) { // Hard Level
            paddleSize = 100;
        }
        else { // Insane Level
            paddleSize = 50;
        }

        int humanPaddleLeft = boardWidth - 60; // paddle left
        int humanPaddleTop = midHeight - paddleSize; // paddle top
        int humanPaddleRight = boardWidth - 10; // paddle right
        int humanPaddleBot = midHeight + paddleSize; // paddle bottom


        // Multiplying countX or countY changes the speed of the ball in its respected X or Y direction
        int ballX = (countX*ballXSpd);
        int ballY = (countY*ballYSpd);



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


        // Checks if Ball is out of bounds of the board
        if ((ballX - 60) > boardWidth) {
            ballOut = true;
        }
        // Check if ball hits top left corner
        else if (((ballX - 60) <= 30) && (ballY - 60) <= 30) {
            NW = false;
            NE = false;
            SW = false;
            SE = true;
        }
        // Check if ball hits bottom left corner
        else if (((ballX - 60) <= 30) && (ballY + 60) >= boardHeight - 30) {
            NW = false;
            NE = true;
            SW = false;
            SE = false;
        }
        // Checks if ball hits the paddle
        else if (((ballX + 60) >= humanPaddleLeft) &&
                  (ballY >= humanPaddleTop) && (ballY <= humanPaddleBot)) {
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
        // Checks if ball hits the left wall
        else if ((ballX - 60) <= 30) {
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
        // Checks if ball hits the bottom wall
        else if ((ballY + 60) >= boardHeight - 30) {
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
        // Checks if ball hits top wall
        else if ((ballY - 60) <= 30) {
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
        // move out of bounds ball to a new coordinate with a random location and velocity
        if (ballOut && pong_main.newBall) {
            ballOut = false;
            pong_main.newBall = false;

            countX = 60;
            countY = 60;
            ballXSpd = gen.nextInt(20) + 5;
            ballYSpd = gen.nextInt(20) + 5;

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
        else if (ballOut && !pong_main.newBall) {
            ballX = boardWidth + 70;
            ballY = boardHeight + 70;
        }

        /*
         * External Citation
         * Date: March 18, 2018
         * Problem: Wanted to find a better way to created a Dashed Line Effect
         * Resource: https://stackoverflow.com/questions/16528572/draw-dash-line-on-a-canvas
         * Solution: I used this example to create the dashed line effect
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

        // Green Ball
        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        ball = new Ball(ballX, ballY, radius, greenPaint);
        g.drawCircle(ball.ballCX, ball.ballCY, ball.ballRad, ball.ballColor);


    }

    @Override
    public void onTouch(MotionEvent event) {
        // do nothing for now
    }
}
