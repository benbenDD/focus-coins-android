package com.focuscoins.app;
import android.content.*;
public class BootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context c, Intent i) { FocusService.start(c); }
}
