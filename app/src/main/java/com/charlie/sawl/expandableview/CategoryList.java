package com.charlie.sawl.expandableview;

import com.charlie.sawl.expandableview.library.ParentListItem;
import java.util.List;

public class CategoryList implements ParentListItem {

    private String mCategory;
    private List<Contents> mList;

    public CategoryList(String category, List<Contents> list) {
        mCategory = category;
        mList = list;
    }

    public String getCategory() {
        return mCategory;
    }

    @Override
    public List<?> getChildItemList() {
        return mList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
