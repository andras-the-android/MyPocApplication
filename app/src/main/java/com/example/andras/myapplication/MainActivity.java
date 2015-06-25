package com.example.andras.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static final int mId = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setupNotifications();

    }


    private void setupNotifications() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cannond);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        //.setSmallIcon(R.drawable.icon_blue_86x86_stride)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setLargeIcon(bitmap)
                        .setAutoCancel(true);
        //.setProgress(0, 0, true);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
// Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        inboxStyle.setSummaryText("This is the summary. Enjoy!");

// Moves events into the expanded layout
        for (int i=0; i < 5; i++) {

            inboxStyle.addLine(i + ": sdvoskdvpovw");
        }

        NotificationCompat.BigPictureStyle notiStyle = new
                NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle("Big Picture Expanded");
        notiStyle.setSummaryText("Nice big picture.");
        //notiStyle.bigText("Big text");
        notiStyle.setBigContentTitle("big content tite");
        notiStyle.setSummaryText("Summary text");


// Add the big picture to the style.
        notiStyle.bigPicture(bitmap);


// Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        //stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        //stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT );
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.addAction(R.drawable.ic_launcher, "First", resultPendingIntent);
        mBuilder.addAction(R.drawable.ic_launcher, "Second", resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
//        mNotificationManager.notify(mId, mBuilder.build());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("onPrepareOptionsMenuxxx");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (menu.size() == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            System.out.println("menusize: " + menu.size());
        }
//        MenuItem item = menu.findItem(R.id.codeversed_logo);
//        System.out.println("onCreateContextMenuxxx" + item.isEnabled());
        //item.setEnabled(!item.isEnabled());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        TextView actionView = (TextView) menu.findItem(R.id.action_settings).getActionView();
        actionView.setText("I'm action view!");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    public void onDragNDropButtonClick(View view) {
        startActivity(new Intent(this, DragDropActivity.class));
    }
}
