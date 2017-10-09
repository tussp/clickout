package com.clickout.clickout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class BusyIndicator extends Dialog {
    private ProgressBar progressBar;
    private int stringNumber = 0;
    private Handler handler;

    public BusyIndicator(final Context context) {
        super(context);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setContentView(R.layout.busy_indicator_layout);
        this.setCanceledOnTouchOutside(false);

        this.progressBar = (ProgressBar) this.findViewById(R.id.pb_busy);
        final TextSwitcher simpleTextSwitcher = (TextSwitcher) findViewById(R.id.ts_progress_messages);

        simpleTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(context);
                t.setTextColor(Color.WHITE);
                t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                t.setTextSize(30);
                return t;
            }
        });


        final String strings[] = {"Connecting .", "Connecting ..", "Connecting ..."};
        simpleTextSwitcher.setCurrentText("Connecting");

        Animation in = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        simpleTextSwitcher.setInAnimation(in);
        simpleTextSwitcher.setOutAnimation(out);

        handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            public void run() {
                if (stringNumber >= strings.length) {
                    stringNumber = 0;
                }
                simpleTextSwitcher.setText(strings[stringNumber]);
                stringNumber++;

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void hide() {
        super.hide();
        this.disposeHandler();
    }

    private void disposeHandler() {
        this.handler.removeCallbacksAndMessages(null);
    }
}
