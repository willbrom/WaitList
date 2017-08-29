package com.example.toshiba.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toshiba.waitlist.data.WaitListContract;
import com.example.toshiba.waitlist.data.WaitListDbHelper;

public class MainActivity extends AppCompatActivity {

    private EditText guestNameEditText;
    private EditText partySizeEditText;
    private GuestListAdapter guestListAdapter;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guestNameEditText = (EditText) findViewById(R.id.et_guest_name);
        partySizeEditText = (EditText) findViewById(R.id.et_party_size);
        RecyclerView guestListRecyclerView;
        guestListRecyclerView = (RecyclerView) findViewById(R.id.rv_guest_list);
        guestListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        WaitListDbHelper waitListDbHelper = new WaitListDbHelper(this);
        sqLiteDatabase = waitListDbHelper.getWritableDatabase();
        Cursor cursor = getAllGuest();

        guestListAdapter = new GuestListAdapter(this, cursor);
        guestListRecyclerView.setAdapter(guestListAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                float id = (float) viewHolder.itemView.getTag();
                if (removeGuest(id)) {
                    Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.toast_guest_removed), Toast.LENGTH_SHORT).show();
                }
                guestListAdapter.swapCursor(getAllGuest());
            }
        }).attachToRecyclerView(guestListRecyclerView);
    }

    private boolean removeGuest(float id) {
        return sqLiteDatabase.delete(WaitListContract.WaitListEntry.TABLE_NAME,
                WaitListContract.WaitListEntry._ID + "=" + id, null) > 0;
    }

    public void addToWaitList(View view) {
        if (guestNameEditText.getText().length() == 0 || partySizeEditText.getText().length() == 0)
            return;

        String guestName = guestNameEditText.getText().toString();
        int partySize = Integer.parseInt(partySizeEditText.getText().toString());

        if (partySize != 0) {
            addNewGuest(guestName, partySize);
            guestListAdapter.swapCursor(getAllGuest());

            Toast.makeText(this, getResources().getString(R.string.toast_guest_added), Toast.LENGTH_SHORT).show();

            partySizeEditText.clearFocus();
            guestNameEditText.getText().clear();
            partySizeEditText.getText().clear();
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_error_party_size), Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewGuest(String guestName, int partySize) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WaitListContract.WaitListEntry.GUEST_NAME, guestName);
        contentValues.put(WaitListContract.WaitListEntry.PARTY_SIZE, partySize);
        sqLiteDatabase.insert(WaitListContract.WaitListEntry.TABLE_NAME, null, contentValues);
    }

    private Cursor getAllGuest() {
        return sqLiteDatabase.query(
                WaitListContract.WaitListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitListContract.WaitListEntry.TIME_STAMP);
    }

}
