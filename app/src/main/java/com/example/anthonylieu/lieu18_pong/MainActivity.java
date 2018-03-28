package com.example.anthonylieu.lieu18_pong;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**
 * MainActivity
 *
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 *
 * Enhancements to Part A:
 * Created a New Ball button. Once the ball goes off screen, the game will wait until the user
 * presses the New Ball button to start the game again with the ball starting at a random location
 * with a random velocity
 *
 * Created a Spinner to adjust the size of the paddle to change the difficulty level of the game.
 *
 * Enhancements to Part B:
 * 1) When the ball hits the paddle the speed of the ball increases at a random value from 1 to 5
 *    This makes the game harder as the player continues to score points.
 *
 * 2) I keep a running score of the game. The score increments by one (1) every time the player
 *    successfully hits the ball. There is a 3 point penalty for missing the ball.
 *    Running score is displayed on the top left corner of the board.
 *
 * 3) Each game also has a stock of 5 balls per game. Displayed on the board is the amount of
 *    remaining balls left that have yet been played. (Not including the current ball in play)
 *    The game is over once there are no more balls remaining and the last ball is out of bounds.
 *
 * 4) Added a Restart button to reload the game into its beginning state.
 *
 * @author Anthony Lieu
 * @version March 2018
 *
 */
public class MainActivity extends Activity {
    public boolean newBall = false;
    private String[] levels =
            {"Easy", "Medium", "Hard", "Insane"};
    public int difficultyLevels; // Used in Spinner to change the difficulty level
    public int ballLives = 5; // Number of balls each game has
    public int score; // The successful paddle hits in a game, subtracting the penalty for missing the paddle
    public boolean subScore = true; // Boolean for the penalty subtraction happened yet.
    public boolean clickedNewBall;

    /**
     * creates an AnimationSurface containing a MyAnimator.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the animation surface with the animator
        AnimationSurface mySurface = (AnimationSurface) this.findViewById(R.id.animationSurface);
        mySurface.setAnimator(new MyAnimator(this));

        // Button to create new ball when ball is not in okay anymore
        Button newBallButton =
                (Button) findViewById(R.id.newBall);
        newBallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clickedNewBall) {
                    clickedNewBall = true;
                    newBall = true;
                    ballLives--;
                    subScore = true;
                }
            }
        });

        // Button to restart the game. sets score to zero and ballLives to 5
        Button restartButton =
                (Button) findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBall = true;
                ballLives = 5;
                score = 0;
                subScore = true;
            }
        });

        // Spinner to change paddle size for different difficulties
        ArrayAdapter<String> spinAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, levels);
        Spinner difficulty =
                (Spinner) findViewById(R.id.difficultyLevel);
        difficulty.setAdapter(spinAdapter);
        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficultyLevels = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }
}

