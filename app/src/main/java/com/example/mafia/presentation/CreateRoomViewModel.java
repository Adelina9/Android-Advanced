package com.example.mafia.presentation;

import android.util.Log;

import androidx.annotation.StringRes;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.mafia.domain.CreateRoomWorker;
import com.example.mafia.R;
import com.example.mafia.domain.FetchUsersUseCase;
import com.example.mafia.domain.User;

import java.util.List;

public class CreateRoomViewModel extends ViewModel {
    private final WorkManager workManager;
    public static final String LOG_TAG = "create_room_view_model";

    @StringRes
    public int numbersOfPlayersRes = R.string.app_name;
    public String numberOfPlayers = "Number of players";

    private final FetchUsersUseCase fetchUsersUseCase;
    public ObservableArrayList<User> users = new ObservableArrayList<>();

    @StringRes
    public int createRoomRes = R.string.app_name;
    public String createRoom = "Create room";

    public CreateRoomViewModel(WorkManager workManager, FetchUsersUseCase fetchUsersUseCase) {
        this.workManager = workManager;
        this.fetchUsersUseCase = fetchUsersUseCase;
    }

    public void onButtonHit() {
        Log.d(LOG_TAG, "My button was hit from view model");

        LiveData<List<User>> liveItems = fetchUsersUseCase.execute();
        liveItems.observeForever(heavyItems -> this.users.addAll(heavyItems));

        launchWorker();
    }

    private void launchWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
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
                ExistingWorkPolicy.REPLACE,
                request)
                .enqueue();
    }
}
