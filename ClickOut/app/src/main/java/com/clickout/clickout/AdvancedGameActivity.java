package com.clickout.clickout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AdvancedGameActivity extends AppCompatActivity {
    private final String AdvancedGameScoreKey = "Advanced_Game_Score_key";

    private ArrayList<BoxView> boxViewsTop;
    private ArrayList<BoxView> boxViewsBottom;
    private RelativeLayout relativeContainer;
    private int heightStep;
    private int boxViewWidth;
    private int boxViewDefaultHeight;
    private int screenWidth;
    private int boxViewsCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int screenHeight = ScreenUtil.getScreenHeight(this.getWindowManager());
        this.screenWidth = ScreenUtil.getScreenWidth(this.getWindowManager());
        this.boxViewWidth = this.screenWidth / this.boxViewsCount;
        this.heightStep = screenHeight / 26;
        this.boxViewDefaultHeight = screenHeight / 2;
        this.relativeContainer = (RelativeLayout) this.findViewById(R.id.relative_container);
        this.boxViewsBottom = new ArrayList<BoxView>();
        this.boxViewsTop = new ArrayList<BoxView>();
        this.initBoxViews();
        playInitialAnimations();
    }

    private void showFingerCheck() {
        FingerCheckDialog dialog = new FingerCheckDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void initBoxViews() {
        int previousBoxViewId = -1;
        for (int i = 0; i < this.boxViewsCount; i += 1) {
            BoxView boxView = this.createBoxView(this.getColor(R.color.playerOneColor));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) boxView.getLayoutParams();
            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, previousBoxViewId);
            }

            final int currentIndex = i;
            boxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseTop(currentIndex);
                }
            });

            this.boxViewsTop.add(boxView);
            this.relativeContainer.addView(boxView);
            previousBoxViewId = boxView.getId();
        }

        for (int i = 0; i < this.boxViewsCount; i += 1) {
            BoxView boxView = this.createBoxView(this.getColor(R.color.playerTwoColor));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) boxView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, previousBoxViewId);
            }

            final int currentIndex = i;
            boxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseBottom(currentIndex);
                }
            });

            this.boxViewsBottom.add(boxView);
            this.relativeContainer.addView(boxView);
            previousBoxViewId = boxView.getId();
        }
    }

    private void setNewHeight(int index, int topHeight, int bottomHeight) {
        this.checkGameOver(topHeight, bottomHeight);

        BoxView topView = this.boxViewsTop.get(index);
        RelativeLayout.LayoutParams topParams = (RelativeLayout.LayoutParams) topView.getLayoutParams();
        topParams.height = topHeight;
        topView.setLayoutParams(topParams);

        BoxView bottomView = this.boxViewsBottom.get(index);
        RelativeLayout.LayoutParams bottomParams = (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
        bottomParams.height = bottomHeight;
        bottomView.setLayoutParams(bottomParams);
    }


    private void increaseTop(int index) {
        BoxView topView = this.boxViewsTop.get(index);
        BoxView bottomView = this.boxViewsBottom.get(index);
        this.setNewHeight(index, topView.getHeight() + this.heightStep, bottomView.getHeight() - this.heightStep);
    }

    private void increaseBottom(int index) {
        BoxView topView = this.boxViewsTop.get(index);
        BoxView bottomView = this.boxViewsBottom.get(index);
        this.setNewHeight(index, topView.getHeight() - this.heightStep, bottomView.getHeight() + this.heightStep);
    }

    private BoxView createBoxView(int color) {
        BoxView boxView = new BoxView(this);
        boxView.setId(View.generateViewId());
        boxView.setColor(color);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(this.boxViewWidth, 0);
        boxView.setLayoutParams(params);
        return boxView;
    }

    private void playInitialAnimations() {
        final ArrayList<ValueAnimator> animators = new ArrayList<>(this.boxViewsTop.size());
        int animationDuration = 400;
        final int max = this.boxViewsTop.size();
        final int middle = max / 2;

        for (int i = 0; i < this.boxViewsTop.size(); i += 1) {
            final int currentIndex = i;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, this.boxViewDefaultHeight);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    setNewHeight(currentIndex, value, value);
                }
            });
            valueAnimator.setInterpolator(new OvershootInterpolator());
            valueAnimator.setDuration(animationDuration);
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (currentIndex < max - 1) {
                        animators.get(currentIndex + 1).start();
                    } else if (currentIndex == max - 1) {
                        showFingerCheck();
                    }
                }
            });
            animators.add(valueAnimator);
        }

        animators.get(0).start();
    }

    private void checkGameOver(int boxTopHeight, int boxBottomHeight) {
        if (boxBottomHeight < 0) {
            Score score = ScoreManager.getScore(this.AdvancedGameScoreKey);
            score.Player1 += 1;
            ScoreManager.updateScore(this.AdvancedGameScoreKey, score);

            Intent intent = new Intent(this, EndGameActivity.class);
            startActivity(intent);

        } else if (boxTopHeight < 0) {
            Score score = ScoreManager.getScore(this.AdvancedGameScoreKey);
            score.Player2 += 1;
            ScoreManager.updateScore(this.AdvancedGameScoreKey, score);

            Intent intent = new Intent(this, EndGameActivity.class);
            startActivity(intent);
        }
    }
}
