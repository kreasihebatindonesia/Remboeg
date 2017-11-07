package com.kreasihebatindonesia.remboeg.utils;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class VerticalOffsetDecoration extends RecyclerView.ItemDecoration {
    private Activity context;

    public VerticalOffsetDecoration(Activity context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int total = parent.getAdapter().getItemCount();

        if (position != 0 && position != total - 1)
            return;

        Display display = context.getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int displayWidth = displaySize.x;
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        float viewWidth = params.width;

        int offset = (int)(displayWidth - viewWidth) / 2 ;

        if (position == 0)
            outRect.left = offset - params.getMarginStart();

        if (position == total - 1)
            outRect.right = offset- params.getMarginEnd();
    }
}