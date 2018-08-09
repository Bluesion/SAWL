package com.charlie.sawl.homepage;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.charlie.sawl.R;
import java.util.ArrayList;

public class HomepageAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> title, date, author;

    HomepageAdapter(Activity context, ArrayList<String> title, ArrayList<String> author, ArrayList<String> date) {
        super();
        this.context = context;
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public int getCount() {
        return title.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        AppCompatTextView mTitle, mData;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_homepage_item, null);
            holder = new ViewHolder();
            holder.mTitle = convertView.findViewById(R.id.title);
            holder.mData = convertView.findViewById(R.id.data);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle.setText(title.get(position));
        holder.mData.setText(author.get(position) + " · " + date.get(position));
        return convertView;
    }
}