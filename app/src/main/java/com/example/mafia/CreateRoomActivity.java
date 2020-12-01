package com.example.mafia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.example.mafia.databinding.ActivityCreateRoomBinding;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateRoomViewModel viewModel = new CreateRoomViewModel(WorkManager.getInstance(this));

        ActivityCreateRoomBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_create_room);
        binding.setCreateRoomViewModel(viewModel);
    }
}