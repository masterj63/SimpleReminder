package mdev.master_j.simplereminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;

public class DescriptionPickerDialog extends DialogFragment {
	private EditText descriptionEditText;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		descriptionEditText = new EditText(getActivity());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		descriptionEditText.setLayoutParams(params);

		ReminderActivity reminderActivity = (ReminderActivity) getActivity();
		String formDescription = reminderActivity.getFormDescription();
		if (formDescription.length() > 0)
			descriptionEditText.setText(formDescription);

		builder.setTitle(R.string.label_dialog_description);
		builder.setView(descriptionEditText);

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
				String string = descriptionEditText.getText().toString().trim();
				reminderActivity.updateDescription(string);
			}
		}

	}

}
