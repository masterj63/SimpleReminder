package mdev.master_j.simplereminder;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

final class Reminder {
	static final Reminder INSTANCE = new Reminder();

	static final String NAME_PREFERENCES = "mdev.master_j.simplereminder.NAME_PREFERENCES";

	static final String KEY_SAVED_YEAR = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_YEAR";
	static final String KEY_SAVED_MONTH = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_MONTH";
	static final String KEY_SAVED_DAY = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_DAY";

	static final String KEY_SAVED_HOUR = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_HOUR";
	static final String KEY_SAVED_MINUTE = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_MINUTE";

	static final String KEY_SAVED_TITLE = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_TITLE";
	static final String KEY_SAVED_DESCRIPTION = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_DESCRIPTION";

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private String title;
	private String description;

	private Reminder() {
	}

	void restoreFromPreferences(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE);

		year = preferences.getInt(KEY_SAVED_YEAR, 0);
		month = preferences.getInt(KEY_SAVED_MONTH, 0);
		day = preferences.getInt(KEY_SAVED_DAY, 0);

		hour = preferences.getInt(KEY_SAVED_HOUR, 0);
		minute = preferences.getInt(KEY_SAVED_MINUTE, 0);

		title = preferences.getString(KEY_SAVED_TITLE, "");
		description = preferences.getString(KEY_SAVED_DESCRIPTION, "");
	}

	void restoreFromBundle(Bundle bundle) {

	}

	void scheduleAlarm(Context context) {
		SharedPreferences.Editor editor = context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE).edit();

		editor.putString(KEY_SAVED_TITLE, title);
		editor.putString(KEY_SAVED_DESCRIPTION, description);

		editor.putInt(KEY_SAVED_YEAR, year);
		editor.putInt(KEY_SAVED_MONTH, month);
		editor.putInt(KEY_SAVED_DAY, day);

		editor.putInt(KEY_SAVED_HOUR, hour);
		editor.putInt(KEY_SAVED_MINUTE, minute);

		editor.commit();

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, NotificatorService.class);
		intent.putExtra(NotificatorService.EXTRA_TITLE, title);
		intent.putExtra(NotificatorService.EXTRA_DESCRIPTION, description);

		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		long timeMillis = getCalendar().getTimeInMillis();
		timeMillis /= 1000;
		timeMillis *= 1000;

		// alarmManager.cancel(pendingIntent);
		alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);
	}

	private static Calendar getCalendar(int year, int month, int day, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	private Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}
}
