package com.example.mafia;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;

public class CreateRoomViewModel extends ViewModel {
    private final WorkManager workManager;

    @StringRes
    public int numbersOfPlayersRes = R.string.app_name;
    public String numberOfPlayers = "Number of players";

    @StringRes
    public int createRoomRes = R.string.app_name;
    public String createRoom = "Create room";

    public CreateRoomViewModel(WorkManager workManager) {
        this.workManager = workManager;
    }
}
