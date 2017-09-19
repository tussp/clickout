package com.clickout.clickout;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ModeSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mode_select_activity);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/ubuntu_b.ttf");
        TextView mode_select_title = (TextView)findViewById(R.id.mode_select_title);
        mode_select_title.setTypeface(custom_font);

    }

}