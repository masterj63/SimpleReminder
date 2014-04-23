package mdev.master_j.simplereminder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class ReminderActivity extends ActionBarActivity {

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
	}

}
