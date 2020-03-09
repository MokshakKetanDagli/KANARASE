package com.example.afinal;

import android.annotation.SuppressLint;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.afinal.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Messaging_Service extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        shownotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void shownotification(String title, String msg){
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "Mynotification")
                .setContentTitle("KANARASE")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(R.color.main_back)
                .setAutoCancel(true)
                .setContentText(msg);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(999,builder.build());
    }
}
