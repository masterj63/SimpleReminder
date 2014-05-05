package mdev.master_j.simplereminder;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	static final String ACTION_DATE = "mdev.master_j.simplereminder.DatePickerFragment.ACTION_DATE";

	static final String KEY_YEAR = "mdev.master_j.simplereminder.DatePickerFragment.KEY_YEAR";
	static final String KEY_MONTH = "mdev.master_j.simplereminder.DatePickerFragment.KEY_MONTH";
	static final String KEY_DAY = "mdev.master_j.simplereminder.DatePickerFragment.KEY_DAY";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ReminderActivity reminderActivity = (ReminderActivity) getActivity();

		int year;
		int month;
		int day;

		if (reminderActivity.getFormDateSet()) {
			year = reminderActivity.getFormYear();
			month = reminderActivity.getFormMonth();
			day = reminderActivity.getFormDay();
		} else {
			Calendar now = Calendar.getInstance();
			year = now.get(Calendar.YEAR);
			month = now.get(Calendar.MONTH);
			day = now.get(Calendar.DAY_OF_MONTH);
		}

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		ReminderActivity reminderActivity = (ReminderActivity) getActivity();
		reminderActivity.updateDate(year, monthOfYear, dayOfMonth);
	}
}
