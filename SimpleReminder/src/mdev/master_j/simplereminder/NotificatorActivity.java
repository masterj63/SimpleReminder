package mdev.master_j.simplereminder;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;

public class NotificatorActivity extends Activity {
	static final String EXTRA_TITLE = "mdev.master_j.simplereminder.NotificatorActivity.EXTRA_TITLE";
	static final String EXTRA_DESCRIPTION = "mdev.master_j.simplereminder.NotificatorActivity.EXTRA_DESCRIPTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent starterIntent = getIntent();
		String title = starterIntent.getStringExtra(EXTRA_TITLE);
		String description = starterIntent.getStringExtra(EXTRA_DESCRIPTION);

		Intent intent = new Intent(this, ReminderActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle(title);
		builder.setContentText(description);
		builder.setOngoing(false);
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());

		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "");
		wl.acquire(5500);

		finish();
	}
}
