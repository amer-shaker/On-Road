package reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.onroad.MyService;

public class MyReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MyService.class).
                putExtra("", ""));
    }
}
