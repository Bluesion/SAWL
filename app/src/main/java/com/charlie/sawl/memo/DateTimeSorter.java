package com.charlie.sawl.memo;

public class DateTimeSorter {
    private int mIndex;
    private String mDateTime;

    DateTimeSorter(int index, String DateTime){
        mIndex = index;
        mDateTime = DateTime;
    }
    public int getIndex() {
        return mIndex;
    }

    public String getDateTime() {
        return mDateTime;
    }
}
