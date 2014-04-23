package mdev.master_j.simplereminder;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ReminderActivity reminderActivity = (ReminderActivity) getActivity();

		int hour;
		int minute;

		if (reminderActivity.getFormTimeSet()) {
			hour = reminderActivity.getFormHour();
			minute = reminderActivity.getFormMinute();
		} else {
			Calendar now = Calendar.getInstance();
			hour = now.get(Calendar.HOUR_OF_DAY);
			minute = now.get(Calendar.MINUTE);
		}

		return new TimePickerDialog(getActivity(), this, hour, minute, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		ReminderActivity reminderActivity = (ReminderActivity) getActivity();
		reminderActivity.updateTime(hourOfDay, minute);
	}

}
