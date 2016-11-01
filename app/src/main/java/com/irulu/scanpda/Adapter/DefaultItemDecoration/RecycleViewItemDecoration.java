package com.irulu.scanpda.Adapter.DefaultItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dtw on 16/10/24.
 */

public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RecycleViewItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top=space;
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
    }
}
