package mdev.master_j.simplereminder;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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

	private static final String KEY_SAVED_TIME_MS = "mdev.master_j.simplereminder.ReminderActivity.KEY_SAVED_TIME_MS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState != null) {
			long savedTime = savedInstanceState.getLong(KEY_SAVED_TIME_MS);
			if (savedTime != 0L) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(savedTime);
				formYear = c.get(Calendar.YEAR);
				formMonth = c.get(Calendar.MONTH);
				formDay = c.get(Calendar.DAY_OF_MONTH);
				formHour = c.get(Calendar.HOUR_OF_DAY);
				formMinute = c.get(Calendar.MINUTE);
				formDateSet = true;
				formTimeSet = true;
			}
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

	private boolean isFormInputCorrect() {
		if (!formDateSet || !formTimeSet)
			return false;

		Calendar formCalendar = Calendar.getInstance();
		formCalendar.set(Calendar.YEAR, formYear);
		formCalendar.set(Calendar.MONTH, formMonth);
		formCalendar.set(Calendar.DAY_OF_MONTH, formDay);
		formCalendar.set(Calendar.HOUR_OF_DAY, formHour);
		formCalendar.set(Calendar.MINUTE, formMinute);

		Calendar nowCalendar = Calendar.getInstance();

		if (formCalendar.compareTo(nowCalendar) <= 0)
			return false;

		return true;
	}
}
