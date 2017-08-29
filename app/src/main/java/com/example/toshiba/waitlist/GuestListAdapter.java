package com.example.toshiba.waitlist;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.waitlist.data.WaitListContract;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {


    private final Context context;
    private Cursor cursor;

    GuestListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if (!cursor.moveToPosition(position))
            return;
        String guestName = cursor.getString(cursor.getColumnIndex(WaitListContract.WaitListEntry.GUEST_NAME));
        int partySize = cursor.getInt(cursor.getColumnIndex(WaitListContract.WaitListEntry.PARTY_SIZE));
        float id = cursor.getFloat(cursor.getColumnIndex(WaitListContract.WaitListEntry._ID));
        holder.itemView.setTag(id);
        holder.guestNameTextView.setText(guestName);
        holder.partySizeTextView.setText(String.valueOf(partySize));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    void swapCursor(Cursor cursor) {
        if (this.cursor != null)
            this.cursor.close();
        this.cursor = cursor;
        if (this.cursor != null)
            notifyDataSetChanged();
    }

    class GuestViewHolder extends RecyclerView.ViewHolder {

        final TextView guestNameTextView;
        final TextView partySizeTextView;

        public GuestViewHolder(View itemView) {
            super(itemView);

            guestNameTextView = itemView.findViewById(R.id.tv_guest_name);
            partySizeTextView = itemView.findViewById(R.id.tv_party_size);
        }
    }

}
