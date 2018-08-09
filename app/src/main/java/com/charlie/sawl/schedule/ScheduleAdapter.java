package com.charlie.sawl.schedule;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.charlie.sawl.R;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private ArrayList<ScheduleInfo> mValues = new ArrayList<>();

    public void addItem(String mSchedule, String mDate) {
        ScheduleInfo addInfo = new ScheduleInfo();

        addInfo.date = mDate;
        addInfo.schedule = mSchedule;
        addInfo.isHoliday = false;
        addInfo.home = false;

        mValues.add(addInfo);
    }

    public void addItem(String mSchedule, String mDate, boolean home, boolean isHoliday) {
        ScheduleInfo addInfo = new ScheduleInfo();

        addInfo.date = mDate;
        addInfo.schedule = mSchedule;
        addInfo.isHoliday = isHoliday;
        addInfo.home = home;

        mValues.add(addInfo);
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schedule_item, parent, false);

        return new ScheduleViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ScheduleViewHolder holder, int position) {
        ScheduleInfo mInfo = getItemData(position);

        holder.mDate.setText(mInfo.date);
        holder.mSchedule.setText(mInfo.schedule);

        if (mInfo.isHoliday && mInfo.home) {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.getContext(), R.color.refresh_red));
        } else if (mInfo.isHoliday) {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.getContext(), R.color.refresh_red));
        } else if (mInfo.home) {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.getContext(), R.color.refresh_blue));
        } else {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.getContext(), R.color.colorSecondaryText));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ScheduleInfo getItemData(int position) {
        return mValues.get(position);
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public final TextView mDate, mSchedule;

        public ScheduleViewHolder(View mView) {
            super(mView);

            mSchedule = mView.findViewById(R.id.list_item_entry_title);
            mDate = mView.findViewById(R.id.list_item_entry_summary);
        }
    }

    public class ScheduleInfo {
        public String date;
        public String schedule;
        public boolean isHoliday, home;
    }
}