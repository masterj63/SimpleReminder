package mdev.master_j.simplereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
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
