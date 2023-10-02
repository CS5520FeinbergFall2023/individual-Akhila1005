package edu.northeastern.numad23fa_akhilachiguluri;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LcHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public TextView itemURL;

    public LcHolder(@NonNull View itemView, final LcAdapter.clickListener linkClickListener,final LcAdapter.longListener linkLongClickListener) {
        super(itemView);
        itemName = itemView.findViewById(R.id.link_name_value_pair);
        itemURL = itemView.findViewById(R.id.link_url_value_pair);

        itemView.setOnClickListener(v -> {
            int layoutPosition = getLayoutPosition();
            if (layoutPosition != RecyclerView.NO_POSITION) {
                linkClickListener.onItemClick(layoutPosition);
            }
        });
        itemView.setOnLongClickListener(v -> {
            int layoutPosition = getLayoutPosition();
            if (layoutPosition != RecyclerView.NO_POSITION) {
                linkLongClickListener.onItemLongClick(layoutPosition);
                return true;
            }
            return false;
        });
    }
}