package com.lamvo.groupproject_flowershop;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;




class MyFirebaseMessagingService extends FirebaseMessagingService  {

    final String channelId = "notification_channel";
    final String channelName = "com.lamvo.groupproject_flowershop";

    //generate the noti
    //attach the noti with custom layout
    //show the noti

    public void generateNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder build = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.icons8_shopping_basket_add_96___)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

//        build = build.setContent(getRemoteView(title, message));
    }

//    class getRemoteView(String title, String message) extends RemoteViews {
//        @SuppressLint("RemoteViewLayout")
//        RemoteViews remoteView = new RemoteViews("com.lamvo.groupproject_flowershop", R.layout.notification);
//
//    }




}
