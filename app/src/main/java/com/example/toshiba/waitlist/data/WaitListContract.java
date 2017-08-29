package com.example.toshiba.waitlist.data;


import android.provider.BaseColumns;

public class WaitListContract {

    public static final class WaitListEntry implements BaseColumns {
        public static final String TABLE_NAME = "waitList";
        public static final String GUEST_NAME = "guestName";
        public static final String PARTY_SIZE = "partySize";
        public static final String TIME_STAMP = "timeStamp";
    }

}
