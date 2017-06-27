package com.clickout.clickout;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoxView extends TextView {
    private static final int BOXVIEW_TEXT_SIZE = 80;
    private GradientDrawable shape;
    private int color;

    public BoxView(Context context) {
        this(context, null);
    }

    public BoxView(Context context, @Nullable AttributeSet attr) {
        super(context, attr);

        this.shape = this.createGradientDrawable(
                this.getColor(),
                this.getColor(),
                0,
                0);

        this.setBackground(this.shape);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.setTextSize(BOXVIEW_TEXT_SIZE);
    }

    public void setColor(int color) {
        if (this.shape != null) {
            this.color = color;
            this.shape.setColor(color);
        }
    }

    public int getColor() {
        return this.color;
    }

    private GradientDrawable createGradientDrawable(int backgroundColor, int strokeColor, int strokeWith, float cornerRadius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(strokeWith, strokeColor);
        gradientDrawable.setCornerRadius(cornerRadius);
        gradientDrawable.mutate();
        return gradientDrawable;
    }
}
