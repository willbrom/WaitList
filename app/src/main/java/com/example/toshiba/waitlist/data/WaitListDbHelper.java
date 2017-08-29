package com.example.toshiba.waitlist.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WaitListDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitlist.db";
    private static final int DATABASE_VERSION = 1;

    public WaitListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + WaitListContract.WaitListEntry.TABLE_NAME
                + " (" + WaitListContract.WaitListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WaitListContract.WaitListEntry.GUEST_NAME + " TEXT NOT NULL, "
                + WaitListContract.WaitListEntry.PARTY_SIZE + " INTEGER NOT NULL, "
                + WaitListContract.WaitListEntry.TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WaitListContract.WaitListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
