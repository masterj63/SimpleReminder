package mdev.master_j.simplereminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

	// private static final String KEY_SAVED_TIME_MS =
	// "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_TIME_MS";

	private static final String KEY_SAVED_YEAR = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_YEAR";
	private static final String KEY_SAVED_MONTH = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_MONTH";
	private static final String KEY_SAVED_DAY = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_DAY";

	private static final String KEY_SAVED_HOUR = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_HOUR";
	private static final String KEY_SAVED_MINUTE = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_MINUTE";

	private static final String KEY_SAVED_TITLE = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_TITLE";
	private static final String KEY_SAVED_DESCRIPTION = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_DESCRIPTION";

	private CustomAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);

			if (preferences.contains(KEY_SAVED_YEAR)) {
				formDateSet = true;
				formTimeSet = true;

				formYear = preferences.getInt(KEY_SAVED_YEAR, 0);
				formMonth = preferences.getInt(KEY_SAVED_MONTH, 0);
				formDay = preferences.getInt(KEY_SAVED_DAY, 0);

				formHour = preferences.getInt(KEY_SAVED_HOUR, 0);
				formMinute = preferences.getInt(KEY_SAVED_MINUTE, 0);
			}
			// else {
			// formDateSet = false;
			// formTimeSet = false;
			// }

			formTitle = preferences.getString(KEY_SAVED_TITLE, "");
			formDescription = preferences.getString(KEY_SAVED_DESCRIPTION, "");
		} else {
			if (savedInstanceState.containsKey(KEY_SAVED_YEAR)) {
				formDateSet = true;

				formYear = savedInstanceState.getInt(KEY_SAVED_YEAR);
				formMonth = savedInstanceState.getInt(KEY_SAVED_MONTH);
				formDay = savedInstanceState.getInt(KEY_SAVED_DAY);
			}

			if (savedInstanceState.containsKey(KEY_SAVED_HOUR)) {
				formTimeSet = true;

				formHour = savedInstanceState.getInt(KEY_SAVED_HOUR);
				formMinute = savedInstanceState.getInt(KEY_SAVED_MINUTE);
			}

			formTitle = savedInstanceState.getString(KEY_SAVED_TITLE);
			if (formTitle == null)
				formTitle = "";

			formDescription = savedInstanceState.getString(KEY_SAVED_DESCRIPTION);
			if (formDescription == null)
				formDescription = "";
		}

		listAdapter = new CustomAdapter(this);
		ListView lV = (ListView) findViewById(R.id.listView1);
		listAdapter.add("");
		listAdapter.add("");
		listAdapter.add("");
		listAdapter.add("");
		lV.setAdapter(listAdapter);
		lV.setOnItemClickListener(new OnFormItemClickListener());

		Button saveButton = (Button) findViewById(R.id.button_save);
		saveButton.setOnClickListener(new OnSaveButtonClickListener());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (formDateSet) {
			outState.putInt(KEY_SAVED_YEAR, formYear);
			outState.putInt(KEY_SAVED_MONTH, formMonth);
			outState.putInt(KEY_SAVED_DAY, formDay);
		}

		if (formTimeSet) {
			outState.putInt(KEY_SAVED_HOUR, formHour);
			outState.putInt(KEY_SAVED_MINUTE, formMinute);
		}

		outState.putString(KEY_SAVED_TITLE, formTitle);
		outState.putString(KEY_SAVED_DESCRIPTION, formDescription);
	}

	private class OnFormItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case INDEX_ITEM_TITLE:
				DialogFragment titleDialog = new TitlePickerFragment();
				titleDialog.show(getFragmentManager(), "");
				break;
			case INDEX_ITEM_DATE:
				DialogFragment dateDialog = new DatePickerFragment();
				dateDialog.show(getFragmentManager(), "");
				break;
			case INDEX_ITEM_TIME:
				DialogFragment timeDialog = new TimePickerFragment();
				timeDialog.show(getFragmentManager(), "");
				break;
			case INDEX_ITEM_DESCRIPTION:
				DialogFragment descriptionDialog = new DescriptionPickerDialog();
				descriptionDialog.show(getFragmentManager(), "");
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
		if (!formDateSet || !formTimeSet || formTitle.length() == 0) {
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
		formDay = calendar.get(Calendar.DAY_OF_MONTH);
		formHour = calendar.get(Calendar.HOUR_OF_DAY);
		formMinute = calendar.get(Calendar.MINUTE);
	}

	void updateTitle(String title) {
		formTitle = title;

		listAdapter.notifyDataSetChanged();
	}

	void updateDescription(String description) {
		formDescription = description;

		listAdapter.notifyDataSetChanged();
	}

	void updateDate(int year, int month, int day) {
		formDateSet = true;

		formYear = year;
		formMonth = month;
		formDay = day;

		listAdapter.notifyDataSetChanged();
	}

	void updateTime(int hour, int minute) {
		formTimeSet = true;
		formHour = hour;
		formMinute = minute;

		listAdapter.notifyDataSetChanged();
	}

	private class OnSaveButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (!isFormInputCorrect()) {
				Toast.makeText(ReminderActivity.this, R.string.message_fill_up_form, Toast.LENGTH_SHORT).show();
				return;
			}

			SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

			editor.putString(KEY_SAVED_TITLE, formTitle);
			editor.putString(KEY_SAVED_DESCRIPTION, formDescription);

			editor.putInt(KEY_SAVED_YEAR, formYear);
			editor.putInt(KEY_SAVED_MONTH, formMonth);
			editor.putInt(KEY_SAVED_DAY, formDay);

			editor.putInt(KEY_SAVED_HOUR, formHour);
			editor.putInt(KEY_SAVED_MINUTE, formMinute);

			editor.commit();
		}
	}

	private class CustomAdapter extends ArrayAdapter<String> {
		private final Context CONTEXT;

		public CustomAdapter(Context context) {
			super(context, R.layout.list_item);
			CONTEXT = context;
		}

		@SuppressLint("SimpleDateFormat")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_item, parent, false);

			TextView header = (TextView) rowView.findViewById(R.id.textview_header);
			TextView footer = (TextView) rowView.findViewById(R.id.textview_footer);

			int headerTextId = R.string.app_name;
			String footerText = null;

			switch (position) {
			// TODO italic footer_not_set
			case ReminderActivity.INDEX_ITEM_TITLE:
				headerTextId = R.string.label_title;

				if (formTitle.length() > 0) {
					footerText = formTitle;
				}
				break;

			case ReminderActivity.INDEX_ITEM_DATE:
				headerTextId = R.string.label_date;

				if (formDateSet) {
					Calendar calendar = Calendar.getInstance();

					calendar.set(Calendar.YEAR, formYear);
					calendar.set(Calendar.MONTH, formMonth);
					calendar.set(Calendar.DAY_OF_MONTH, formDay);

					SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
					String label = format.format(calendar.getTime());

					footerText = label;
				}
				break;

			case ReminderActivity.INDEX_ITEM_TIME:
				headerTextId = R.string.label_time;

				if (formTimeSet) {
					Calendar calendar = Calendar.getInstance();

					calendar.set(Calendar.HOUR_OF_DAY, formHour);
					calendar.set(Calendar.MINUTE, formMinute);

					SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
					String label = format.format(calendar.getTime());

					footerText = label;
				}
				break;

			case ReminderActivity.INDEX_ITEM_DESCRIPTION:
				headerTextId = R.string.label_description;

				if (formDescription.length() > 0) {
					footerText = formDescription;
				}
				break;
			}

			if (headerTextId != R.string.app_name) {
				header.setText(headerTextId);
			}

			if (footerText == null) {
				footer.setText(R.string.label_footer_not_set);
				footer.setTypeface(null, Typeface.ITALIC);
				footer.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			} else {
				footer.setText(footerText);
			}

			return rowView;
		}
	}

	boolean getFormDateSet() {
		return formDateSet;
	}

	int getFormYear() {
		return formYear;
	}

	int getFormMonth() {
		return formMonth;
	}

	int getFormDay() {
		return formDay;
	}

	boolean getFormTimeSet() {
		return formTimeSet;
	}

	int getFormHour() {
		return formHour;
	}

	int getFormMinute() {
		return formMinute;
	}

	String getFormTitle() {
		return formTitle;
	}

	String getFormDescription() {
		return formDescription;
	}

}
