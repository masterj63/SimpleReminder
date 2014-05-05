package mdev.master_j.simplereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
			return;

		SharedPreferences preferences = context.getSharedPreferences(ReminderActivity.NAME_PREFERENCES,
				Context.MODE_PRIVATE);

		if (!preferences.contains(ReminderActivity.KEY_SAVED_YEAR))
			return;

		int year = preferences.getInt(ReminderActivity.KEY_SAVED_YEAR, 0);
		int month = preferences.getInt(ReminderActivity.KEY_SAVED_MONTH, 0);
		int day = preferences.getInt(ReminderActivity.KEY_SAVED_DAY, 0);

		int hour = preferences.getInt(ReminderActivity.KEY_SAVED_HOUR, 0);
		int minute = preferences.getInt(ReminderActivity.KEY_SAVED_MINUTE, 0);

		// Calendar calendar = ReminderActivity.getCalendar(year, month, day,
		// hour, minute);
		if (!ReminderActivity.isDateInFuture(year, month, day, hour, minute))
			return;

		String title = preferences.getString(ReminderActivity.KEY_SAVED_TITLE, "");
		String description = preferences.getString(ReminderActivity.KEY_SAVED_DESCRIPTION, "");

		ReminderActivity.saveDateAndSetAlarm(context, title, description, year, month, day, hour, minute);
	}
}
