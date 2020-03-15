package com.example.andras.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    public static final int mId = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupNotifications();
    }

    private void setupNotifications() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cannond);

        Intent resultIntent = new Intent(this, MainActivity.class);
        //alternatively you can add stack to pending intent
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(ResultActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        //Manadatory items
                        //------
                        .setSmallIcon(R.drawable.icon_blue_86x86_stride)
                        .setContentTitle("Content title")
                        .setContentText("Content text")
                        //------
                        .setLargeIcon(bitmap)
                        //dismiss notification atrer the user tapped on it
                        .setAutoCancel(true)
                        //this generates an indeterminate progress bar
                        //.setProgress(0, 0, true)
                        ;
        //alternative configuration way
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.addAction(R.drawable.ic_launcher, "First", resultPendingIntent);
        mBuilder.addAction(R.drawable.ic_launcher, "Second", resultPendingIntent);

        //inbox style--------------------------------------------------------------------------------

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        inboxStyle.setBigContentTitle("Big content title");
        inboxStyle.setSummaryText("Summary text");

        // Moves events into the expanded layout
        for (int i=0; i < 5; i++) {
            inboxStyle.addLine(i + ": inbox style line");
        }
        mBuilder.setStyle(inboxStyle);

        //big picture style---------------------------------------------------------------------------

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle("Big content title");
        bigPictureStyle.setSummaryText("Summary text");
        bigPictureStyle.bigPicture(bitmap);
        //mBuilder.setStyle(bigPictureStyle);

        //big text style-------------------------------------------------------------------------------

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText("Big text");
        bigTextStyle.setBigContentTitle("Big content tite");
        bigTextStyle.setSummaryText("Summary text");
        //mBuilder.setStyle(bigTextStyle);

        //show the notification
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
