package com.clickout.clickout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

public class GameActivity extends AppCompatActivity {
    private final String GameScoreKey = "GameScoreKey";

    private BoxView boxTop;
    private BoxView boxBottom;

    private int boxTopHeight;
    private int boxBottomHeight;
    private int heightStep;
    private boolean initialized;
    private boolean gameFinished;


    private int boxViewDefaultSize;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_activity);

        this.gameFinished = false;
        int screenHeight = ScreenUtil.getScreenHeight(this.getWindowManager());
        this.boxViewDefaultSize = screenHeight / 2;
        this.initialized = false;


        // TODO: do we need to divide the action bar height ?
//        int actionBarHeight = 0;
//        TypedValue tv = new TypedValue();
//        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
//        {
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
//        }
//        screenHeight -= actionBarHeight;

        this.boxTop = (BoxView) this.findViewById(R.id.box_top);
        this.boxTopHeight = screenHeight / 2;

        boxTop.setColor(getColor(R.color.playerOneColor));

        this.boxBottom = (BoxView) this.findViewById(R.id.box_bottom);
        this.boxBottomHeight = screenHeight / 2;

        boxBottom.setColor(getColor(R.color.playerTwoColor));


        boxTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initialized)
                    increaseTop();
            }
        });

        boxBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initialized)
                    increaseBottom();
            }
        });

        this.playInitialAnimations();
    }

    private void checkGameOver() {
        if (this.gameFinished) {
            return;
        }

        if (this.boxBottomHeight < 0) {
            this.gameFinished = true;

            Score score = ScoreManager.getScore(this.GameScoreKey);
            score.Player1 += 1;
            ScoreManager.updateScore(this.GameScoreKey, score);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Game Over");
            builder.setMessage("Player 1 win\nPlayer 1 Score - " + score.Player1 + "\nPlayer 2 Score - " + score.Player2);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("Ok", null);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();

            Intent intent = new Intent(this, EndGameActivity.class);
            startActivity(intent);
            this.finish();

        } else if (this.boxTopHeight < 0) {
            this.gameFinished = true;

            Score score = ScoreManager.getScore(this.GameScoreKey);
            score.Player2 += 1;
            ScoreManager.updateScore(this.GameScoreKey, score);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Game Over");
            builder.setMessage("Player 2 win\nPlayer 1 Score - " + score.Player1 + "\nPlayer 2 Score - " + score.Player2);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("Ok", null);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
            Intent intent = new Intent(this, EndGameActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private void playInitialAnimations() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, this.boxTopHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                boxTopHeight = value;
                boxBottomHeight = value;

                setNewHeight();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                showFingerCheck();
            }
        });

        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }


    private void increaseTop() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        this.heightStep = screenHeight / 8;

        this.genericAnimation(this.boxTopHeight, this.boxTopHeight += this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                checkGameOver();
                boxTopHeight = value;
                boxTop.setHeight(boxTopHeight);
            }
        });
        this.genericAnimation(this.boxBottomHeight, this.boxBottomHeight -= this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                checkGameOver();
                boxBottomHeight = value;
                boxBottom.setHeight(boxBottomHeight);
            }
        });
    }

    private void increaseBottom() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        this.heightStep = screenHeight / 8;

        this.genericAnimation(this.boxTopHeight, this.boxTopHeight -= this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                checkGameOver();

                boxTopHeight = value;
                boxTop.setHeight(boxTopHeight);
            }
        });
        this.genericAnimation(this.boxBottomHeight, this.boxBottomHeight += this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                checkGameOver();
                boxBottomHeight = value;
                boxBottom.setHeight(boxBottomHeight);
            }
        });
    }

    private void setNewHeight() {
        this.boxTop.setHeight(this.boxTopHeight);
        this.boxBottom.setHeight(this.boxBottomHeight);
    }

    private void genericAnimation(int oldValue, int newValue, final Func func) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(oldValue, newValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                func.execute(value);
            }
        });

        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    private void showFingerCheck() {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 300 milliseconds
        v.vibrate(300);

        FingerCheckDialog dialog = new FingerCheckDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                initialized = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        ExitGameCheckDialog.showDialog(this, new Func() {
            @Override
            public void execute(int value) {
                GameActivity.super.onBackPressed();
            }
        });
    }
}
