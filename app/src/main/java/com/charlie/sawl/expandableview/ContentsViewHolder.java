package com.charlie.sawl.expandableview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.charlie.sawl.R;
import com.charlie.sawl.expandableview.library.ChildViewHolder;

public class ContentsViewHolder extends ChildViewHolder implements View.OnClickListener {

    private TextView mTitle, mDescription;
    private final Context context;

    ContentsViewHolder(View v) {
        super(v);
        mTitle = itemView.findViewById(R.id.title);
        mDescription = itemView.findViewById(R.id.description);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            String second = mDescription.getText().toString();
            if (second.startsWith("0")) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", second, null)));
            } else if (second.startsWith("h")) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(second)));
            }
        }
    }

    public void bind(Contents contents) {
        mTitle.setText(contents.getTitle());
        mDescription.setText(contents.getDescription());
    }
}