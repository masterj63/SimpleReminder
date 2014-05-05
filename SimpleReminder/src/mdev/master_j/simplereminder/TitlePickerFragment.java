package mdev.master_j.simplereminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class TitlePickerFragment extends DialogFragment {
	private EditText titleEditText;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		titleEditText = new EditText(getActivity());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		titleEditText.setLayoutParams(params);

		ReminderActivity reminderActivity = (ReminderActivity) getActivity();
		String formTitle = reminderActivity.getFormTitle();
		if (formTitle.length() > 0)
			titleEditText.setText(formTitle);

		builder.setTitle(R.string.label_dialog_title);
		builder.setView(titleEditText);

		OnClickListener listener = new OnButtonClickListener();
		builder.setPositiveButton(android.R.string.ok, listener);
		builder.setNegativeButton(android.R.string.cancel, listener);

		return builder.create();
	}

	private class OnButtonClickListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == Dialog.BUTTON_POSITIVE) {
				ReminderActivity reminderActivity = (ReminderActivity) getActivity();
				String string = titleEditText.getText().toString().trim();
				if (string.length() == 0) {
					Toast.makeText(getActivity(), R.string.message_empty_title, Toast.LENGTH_SHORT).show();
				} else if (string.length() > getResources().getInteger(R.integer.max_length_title)) {
					Toast.makeText(getActivity(), R.string.message_too_long_title, Toast.LENGTH_SHORT).show();
				} else {
					reminderActivity.updateTitle(string);
				}
			}
		}

	}
}
