package com.charlie.sawl.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.charlie.sawl.R;
import com.charlie.sawl.meal.MealTool;

public class TodayMeal extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_meal, container, false);

        TextView breakfast = rootView.findViewById(R.id.todayBreakfast);
        MealTool.todayMealData mMealData = MealTool.todayBreakfast(getActivity());
        breakfast.setText(mMealData.info);

        TextView lunch = rootView.findViewById(R.id.todayLunch);
        MealTool.todayMealData mMealData2 = MealTool.todayLunch(getActivity());
        lunch.setText(mMealData2.info);

        TextView dinner = rootView.findViewById(R.id.todayDinner);
        MealTool.todayMealData mMealData3 = MealTool.todayDinner(getActivity());
        dinner.setText(mMealData3.info);

        return rootView;
    }
}