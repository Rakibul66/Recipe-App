package com.recipe.finder.Adapters;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedRecyclerDecoration extends RecyclerView.ItemDecoration {
    int sidePadding, topPadding,bottomPadding;

    public FeedRecyclerDecoration(int sidePadding, int topPadding, int bottomPadding) {
        this.sidePadding = sidePadding;
        this.topPadding = topPadding;
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();

        if (itemCount > 0 && parent.getChildAdapterPosition(view) == itemCount - 1) {
            outRect.bottom = bottomPadding;
        }
        outRect.top = topPadding;
        outRect.right = sidePadding;
        outRect.left = sidePadding;
    }
}
