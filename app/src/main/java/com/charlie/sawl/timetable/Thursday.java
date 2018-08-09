package com.charlie.sawl.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;
import java.util.Objects;

public class Thursday extends Fragment {

    String Subject1, Subject2, Subject3, Subject4, Subject5, Subject6, Subject7, Subject8;
    AppCompatEditText Sub1, Sub2, Sub3, Sub4, Sub5, Sub6, Sub7, Sub8;
    SharedPreferences mSubjects;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        mSubjects = Objects.requireNonNull(getActivity()).getSharedPreferences("timetable", Context.MODE_PRIVATE);

        Sub1 = rootView.findViewById(R.id.subject1);
        Sub2 = rootView.findViewById(R.id.subject2);
        Sub3 = rootView.findViewById(R.id.subject3);
        Sub4 = rootView.findViewById(R.id.subject4);
        Sub5 = rootView.findViewById(R.id.subject5);
        Sub6 = rootView.findViewById(R.id.subject6);
        Sub7 = rootView.findViewById(R.id.subject7);
        Sub8 = rootView.findViewById(R.id.subject8);
        getData();

        Sub1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject1 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject1 = s.toString();
            }
        });
        Sub2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject2 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject2 = s.toString();
            }
        });
        Sub3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject3 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject3 = s.toString();
            }
        });
        Sub4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject4 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject4 = s.toString();
            }
        });
        Sub5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject5 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject5 = s.toString();
            }
        });
        Sub6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject6 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject6 = s.toString();
            }
        });
        Sub7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject7 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject7 = s.toString();
            }
        });
        Sub8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Subject8 = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                Subject8 = s.toString();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void onDestroyView() {
        SaveData();
        super.onDestroyView();
    }

    public void getData() {
        Subject1 = mSubjects.getString("thursday1", "");
        Subject2 = mSubjects.getString("thursday2", "");
        Subject3 = mSubjects.getString("thursday3", "");
        Subject4 = mSubjects.getString("thursday4", "");
        Subject5 = mSubjects.getString("thursday5", "");
        Subject6 = mSubjects.getString("thursday6", "");
        Subject7 = mSubjects.getString("thursday7", "");
        Subject8 = mSubjects.getString("thursday8", "");

        Sub1.setText(Subject1);
        Sub2.setText(Subject2);
        Sub3.setText(Subject3);
        Sub4.setText(Subject4);
        Sub5.setText(Subject5);
        Sub6.setText(Subject6);
        Sub7.setText(Subject7);
        Sub8.setText(Subject8);
    }

    public void SaveData() {
        SharedPreferences.Editor mEditor = mSubjects.edit();
        mEditor.putString("thursday1", Subject1);
        mEditor.putString("thursday2", Subject2);
        mEditor.putString("thursday3", Subject3);
        mEditor.putString("thursday4", Subject4);
        mEditor.putString("thursday5", Subject5);
        mEditor.putString("thursday6", Subject6);
        mEditor.putString("thursday7", Subject7);
        mEditor.putString("thursday8", Subject8);
        mEditor.apply();
    }
}