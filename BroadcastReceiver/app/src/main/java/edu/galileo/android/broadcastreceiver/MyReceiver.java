package edu.galileo.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Larry on 3/19/2018.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String broadcastMessage = intent.getStringExtra("broadcast");
        Toast.makeText(context, "Message: " + broadcastMessage
        , Toast.LENGTH_LONG).show();
    }
}
