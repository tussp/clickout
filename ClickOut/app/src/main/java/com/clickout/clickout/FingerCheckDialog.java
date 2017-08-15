package com.clickout.clickout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;

public class FingerCheckDialog extends Dialog {
    ImageButton topButton;
    ImageButton bottomButton;
    TextView fingerCheckCounterLabel;
    TextView fingerCheckDefaultMessageLabel;
    private boolean topFingerDown = false;
    private boolean bottomFingerDown = false;
    private Activity activity;

    public FingerCheckDialog(Activity acitvity) {
        super(acitvity);
        this.activity = acitvity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fingercheck_dialog_layout);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.fingerCheckCounterLabel = (TextView) this.findViewById(R.id.fingercheck_counter);
        this.topButton = (ImageButton) this.findViewById(R.id.btn_top_finger);
        this.bottomButton = (ImageButton) this.findViewById(R.id.btn_bottom_finger);
        this.fingerCheckDefaultMessageLabel = (TextView) this.findViewById(R.id.fingercheck_default_message_label);

        this.topButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    topFingerDown = true;
                    checkFingers();
                    return false;
                }

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    topFingerDown = false;
                    checkFingers();
                    return false;
                }

                return false;
            }
        });

        this.bottomButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    bottomFingerDown = true;
                    checkFingers();
                    return false;
                }

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    bottomFingerDown = false;
                    checkFingers();
                    return false;
                }

                return false;
            }
        });

        this.timer = new CountDownTimer(6000, 700) {
            @Override
            public void onTick(long l) {
                myCounter -= 1;
                fingerCheckCounterLabel.setText(Integer.toString(myCounter));
                if (myCounter == 0) {
                    Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    dismiss();
                }
            }

            @Override
            public void onFinish() {
            }
        };
    }

    private int myCounter = 4;
    private CountDownTimer timer;

    private void checkFingers() {
        if (this.topFingerDown && this.bottomFingerDown) {
            this.timer.start();
            this.fingerCheckDefaultMessageLabel.setVisibility(View.INVISIBLE);
        } else {
            this.fingerCheckDefaultMessageLabel.setVisibility(View.VISIBLE);
            this.fingerCheckCounterLabel.setText("");
            this.timer.cancel();
            this.myCounter = 4;
        }
    }

    @Override
    public void dismiss() {
        this.activity = null;
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.activity.finish();
            this.dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }
}
