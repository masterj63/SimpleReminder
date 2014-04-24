package mdev.master_j.simplereminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;

public class TitlePickerFragment extends DialogFragment {
	private EditText titleEditText;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		titleEditText = new EditText(getActivity());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		titleEditText.setLayoutParams(params);

		builder.setMessage("message");
		builder.setTitle("title");
		builder.setView(titleEditText);

		OnClickListener listener = new OnButtonClickListener();
		builder.setPositiveButton("ok", listener);
		builder.setNegativeButton("cancel", listener);

		return builder.create();
	}

	private class OnButtonClickListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case Dialog.BUTTON_NEGATIVE:
				Log.d("mj", "-");
				break;

			case Dialog.BUTTON_POSITIVE:
				Log.d("mj", "+");
				ReminderActivity reminderActivity = (ReminderActivity) getActivity();
				reminderActivity.updateTitle(titleEditText.getText().toString());
				break;
			}
		}

	}
}
