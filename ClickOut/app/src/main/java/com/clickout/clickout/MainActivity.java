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
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Class selectedGameType = GameActivity.class;

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
        Animation rotate = AnimationUtils.loadAnimation(c, R.anim.rotate);
        rotate.setInterpolator(new LinearInterpolator());
        LinearLayout spl = (LinearLayout) this.findViewById(R.id.spl);
        spl.startAnimation(scale);

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

        ImageButton btnSettings = (ImageButton) findViewById(R.id.btn_settings);
        ImageButton btnStats = (ImageButton) findViewById(R.id.btn_stats);
        ImageButton btnExit = (ImageButton) findViewById(R.id.btn_exit);

        btnSettings.startAnimation(rotate);
        btnStats.startAnimation(rotate);
        btnExit.startAnimation(rotate);

        btnSettings.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 navigateToSettings();
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

 // public void onResume(){
 //     super.onResume();
//
 // int x = ScreenUtil.getScreenWidth(this.getWindowManager())+450;
 //   int y = ScreenUtil.getScreenHeight(this.getWindowManager())+450;
//
 //   final ParticleSystem ps1 = new ParticleSystem(this, 50000, R.drawable.ic_confetti_one,10000, R.id.emiter);
 //       ps1.setSpeedModuleAndAngleRange(0.3f, 0.3f, 70, 70);
 //       ps1.setAcceleration(0.00025f,180);
 //       ps1.setScaleRange(0.5f, 1.5f);
 //       ps1.emit(x,-450,1);
//
 //   final ParticleSystem ps4 = new ParticleSystem(this, 50000, R.drawable.ic_confetti_two,10000, R.id.emiter);
 //       ps4.setSpeedModuleAndAngleRange(0.3f, 0.3f, 250, 250);
 //       ps4.setAcceleration(0.00025f,0);
 //       ps4.setScaleRange(0.5f, 1.5f);
 //       ps4.emit(-450,y,1);
//
 // }

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

    private void navigateToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
