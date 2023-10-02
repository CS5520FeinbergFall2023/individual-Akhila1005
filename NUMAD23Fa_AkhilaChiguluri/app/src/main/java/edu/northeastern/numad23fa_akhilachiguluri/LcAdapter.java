package edu.northeastern.numad23fa_akhilachiguluri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LcAdapter extends RecyclerView.Adapter<LcHolder> {
    private Context ac_context;
    private ArrayList<LcItem> myDataList;
    private clickListener myItemClickListener;
    private longListener myItemLongClickListener;

    public LcAdapter(Context context, ArrayList<LcItem> dataList) {
        this.ac_context = context;
        this.myDataList = dataList;
    }

    public void setOnItemClickListener(clickListener itemClickListener) {
        this.myItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(longListener itemLongClickListener) {
        this.myItemLongClickListener = itemLongClickListener;
    }

    @NonNull
    @Override
    public LcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lc_item, parent, false);
        return new LcHolder(view, myItemClickListener, myItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LcHolder viewHolder, int position) {
        LcItem currentItem = myDataList.get(position);
        viewHolder.itemName.setText(ac_context.getString(R.string.ItemNameValuePair, currentItem.getItemName()));
        viewHolder.itemURL.setText(ac_context.getString(R.string.ItemDescriptionValuePair, currentItem.getItemUrl()));
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public interface clickListener {
        void onItemClick(int position);
    }

    public void setDataList(ArrayList<LcItem> dataList) {
        this.myDataList = dataList;
        notifyDataSetChanged();
    }

    public interface longListener {
        void onItemLongClick(int position);
    }
}
