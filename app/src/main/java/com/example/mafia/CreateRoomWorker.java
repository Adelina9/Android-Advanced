package com.example.mafia;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CreateRoomWorker extends Worker {
    public static final String CHANNEL_ID = "15";
    public static final String LOG_TAG = "create_room_worker";
    public static Context context;

    public CreateRoomWorker(@NonNull Context context,
                            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        String value = data.getString("type");

        Log.d(LOG_TAG, "My button was hit from worker");
        String textTitle = "Notification title";
        String textContent = "You've been notified :)";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());

        // TO DO create a "room" with a unique code

//        if ("get".equals(value)) {
//            // GET operation
//        } else if ("post".equals(value)) {
//            // POST operation
//        }

        return Result.success();
    }
}
