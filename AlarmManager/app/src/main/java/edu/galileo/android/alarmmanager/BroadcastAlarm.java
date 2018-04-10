package edu.galileo.android.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Larry on 3/25/2018.
 */

public class BroadcastAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "30 seconds passed!", Toast.LENGTH_LONG).show();
    }
}
