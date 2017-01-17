package pt.ismai.hungryme.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.Recipes.RecipeDetailActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, FavoritesActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent settings_intent = new Intent(context, SettingsActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentSettings = PendingIntent.getActivity(context,100,settings_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Let's Eat!")
                .setContentText("We've prepared some recipes for you.")
                .addAction(R.drawable.ic_action_next, "Open", pendingIntent)
                .addAction(R.drawable.settings, "Settings", pendingIntentSettings)
                .setContentIntent(pendingIntent).getNotification();

        builder.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, builder);
    }
}
