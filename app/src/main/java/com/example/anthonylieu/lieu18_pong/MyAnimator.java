package com.example.anthonylieu.lieu18_pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import static com.example.anthonylieu.lieu18_pong.MyAnimator.Direction.*;

/**
 * @author AnthonyLieu
 * @version March 2018
 * This is the animator for the pong game. It handles the graphics and the animation
 * for the paddle as well as the ball
 */

public class MyAnimator implements Animator {

    // instance variables
    private Random gen = new Random(); // For random number generation

    private int dir; // Used later for new direction of ball

    private boolean ballOut = false; // Ball out of screen

    private Paddle humanPaddle; // stationary paddle
    private Ball ball; // ball

    private int radius = 50; // ball radius
    private int ballXSpd = 15;//gen.nextInt(20) + 10; // random ball x speed ranging from 10 to 20
    private int ballYSpd = 15;//gen.nextInt(20) + 10; // random ball y speed ranging from 10 to 20

    private int ballXDir = 1; // X Direction of Ball
    private int ballYDir = 1; // Y Direction of Ball

    private int ballXVel; // X Velocity of Ball
    private int ballYVel; // Y Velocity of Ball

    private int ballX; // Ball Current X Position
    private int ballY; // Ball Current Y Position

    private MainActivity pong_main; // for constructor

    private int paddleMid; // Middle of Paddle Y Coordinates

    private Direction direction = NW; // Direction Variable

    //private int ballLives = 2;


    /**
     * Constructor for MyAnimator
     */
    public MyAnimator (MainActivity pong_main) {
        this.pong_main = pong_main; // Grabs an instance of main activity to reference its variables
    }

    /**
     * Enum Definition for Direction Variables
     */
    public enum Direction {
        NW,
        NE,
        SW,
        SE
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

        int humanPaddleTop; // Top coordinate of paddle
        int humanPaddleBot; // Bottom coordinate of paddle

        // Paddle Motion Controls
        // Check if Paddle is at the top of the screen. If it is then stop it at the top
        if (paddleMid - paddleSize <= 30) {
            humanPaddleTop = 30;
            humanPaddleBot = paddleSize*2 + 30;
        }
        // Check if Paddle is at the bottom of the screen. If it is then stop it at the bottom
        else if (paddleMid + paddleSize >= boardHeight - 30) {
            humanPaddleTop = (boardHeight - 30) - (paddleSize * 2);
            humanPaddleBot = boardHeight - 30;
        }
        // Paddle Motion when in middle of screen not touching walls
        else {
            humanPaddleTop = paddleMid - paddleSize;
            humanPaddleBot = paddleMid + paddleSize;
        }

        int humanPaddleLeft = boardWidth - 60; // paddle left
        int humanPaddleRight = boardWidth - 30; // paddle right

        // Ball Velocity is calculated based on its speed and direction
        ballXVel = ballXDir * ballXSpd;
        ballYVel = ballYDir * ballYSpd;

        // Ball location is then determined by incrementing by its respective velocity
        ballX += ballXVel;
        ballY += ballYVel;

        // Determines how the ball moves depending on its direction
        switch (direction) {
            case NW:
                ballXDir = -1;
                ballYDir = -1;
                break;
            case NE:
                ballXDir = 1;
                ballYDir = -1;
                break;
            case SW:
                ballXDir = -1;
                ballYDir = 1;
                break;
            case SE:
                ballXDir = 1;
                ballYDir = 1;
                break;
            default:
                Log.i("Direction", "Something broke");
        }

        // Checks if Ball is out of bounds of the board
        if ((ballX - 60) > boardWidth) {
            ballOut = true;
        }
        // Check if ball hits top left corner
        else if (((ballX - 60) <= 30) && (ballY - 60) <= 30) {
            direction = SE;
        }
        // Check if ball hits bottom left corner
        else if (((ballX - 60) <= 30) && (ballY + 60) >= boardHeight - 30) {
            direction = NE;
        }
        // Checks if ball hits the paddle
        else if (((ballX + 60) >= humanPaddleLeft) &&
                  (ballY >= humanPaddleTop) && (ballY <= humanPaddleBot)) {
            if (direction == NE) {
                direction = NW;
                ballXSpd += gen.nextInt(5)+1; // Increase ballXSpd by a number from 1 to 5
                ballYSpd += gen.nextInt(5)+1; // Increase ballYSpd by a number from 1 to 5
                pong_main.score++;
            }
            if (direction == SE) {
                direction = SW;
                ballXSpd += gen.nextInt(5)+1;
                ballYSpd += gen.nextInt(5)+1;
                pong_main.score++;
            }
        }
        // Checks if ball hits the left wall
        else if ((ballX - 60) <= 30) {
            if (direction == NW) {
                direction = NE;
            }
            if (direction == SW) {
                direction = SE;
            }
        }
        // Checks if ball hits the bottom wall
        else if ((ballY + 60) >= boardHeight - 30) {
            if (direction == SW) {
                direction = NW;
            }
            if (direction == SE) {
                direction = NE;
            }
        }
        // Checks if ball hits top wall
        else if ((ballY - 60) <= 30) {
            if (direction == NW) {
                direction = SW;
            }
            if (direction == NE) {
                direction = SE;
            }
        }
        // move out of bounds ball to a new coordinate with a random location and velocity
        if (ballOut && pong_main.newBall && (pong_main.ballLives > 0)) {
            ballOut = false;
            pong_main.newBall = false;

            ballX = midWidth;
            ballY = gen.nextInt(boardHeight - 60) + 60;

            ballXDir = gen.nextBoolean() ? 1 : -1;
            ballYDir = gen.nextBoolean() ? 1 : -1;

            ballXSpd = gen.nextInt(20) + 15;
            ballYSpd = gen.nextInt(20) + 15;

            dir = gen.nextInt(3);
            if (dir == 0) {
                direction = NW;
            }
            else if (dir == 1) {
                direction = NE;
            }
            else if (dir == 2) {
                direction = SW;
            }
            else {
                direction = SE;
            }
        }
        else if (ballOut && !pong_main.newBall) {
            ballX = boardWidth + 70;
            ballY = boardHeight + 70;
            if (pong_main.subScore) {
                if (pong_main.score < 3) {
                    pong_main.score = 0;
                    pong_main.subScore = false;
                }
                else {
                    pong_main.score -= 3;
                    pong_main.subScore = false;
                }
            }
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


        /*
         * External Citation
         * Date: March 25, 2018
         * Problem: Did not know to to change font size
         * Resource: https://stackoverflow.com/questions/12166476/android-canvas-drawtext-set-font-size-from-width
         * Solution: I saw the usage of setTextSize in the link and used it myself.
         */

        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(40);
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
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        ball = new Ball(ballX, ballY, radius, redPaint);
        g.drawCircle(ball.ballCX, ball.ballCY, ball.ballRad, ball.ballColor);

        // Text and Ball Drawing for Remaining Ball Lives
        int i;
        for (i = 0; i < pong_main.ballLives - 1; i++) {
            g.drawText("Balls Remaining: ", midWidth + 20, 75, whitePaint);
            g.drawCircle(midWidth + 350 + (i * 50), 60, 20, whitePaint);
        }
        g.drawText("Score: " + String.valueOf(pong_main.score), 50, 70, whitePaint);

    }

    /*
     * External Citation
     * Date: March 25, 2018
     * Problem: I knew nothing about MotionEvent methods and wanted to use this to move the paddle
     * Resource: https://developer.android.com/reference/android/view/MotionEvent.html
     * Solution: ACTION_MOVE was exactly what I wanted to use for my paddle movement
     */

    @Override
    public void onTouch(MotionEvent event) {
        // Detect Pressed Screen Movement
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            paddleMid = (int) event.getY();
        }
    }
}
