package edu.northeastern.numad23fa_akhilachiguluri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LcAdapter extends RecyclerView.Adapter<LcHolder> {
    private Context mContext;
    private ArrayList<LcItem> mDataList;
    private clickListener mItemClickListener;
    private longListener mItemLongClickListener;

    public LcAdapter(Context context, ArrayList<LcItem> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    public void setOnItemClickListener(clickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(longListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    @NonNull
    @Override
    public LcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lc_item, parent, false);
        return new LcHolder(view, mItemClickListener, mItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LcHolder viewHolder, int position) {
        LcItem currentItem = mDataList.get(position);
        viewHolder.itemName.setText(mContext.getString(R.string.ItemNameValuePair, currentItem.getItemName()));
        viewHolder.itemURL.setText(mContext.getString(R.string.ItemDescriptionValuePair, currentItem.getItemUrl()));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public interface clickListener {
        void onItemClick(int position);
    }

    public void setDataList(ArrayList<LcItem> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    public interface longListener {
        void onItemLongClick(int position);
    }
}
