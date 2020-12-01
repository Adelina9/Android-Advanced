package com.example.mafia;

import android.util.Log;

import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

public class CreateRoomViewModel extends ViewModel {
    private final WorkManager workManager;
    public static final String LOG_TAG = "create_room_view_model";

    @StringRes
    public int numbersOfPlayersRes = R.string.app_name;
    public String numberOfPlayers = "Number of players";

    @StringRes
    public int createRoomRes = R.string.app_name;
    public String createRoom = "Create room";

    public CreateRoomViewModel(WorkManager workManager) {
        this.workManager = workManager;
    }

    public void onButtonHit() {
        Log.d(LOG_TAG, "My button was hit from view model");

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.METERED)
                .build();

        Data inputData = new Data.Builder()
                .putString("type", "get")
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CreateRoomWorker.class)
                .setInputData(inputData)
                .setConstraints(constraints)
                .build();

        workManager.beginUniqueWork(
                "some-heavy-work",
                ExistingWorkPolicy.APPEND,
                request)
                .enqueue();
    }
}
