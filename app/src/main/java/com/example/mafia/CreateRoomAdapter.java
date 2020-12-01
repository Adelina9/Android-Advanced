package com.example.mafia;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;

public class CreateRoomAdapter {
    @BindingAdapter("numbersOfPlayersRes")
    public static void setTitle(TextView textView, @StringRes int numbersOfPlayersRes) {
        Context context = textView.getContext();
        textView.setText(context.getString(numbersOfPlayersRes));
    }
}
