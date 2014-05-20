package mdev.master_j.simplereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
			return;

		Reminder.INSTANCE.restoreFromPreferences(context);

		ReminderActivity.saveDateAndSetAlarm(context, title, description, year, month, day, hour, minute);
	}
}
