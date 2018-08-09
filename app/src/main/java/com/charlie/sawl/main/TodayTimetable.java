package com.charlie.sawl.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.charlie.sawl.R;
import java.util.Calendar;
import java.util.Objects;

public class TodayTimetable extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_timetable, container, false);

        AppCompatTextView day = rootView.findViewById(R.id.day);
        AppCompatTextView table1 = rootView.findViewById(R.id.timetable1);
        AppCompatTextView table2 = rootView.findViewById(R.id.timetable2);
        AppCompatTextView table3 = rootView.findViewById(R.id.timetable3);
        AppCompatTextView table4 = rootView.findViewById(R.id.timetable4);
        AppCompatTextView table5 = rootView.findViewById(R.id.timetable5);
        AppCompatTextView table6 = rootView.findViewById(R.id.timetable6);
        AppCompatTextView table7 = rootView.findViewById(R.id.timetable7);
        AppCompatTextView table8 = rootView.findViewById(R.id.timetable8);

        Calendar mCalendar = Calendar.getInstance();
        SharedPreferences subjects = Objects.requireNonNull(getActivity()).getSharedPreferences("timetable", Activity.MODE_PRIVATE);

        LinearLayout free = rootView.findViewById(R.id.no_timetable);
        LinearLayout study = rootView.findViewById(R.id.timetable_layout);

        if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            free.setVisibility(View.GONE);

            day.setText("월요일");

            String mon1 = subjects.getString("monday1", "");
            String mon2 = subjects.getString("monday2", "");
            String mon3 = subjects.getString("monday3", "");
            String mon4 = subjects.getString("monday4", "");
            String mon5 = subjects.getString("monday5", "");
            String mon6 = subjects.getString("monday6", "");
            String mon7 = subjects.getString("monday7", "");
            String mon8 = subjects.getString("monday8", "");

            table1.setText(mon1);
            table2.setText(mon2);
            table3.setText(mon3);
            table4.setText(mon4);
            table5.setText(mon5);
            table6.setText(mon6);
            table7.setText(mon7);
            table8.setText(mon8);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            free.setVisibility(View.GONE);

            day.setText("화요일");

            String tue1 = subjects.getString("tuesday1", "");
            String tue2 = subjects.getString("tuesday2", "");
            String tue3 = subjects.getString("tuesday3", "");
            String tue4 = subjects.getString("tuesday4", "");
            String tue5 = subjects.getString("tuesday5", "");
            String tue6 = subjects.getString("tuesday6", "");
            String tue7 = subjects.getString("tuesday7", "");
            String tue8 = subjects.getString("tuesday8", "");

            table1.setText(tue1);
            table2.setText(tue2);
            table3.setText(tue3);
            table4.setText(tue4);
            table5.setText(tue5);
            table6.setText(tue6);
            table7.setText(tue7);
            table8.setText(tue8);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            free.setVisibility(View.GONE);

            day.setText("수요일");

            String wed1 = subjects.getString("wednesday1", "");
            String wed2 = subjects.getString("wednesday2", "");
            String wed3 = subjects.getString("wednesday3", "");
            String wed4 = subjects.getString("wednesday4", "");
            String wed5 = subjects.getString("wednesday5", "");
            String wed6 = subjects.getString("wednesday6", "");
            String wed7 = subjects.getString("wednesday7", "");
            String wed8 = subjects.getString("wednesday8", "");

            table1.setText(wed1);
            table2.setText(wed2);
            table3.setText(wed3);
            table4.setText(wed4);
            table5.setText(wed5);
            table6.setText(wed6);
            table7.setText(wed7);
            table8.setText(wed8);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            free.setVisibility(View.GONE);

            day.setText("목요일");

            String thu1 = subjects.getString("thursday1", "");
            String thu2 = subjects.getString("thursday2", "");
            String thu3 = subjects.getString("thursday3", "");
            String thu4 = subjects.getString("thursday4", "");
            String thu5 = subjects.getString("thursday5", "");
            String thu6 = subjects.getString("thursday6", "");
            String thu7 = subjects.getString("thursday7", "");
            String thu8 = subjects.getString("thursday8", "");

            table1.setText(thu1);
            table2.setText(thu2);
            table3.setText(thu3);
            table4.setText(thu4);
            table5.setText(thu5);
            table6.setText(thu6);
            table7.setText(thu7);
            table8.setText(thu8);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            free.setVisibility(View.GONE);

            day.setText("금요일");

            String fri1 = subjects.getString("friday1", "");
            String fri2 = subjects.getString("friday2", "");
            String fri3 = subjects.getString("friday3", "");
            String fri4 = subjects.getString("friday4", "");
            String fri5 = subjects.getString("friday5", "");
            String fri6 = subjects.getString("friday6", "");
            String fri7 = subjects.getString("friday7", "");
            String fri8 = subjects.getString("friday8", "");
            
            table1.setText(fri1);
            table2.setText(fri2);
            table3.setText(fri3);
            table4.setText(fri4);
            table5.setText(fri5);
            table6.setText(fri6);
            table7.setText(fri7);
            table8.setText(fri8);
        } else if (mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            study.setVisibility(View.GONE);
        }

        return rootView;
    }
}