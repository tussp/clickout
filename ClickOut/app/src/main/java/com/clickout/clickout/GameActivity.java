package com.clickout.clickout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

public class GameActivity extends AppCompatActivity {
    private BoxView boxTop;
    private BoxView boxBottom;

    private int boxTopHeight;
    private int boxBottomHeight;

    private int boxTopPadding;
    private int boxBottomPadding;

    private int boxViewDefaultSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_activity);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        this.boxViewDefaultSize = screenHeight / 2;

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
        this.boxTopPadding = screenHeight / 6;

        boxTop.setColor(Color.RED);
        boxTop.setText("Player 1");
        boxTop.setPadding(0, this.boxTopPadding, 0, 0);

        this.boxBottom = (BoxView) this.findViewById(R.id.box_bottom);
        this.boxBottomHeight = screenHeight / 2;
        this.boxBottomPadding = screenHeight / 6;

        boxBottom.setColor(Color.GREEN);
        boxBottom.setText("Player 2");
        boxBottom.setPadding(0, this.boxBottomPadding, 0, 0);


        boxTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseTop();
            }
        });

        boxBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseBottom();
            }
        });

        this.playInitialAnimations();
    }

    private void checkGameOver() {
        if (this.boxBottomHeight < 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Game Over");
            builder.setMessage("Player 1 win");
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            builder.show();

        } else if (this.boxTopHeight < 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Game Over");
            builder.setMessage("Player 2 win");
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            builder.show();
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

        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(900);
        valueAnimator.start();

    }

    private int heightStep = 168;
    private int paddingStep = 32;

    private void increaseTop(){
        this.genericAnimation(this.boxTopHeight, this.boxTopHeight += this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                boxTopHeight = value;
                boxTop.setHeight(boxTopHeight);
                checkGameOver();
            }
        });
        this.genericAnimation(this.boxBottomHeight, this.boxBottomHeight -= this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                boxBottomHeight = value;
                boxBottom.setHeight(boxBottomHeight);
                checkGameOver();
            }
        });

        this.genericAnimation(this.boxTopPadding, this.boxTopPadding += this.paddingStep, new Func() {
            @Override
            public void execute(int value) {
                boxTopPadding = value;
                boxTop.setPadding(0, boxTopPadding, 0, 0);
            }
        });

        this.genericAnimation(this.boxBottomPadding, this.boxBottomPadding += this.paddingStep, new Func() {
            @Override
            public void execute(int value) {
                boxBottomPadding = value;
                boxBottom.setPadding(0, boxBottomPadding, 0, 0);
            }
        });

    }

    private void increaseBottom(){
        this.genericAnimation(this.boxTopHeight, this.boxTopHeight -= this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                boxTopHeight = value;
                boxTop.setHeight(boxTopHeight);
                checkGameOver();
            }
        });
        this.genericAnimation(this.boxBottomHeight, this.boxBottomHeight += this.heightStep, new Func() {
            @Override
            public void execute(int value) {
                boxBottomHeight = value;
                boxBottom.setHeight(boxBottomHeight);
            }
        });

        this.genericAnimation(this.boxTopPadding, this.boxTopPadding -= this.paddingStep, new Func() {
            @Override
            public void execute(int value) {
                boxTopPadding = value;
                boxTop.setPadding(0, boxTopPadding, 0, 0);
            }
        });

        this.genericAnimation(this.boxBottomPadding, this.boxBottomPadding -= this.paddingStep, new Func() {
            @Override
            public void execute(int value) {
                boxBottomPadding = value;
                boxBottom.setPadding(0, boxBottomPadding, 0, 0);
            }
        });
    }

    private void setNewHeight() {
        this.boxTop.setHeight(this.boxTopHeight);
        this.boxBottom.setHeight(this.boxBottomHeight);

        this.boxTop.setPadding(0, this.boxTopPadding, 0, 0);
        this.boxBottom.setPadding(0, this.boxBottomPadding, 0, 0);
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

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }
}