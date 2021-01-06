package com.example.mafia.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mafia.R;

import timber.log.Timber;

public class CreateRoomWorker extends Worker {
    public static final String CHANNEL_ID = "15";
    public static final int NOTIFICATION_ID = 1;
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

        Timber.d("My button was hit from worker");
        String textTitle = "Notification Time";
        String textContent = "You've been notified :>";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dragon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // TO DO create a "room" with a unique code

        return Result.success();
    }
}
