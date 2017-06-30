package com.clickout.clickout;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/ubuntu_b.ttf");
        TextView game_title = (TextView)findViewById(R.id.game_title);
        game_title.setTypeface(custom_font);

        Button btnSinglePlayer = (Button) this.findViewById(R.id.btn_single_player);
        btnSinglePlayer.setTypeface(custom_font);

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSinglePlayerGame();
            }
        });
    }

    private void navigateToSinglePlayerGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
