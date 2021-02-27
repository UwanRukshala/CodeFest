package com.codefestfinal.codefest21_2.FCM;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message Data Placed" + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {

            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}

