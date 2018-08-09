package com.charlie.sawl.expandableview;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.charlie.sawl.R;
import com.charlie.sawl.expandableview.library.ParentViewHolder;

public class CategoryViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private final ImageView mArrowExpandImageView;
    private TextView mCategoryTextView;

    CategoryViewHolder(View itemView) {
        super(itemView);
        mCategoryTextView = itemView.findViewById(R.id.category);
        mArrowExpandImageView = itemView.findViewById(R.id.iv_arrow_expand);
    }

    public void bind(CategoryList mCategory) {
        mCategoryTextView.setText(mCategory.getCategory());
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

        if (expanded) {
            mArrowExpandImageView.setRotation(ROTATED_POSITION);
        } else {
            mArrowExpandImageView.setRotation(INITIAL_POSITION);
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

        RotateAnimation rotateAnimation;
        if (expanded) {
            rotateAnimation = new RotateAnimation(ROTATED_POSITION, INITIAL_POSITION, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION, INITIAL_POSITION, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }

        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        mArrowExpandImageView.startAnimation(rotateAnimation);
    }
}
