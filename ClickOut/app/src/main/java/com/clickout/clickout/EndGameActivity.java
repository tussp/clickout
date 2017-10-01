package com.clickout.clickout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {
    public Class selectedGameType = GameActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_end_game);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/ubuntu_b.ttf");
        TextView game_title = (TextView)findViewById(R.id.game_title);
        game_title.setTypeface(custom_font);

        Context c = getBaseContext();
        Animation rotate = AnimationUtils.loadAnimation(c, R.anim.rotate_replay);
        rotate.setInterpolator(new LinearInterpolator());

        ImageButton replay = (ImageButton)findViewById(R.id.btn_replay);
        replay.setAnimation(rotate);

        Score score = ScoreManager.getScore("GameScoreKey");

        TextView p1s = (TextView) findViewById(R.id.player_one_score);
        TextView p2s = (TextView) findViewById(R.id.player_two_score);

        p1s.setTypeface(custom_font);
        p1s.setText(String.valueOf(score.Player1));
        p2s.setTypeface(custom_font);
        p2s.setText(String.valueOf(score.Player2));


        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSinglePlayerGame();
            }
        });
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void navigateToSinglePlayerGame() {
        Intent intent = new Intent(this, this.selectedGameType);
        startActivity(intent);
        this.finish();
    }
}
