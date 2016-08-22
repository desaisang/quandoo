package com.example.quandoo.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * This class is used for encapsulating broadcastreceiver.
 */
public class ResetBookingWatcher {

    public static final String INTENT_RESET_BOOKING = "intent_reset_booking";
    private static CallBack mCallBack;

    public interface CallBack {
        void onEventReceived();
    }

    /**
     * Broadcastreciever
     */
    public static class bookingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mCallBack.onEventReceived();
        }
    }

    public ResetBookingWatcher(CallBack callBackInterface) {
        mCallBack = callBackInterface;
    }

    /**
     * The method sets the receiver to listen to the alarm event
     * @param cxt application context
     */
    public void setReceiver(Context cxt) {

        IntentFilter bookingWatcherFilter = new IntentFilter();
        bookingWatcherFilter.addAction(INTENT_RESET_BOOKING);

        cxt.registerReceiver(new bookingReceiver(), bookingWatcherFilter);
    }
}
