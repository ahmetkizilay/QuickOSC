package com.ahmetkizilay.controls.osc;

import com.ahmetkizilay.controls.osc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * SeekBarOSCSettingActivity
 * On this screen, the OSC messages are configured for SeekBar controls.
 * msgValueChanged, minValue, maxValue intent data are passed from the caller Activity.
 * At the end, values are passed back to the caller with the same parameter names.
 * The $ sign is used as a wildcard element to replace the scaled progress value in the final OSC message.
 * @author ahmetkizilay
 *
 */
public class SeekBarOSCSettingActivity extends Activity implements OnClickListener {

	private EditText editTextValueChanged;
	private EditText editTextMinValue;
	private EditText editTextMaxValue;

	private Button btnSeekSave;
	private Button btnSeekCancel;
	
	private int selectedIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seekbar_osc_layout);
		
		Intent originalIntent = getIntent(); 
		String msgValueChanged = originalIntent.getStringExtra("msgValueChanged");
		float msgMinValue = originalIntent.getFloatExtra("minValue", 0);
		float msgMaxValue = originalIntent.getFloatExtra("maxValue", 100);
		this.selectedIndex = originalIntent.getIntExtra("index", 0);
		
		editTextValueChanged = (EditText) findViewById(R.id.etSeekMessage);		
		if(msgValueChanged != null && !msgValueChanged.equalsIgnoreCase("")) {	
			editTextValueChanged.setText(msgValueChanged);
		}
		
		editTextMinValue = (EditText) findViewById(R.id.etSeekMinValue);		
		editTextMinValue.setText(Float.toString(msgMinValue));
				
		editTextMaxValue = (EditText) findViewById(R.id.etSeekMaxValue);		
		editTextMaxValue.setText(Float.toString(msgMaxValue));
		
		btnSeekSave = (Button) findViewById(R.id.btnSeekSave);
		btnSeekSave.setOnClickListener(this);
		
		btnSeekCancel = (Button) findViewById(R.id.btnSeekCancel);
		btnSeekCancel.setOnClickListener(this);

	}

	public void onClick(View view) {
		Intent data = new Intent();

		if (view.equals(btnSeekSave)) {
			data.putExtra("msgValueChanged", editTextValueChanged.getText());
			data.putExtra("maxValue", editTextMaxValue.getText());
			data.putExtra("minValue", editTextMinValue.getText());
			data.putExtra("index", this.selectedIndex);
			
			setResult(Activity.RESULT_OK, data);
		} else if (view.equals(btnSeekCancel)) {
			setResult(Activity.RESULT_CANCELED, data);
		} else {
			// you should not be here
			data.putExtra("errorMessage", "weird error");
			setResult(Activity.RESULT_CANCELED, data);
		}

		finish();

	}
}
