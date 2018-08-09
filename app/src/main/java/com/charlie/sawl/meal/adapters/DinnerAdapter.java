package com.charlie.sawl.meal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.charlie.sawl.R;
import com.charlie.sawl.meal.MealTool;
import java.util.ArrayList;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MealListData> mListData = new ArrayList<>();

    public DinnerAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void addItem(String mCalender, String mDayOfTheWeek, String mBreakfast, String mLunch, String mDinner, boolean isToday) {
        MealListData addItemInfo = new MealListData();
        addItemInfo.mCalender = mCalender;
        addItemInfo.mDayOfTheWeek = mDayOfTheWeek;
        addItemInfo.mBreakfast = mBreakfast;
        addItemInfo.mLunch = mLunch;
        addItemInfo.mDinner = mDinner;
        addItemInfo.isToday = isToday;

        mListData.add(addItemInfo);
    }

    public void clearData() {
        mListData.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.row_meal_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealListData mData = mListData.get(position);

        String mCalender = mData.mCalender;
        String mDayOfTheWeek = mData.mDayOfTheWeek;
        String mDinner = mData.mDinner;

        if (MealTool.mStringCheck(mDinner))
            mDinner = mData.mDinner = mContext.getResources().getString(R.string.no_data_dinner);

        holder.mCalender.setText(mCalender);
        holder.mDayOfTheWeek.setText(mDayOfTheWeek);
        holder.mDinner.setText(mDinner);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCalender, mDayOfTheWeek, mDinner;

        ViewHolder(View view) {
            super(view);

            mCalender = view.findViewById(R.id.mCalender);
            mDayOfTheWeek = view.findViewById(R.id.mDayOfTheWeek);
            mDinner = view.findViewById(R.id.mMeal);
            view.setTag(view);
        }
    }
}