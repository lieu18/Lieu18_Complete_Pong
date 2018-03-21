package com.example.anthonylieu.lieu18_pong;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;


/**
 * MainActivity
 *
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 *
 * @author Anthony Lieu
 * @version March 2018
 *
 */
public class MainActivity extends Activity {
    public boolean newBall = false;
    private String[] levels =
            {"Easy", "Medium", "Hard", "Insane"};
    public int difficultyLevels;

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
                newBall = true;
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

