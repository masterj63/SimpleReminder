package mdev.master_j.simplereminder;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

}
