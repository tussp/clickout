package com.clickout.clickout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Class selectedGameType = GameActivity.class;
    public static final String PREFS_NAME = "GameSettings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/ubuntu_b.ttf");
        TextView game_title = (TextView)findViewById(R.id.game_title);
        game_title.setTypeface(custom_font);

        //animated layout
        Context c = getBaseContext();
        Animation scale = AnimationUtils.loadAnimation(c, R.anim.scale);
        scale.setInterpolator(new LinearInterpolator());

        Animation rotate = AnimationUtils.loadAnimation(c, R.anim.rotate_play);
        rotate.setInterpolator(new LinearInterpolator());

        Animation rotate_back = AnimationUtils.loadAnimation(c, R.anim.rotate_backwards);
        rotate_back.setInterpolator(new LinearInterpolator());

        RelativeLayout spl = (RelativeLayout) this.findViewById(R.id.spl);

        ImageButton btnSinglePlayer = (ImageButton) this.findViewById(R.id.btn_single_player);

        spl.startAnimation(rotate);
        btnSinglePlayer.startAnimation(rotate_back);

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0,0);
                navigateToSinglePlayerGame();
            }
        });

        ImageButton btnMode = (ImageButton) this.findViewById(R.id.btn_mode);

        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToModeSelection();
            }
        });

        ImageButton btnSettings = (ImageButton) findViewById(R.id.btn_settings);
        ImageButton btnStats = (ImageButton) findViewById(R.id.btn_stats);
        ImageButton btnExit = (ImageButton) findViewById(R.id.btn_exit);

        btnSettings.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 navigateToSettings();
             }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToStats();
            }
        });


        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
                Runtime.getRuntime().exit(0);
                System.exit(0);
            }
        });

    }

  // private void showSelectModeDialog() {
  //     AlertDialog.Builder adb = new AlertDialog.Builder(this);
  //     CharSequence items[] = new CharSequence[] {"Standard", "Advanced"};
  //     adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
  //         @Override
  //         public void onClick(DialogInterface d, int n) {
  //             selectedGameType = n == 0 ? GameActivity.class : AdvancedGameActivity.class;
  //         }

  //     });

  //     adb.setPositiveButton("Ok", null);
  //     adb.setTitle("Which one?");
  //     adb.show();
  // }

    private void navigateToSinglePlayerGame() {
        Intent intent = new Intent(this, this.selectedGameType);
        startActivity(intent);
    }

    private void navigateToModeSelection() {
        Intent intent = new Intent(this, ModeSelectActivity.class);
        startActivity(intent);
    }

    private void navigateToStats() {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    private void navigateToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
