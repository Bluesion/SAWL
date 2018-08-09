package com.charlie.sawl.expandableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;
import com.charlie.sawl.expandableview.library.ExpandableRecyclerAdapter;
import com.charlie.sawl.expandableview.library.ParentListItem;
import java.util.List;

public class CategoryAdapter extends ExpandableRecyclerAdapter<CategoryViewHolder, ContentsViewHolder> {

    private LayoutInflater mInflater;

    public CategoryAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View categoryView = mInflater.inflate(R.layout.fragment_quickconnect_header, parentViewGroup, false);
        return new CategoryViewHolder(categoryView);
    }

    @Override
    public ContentsViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View contentsView = mInflater.inflate(R.layout.fragment_quickconnect_child, childViewGroup, false);
        return new ContentsViewHolder(contentsView);
    }

    @Override
    public void onBindParentViewHolder(CategoryViewHolder categoryViewHolder, int position, ParentListItem parentListItem) {
        CategoryList mCategory = (CategoryList) parentListItem;
        categoryViewHolder.bind(mCategory);
    }

    @Override
    public void onBindChildViewHolder(ContentsViewHolder contentsViewHolder, int position, Object childListItem) {
        Contents contents = (Contents) childListItem;
        contentsViewHolder.bind(contents);
    }
}
