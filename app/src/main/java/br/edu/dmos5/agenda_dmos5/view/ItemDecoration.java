package br.edu.dmos5.agenda_dmos5.view;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import br.edu.dmos5.agenda_dmos5.R;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public ItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int Count = parent.getChildCount();
        for (int i = 0; i < Count; i++) {

            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();

            int tp = child.getBottom() + params.bottomMargin;
            int bt = tp + mDivider.getIntrinsicHeight();

            mDivider.setBounds(parent.getPaddingLeft(), tp, (parent.getWidth() - parent.getPaddingRight()), bt);
            mDivider.draw(c);
        }
    }
}