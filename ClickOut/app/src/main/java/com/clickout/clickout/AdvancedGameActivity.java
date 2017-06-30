package com.clickout.clickout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AdvancedGameActivity extends AppCompatActivity {
    private ArrayList<BoxView> boxViewsTop;
    private ArrayList<BoxView> boxViewsBottom;

    private LinearLayout topContainer;
    private LinearLayout bottomContainer;
    private RelativeLayout relativeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_game);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int screenHeight = ScreenUtil.getScreenHeight(this.getWindowManager());
        this.heightStep = screenHeight / 17;
        int containerHeight = screenHeight / 2;

//        this.topContainer = (LinearLayout) this.findViewById(R.id.container_top);
//        this.bottomContainer = (LinearLayout) this.findViewById(R.id.container_bottom);
//        this.topContainer.getLayoutParams().height = containerHeight;
//        this.bottomContainer.getLayoutParams().height = containerHeight;

        this.relativeContainer = (RelativeLayout) this.findViewById(R.id.relative_container);
        this.relativeContainer.setBackgroundColor(Color.GREEN);

        this.boxViewsBottom = new ArrayList<BoxView>();
        this.boxViewsTop = new ArrayList<BoxView>();

        int currentId = -1;
        for (int i = 0; i < 20; i += 1) {
            BoxView boxView = new BoxView(this);
            boxView.setId(View.generateViewId());
            boxView.setColor(Color.RED);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, containerHeight);
            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, currentId);
            }
            boxView.setLayoutParams(params);
            final int currentIndex = i;
            boxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseTop(currentIndex);
                }
            });

            this.boxViewsTop.add(boxView);
            this.relativeContainer.addView(boxView);
            currentId = boxView.getId();
        }

        for (int i = 0; i < 20; i += 1) {
            BoxView boxView = new BoxView(this);
            boxView.setId(View.generateViewId());
            boxView.setColor(Color.BLUE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, containerHeight);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, currentId);
            }
            boxView.setLayoutParams(params);
            final int currentIndex = i;
            boxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseBottom(currentIndex);
                }
            });

            this.boxViewsBottom.add(boxView);
            this.relativeContainer.addView(boxView);
            currentId = boxView.getId();
        }
    }

    private int heightStep;

    private void increaseTop(int index) {
        BoxView topView = this.boxViewsTop.get(index);
        int topHeight = topView.getHeight();
        RelativeLayout.LayoutParams topParams = (RelativeLayout.LayoutParams) topView.getLayoutParams();
        topHeight += this.heightStep;
        topParams.height = topHeight;
        topView.setLayoutParams(topParams);

        BoxView bottomView = this.boxViewsBottom.get(index);
        int bottomHeight = bottomView.getHeight();
        RelativeLayout.LayoutParams bottomParams = (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
        bottomHeight -= this.heightStep;
        bottomParams.height = bottomHeight;
        bottomView.setLayoutParams(bottomParams);

    }

    private void increaseBottom(int index) {
        BoxView topView = this.boxViewsTop.get(index);
        int topHeight = topView.getHeight();
        RelativeLayout.LayoutParams topParams = (RelativeLayout.LayoutParams) topView.getLayoutParams();
        topHeight -= this.heightStep;
        topParams.height = topHeight;
        topView.setLayoutParams(topParams);

        BoxView bottomView = this.boxViewsBottom.get(index);
        int bottomHeight = bottomView.getHeight();
        RelativeLayout.LayoutParams bottomParams = (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
        bottomHeight += this.heightStep;
        bottomParams.height = bottomHeight;
        bottomView.setLayoutParams(bottomParams);
    }
}
