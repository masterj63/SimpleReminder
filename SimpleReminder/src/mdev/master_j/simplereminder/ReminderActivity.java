package mdev.master_j.simplereminder;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReminderActivity extends ActionBarActivity {

	static final int INDEX_ITEM_TITLE = 0;
	static final int INDEX_ITEM_DATE = 1;
	static final int INDEX_ITEM_TIME = 2;
	static final int INDEX_ITEM_DESCRIPTION = 3;

	private boolean formDateSet = false;
	private int formYear;
	private int formMonth;
	private int formDay;

	private boolean formTimeSet = false;
	private int formHour;
	private int formMinute;

	private String formTitle = "";
	private String formDescription = "";

	private static final String KEY_SAVED_TIME_MS = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_TIME_MS";
	private static final String KEY_SAVED_TITLE = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_TITLE";
	private static final String KEY_SAVED_DESCRIPTION = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_DESCRIPTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);

			long savedTime = preferences.getLong(KEY_SAVED_TIME_MS, 0L);
			if (savedTime != 0L) {
				Calendar calendar = Calendar.getInstance();

				updateFormSavedDateAndTime(calendar);
			}

			formTitle = preferences.getString(KEY_SAVED_TITLE, "");
			formDescription = preferences.getString(KEY_SAVED_DESCRIPTION, "");
		} else {
			long savedTime = savedInstanceState.getLong(KEY_SAVED_TIME_MS, 0L);
			if (savedTime != 0L) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(savedTime);

				updateFormSavedDateAndTime(calendar);
			}

			formTitle = savedInstanceState.getString(KEY_SAVED_TITLE);
			if (formTitle == null)
				formTitle = "";

			formDescription = savedInstanceState.getString(KEY_SAVED_DESCRIPTION);
			if (formDescription == null)
				formDescription = "";
		}

		CustomAdapter adapter = new CustomAdapter(this);
		ListView lV = (ListView) findViewById(R.id.listView1);
		adapter.add("");
		adapter.add("");
		adapter.add("");
		adapter.add("");
		lV.setAdapter(adapter);
		lV.setOnItemClickListener(new OnFormItemClickListener());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		Calendar formCalendar = getCalendarByForm();
		if (formCalendar != null) {
			long formTimeMillis = formCalendar.getTimeInMillis();

			outState.putLong(KEY_SAVED_TIME_MS, formTimeMillis);
			outState.putString(KEY_SAVED_TITLE, formTitle);
			outState.putString(KEY_SAVED_DESCRIPTION, formDescription);
		}
	}

	private class OnFormItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case INDEX_ITEM_TITLE:
				// TODO title dialog
				break;
			case INDEX_ITEM_DATE:
				// TODO date dialog
				break;
			case INDEX_ITEM_TIME:
				// TODO time dialog
				break;
			case INDEX_ITEM_DESCRIPTION:
				// TODO description dialog
				break;
			}
		}
	}

	private Calendar getCalendarByForm() {
		if (!formDateSet || !formTimeSet) {
			return null;
		}

		Calendar formCalendar = Calendar.getInstance();

		formCalendar.set(Calendar.YEAR, formYear);
		formCalendar.set(Calendar.MONTH, formMonth);
		formCalendar.set(Calendar.DAY_OF_MONTH, formDay);
		formCalendar.set(Calendar.HOUR_OF_DAY, formHour);
		formCalendar.set(Calendar.MINUTE, formMinute);

		return formCalendar;
	}

	private boolean isFormInputCorrect() {
		if (!formDateSet || !formTimeSet) {
			return false;
		}

		Calendar formCalendar = getCalendarByForm();

		Calendar nowCalendar = Calendar.getInstance();

		if (formCalendar.compareTo(nowCalendar) <= 0) {
			return false;
		}

		return true;
	}

	private void updateFormSavedDateAndTime(Calendar calendar) {
		formDateSet = true;
		formTimeSet = true;

		formYear = calendar.get(Calendar.YEAR);
		formMonth = calendar.get(Calendar.MONTH);
		formMonth = calendar.get(Calendar.DAY_OF_MONTH);
		formHour = calendar.get(Calendar.HOUR_OF_DAY);
		formMinute = calendar.get(Calendar.MINUTE);
	}

	class CustomAdapter extends ArrayAdapter<String> {
		private final Context CONTEXT;

		public CustomAdapter(Context context) {
			super(context, R.layout.list_item);
			CONTEXT = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_item, parent, false);

			TextView header = (TextView) rowView.findViewById(R.id.textview_header);
			TextView footer = (TextView) rowView.findViewById(R.id.textview_footer);

			switch (position) {
			case ReminderActivity.INDEX_ITEM_TITLE:
				header.setText(R.string.label_title);
				break;
			case ReminderActivity.INDEX_ITEM_DATE:
				header.setText(R.string.label_date);
				break;
			case ReminderActivity.INDEX_ITEM_TIME:
				header.setText(R.string.label_time);
				break;
			case ReminderActivity.INDEX_ITEM_DESCRIPTION:
				header.setText(R.string.label_description);
				break;
			}

			return rowView;
		}
	}
}
