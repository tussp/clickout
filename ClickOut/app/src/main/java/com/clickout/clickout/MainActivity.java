package com.clickout.clickout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Class selectedGameType = GameActivity.class;

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

        ImageButton btnSinglePlayer = (ImageButton) this.findViewById(R.id.btn_single_player);

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSinglePlayerGame();
            }
        });

        ImageButton btnMode = (ImageButton) this.findViewById(R.id.btn_mode);

        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectModeDialog();
            }
        });
    }

    private void showSelectModeDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        CharSequence items[] = new CharSequence[] {"Standard", "Advanced"};
        adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int n) {
                selectedGameType = n == 0 ? GameActivity.class : AdvancedGameActivity.class;
            }

        });

        adb.setPositiveButton("Ok", null);
        adb.setTitle("Which one?");
        adb.show();
    }

    private void navigateToSinglePlayerGame() {
        Intent intent = new Intent(this, this.selectedGameType);
        startActivity(intent);
    }
}
