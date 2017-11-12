package com.kreasihebatindonesia.remboeg.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kreasihebatindonesia.remboeg.R;

/**
 * Created by InfinityLogic on 11/12/2017.
 */

public class DrawableTextView extends AppCompatTextView {
    public DrawableTextView(Context context) {
        super(context);
    }
    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }
    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomTextView);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.CustomTextView_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(R.styleable.CustomTextView_drawableRightCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.CustomTextView_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.CustomTextView_drawableTopCompat);

                if(drawableLeft != null)
                    DrawableCompat.setTint(drawableLeft, attributeArray.getColor(R.styleable.CustomTextView_drawableTintCompat, Color.BLACK));
                if(drawableRight != null)
                    DrawableCompat.setTint(drawableRight, attributeArray.getColor(R.styleable.CustomTextView_drawableTintCompat, Color.BLACK));
                if(drawableBottom != null)
                    DrawableCompat.setTint(drawableBottom, attributeArray.getColor(R.styleable.CustomTextView_drawableTintCompat, Color.BLACK));
                if(drawableTop != null)
                    DrawableCompat.setTint(drawableTop, attributeArray.getColor(R.styleable.CustomTextView_drawableTintCompat, Color.BLACK));

            } else {
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableTopCompat, -1);
                final int drawableTintId = attributeArray.getColor(R.styleable.CustomTextView_drawableTintCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);

                if (drawableTintId != -1){
                    if(drawableLeft != null)
                        drawableLeft.mutate().setColorFilter(drawableTintId, PorterDuff.Mode.SRC_IN);
                    if(drawableRight != null)
                        drawableRight.mutate().setColorFilter(drawableTintId, PorterDuff.Mode.SRC_IN);
                    if(drawableBottom != null)
                        drawableBottom.mutate().setColorFilter(drawableTintId, PorterDuff.Mode.SRC_IN);
                    if(drawableTop != null)
                        drawableTop.mutate().setColorFilter(drawableTintId, PorterDuff.Mode.SRC_IN);
                }

            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            attributeArray.recycle();
        }
    }
}