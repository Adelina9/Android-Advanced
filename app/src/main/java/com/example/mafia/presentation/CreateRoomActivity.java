package com.example.mafia.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.mafia.R;
import com.example.mafia.data.InMemoryDataSource;
import com.example.mafia.data.remote.RemoteDataSource;
import com.example.mafia.data.remote.UserApi;
import com.example.mafia.databinding.ActivityCreateRoomBinding;
import com.example.mafia.domain.FetchUsersUseCase;
import com.example.mafia.domain.UsersMediator;
import com.example.mafia.domain.UsersRepository;
import com.example.mafia.presentation.CreateRoomViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class CreateRoomActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                FetchUsersUseCase useCase = createUsecase();
                return (T) new CreateRoomViewModel(WorkManager.getInstance(CreateRoomActivity.this), useCase);
            }
        };

        CreateRoomViewModel viewModel = new ViewModelProvider(this, factory).get(CreateRoomViewModel.class);

        ActivityCreateRoomBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_create_room);
        binding.setCreateRoomViewModel(viewModel);
    }

    private FetchUsersUseCase createUsecase() {
        UsersRepository localRepository = new InMemoryDataSource();
        UsersRepository remoteRepository = new RemoteDataSource(UserApi.createApi());

        UsersMediator mediator = new UsersMediator(localRepository, remoteRepository);

        return new FetchUsersUseCase(mediator);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel name";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}