package com.paul.song.shadowlessdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.paul.song.analytics.sdk.ShadowlessTrackIntent;

public class TestService extends Service {

    private static final String ACTION_FOO = "com.paul.song.shadowlessdemo.action.FOO";
    private static final String ACTION_BAZ = "com.paul.song.shadowlessdemo.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.paul.song.shadowlessdemo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.paul.song.shadowlessdemo.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    @ShadowlessTrackIntent(extraName = EXTRA_PARAM1)
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
