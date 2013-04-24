package com.ahmetkizilay.controls.osc;

import com.ahmetkizilay.controls.osc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;


/**
 * ButtonOSCSettingActivity
 * On this screen, the OSC messages are configured for Button controls.
 * msgButtonPressed, msgButtonReleased, trigButtonReleased intent data are passed from the caller Activity.
 * At the end, values are passed back to the caller with the same parameter names.
 * @author ahmetkizilay
 *
 */
public class ButtonOSCSettingActivity extends Activity implements OnClickListener{

	private EditText editTextButtonPressed;
	private EditText editTextButtonReleased;
	private CheckBox checkBoxTrigButtonReleased;
	
	private Button btnButtonSave;
	private Button btnButtonCancel;
	
	private int selectedIndex; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_osc_layout);
		
		Intent originalIntent = getIntent(); 
		String msgButtonPressed = originalIntent.getStringExtra("msgButtonPressed");
		String msgButtonReleased = originalIntent.getStringExtra("msgButtonReleased");
		boolean trigButtonReleased = originalIntent.getBooleanExtra("trigButtonReleased", false);
		this.selectedIndex = originalIntent.getIntExtra("index", 0);
		
		editTextButtonPressed = (EditText) findViewById(R.id.etButtonPressed);
		
		if(msgButtonPressed != null && !msgButtonPressed.equalsIgnoreCase("")) {	
			editTextButtonPressed.setText(msgButtonPressed);
		}
		
		editTextButtonReleased = (EditText) findViewById(R.id.etButtonReleased);
		if(msgButtonReleased != null && !msgButtonReleased.equalsIgnoreCase("")) {

			editTextButtonReleased.setText(msgButtonReleased);
		}
		
		checkBoxTrigButtonReleased = (CheckBox) findViewById(R.id.cbButtonReleased);
		checkBoxTrigButtonReleased.setChecked(trigButtonReleased);
		if(!checkBoxTrigButtonReleased.isChecked()) {
			editTextButtonReleased.setEnabled(false);
		}
		checkBoxTrigButtonReleased.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				editTextButtonReleased.setEnabled(isChecked);				
			}
		});

		
		btnButtonSave = (Button) findViewById(R.id.btnButtonSave);
		btnButtonSave.setOnClickListener(this);
		
		btnButtonCancel = (Button) findViewById(R.id.btnButtonCancel);
		btnButtonCancel.setOnClickListener(this);
	}

	public void onClick(View view) {
		Intent data = new Intent();
		
		if(view.equals(btnButtonSave)) {
			data.putExtra("msgButtonPressed", editTextButtonPressed.getText());
			data.putExtra("msgButtonReleased", editTextButtonReleased.getText());
			data.putExtra("trigButtonReleased", checkBoxTrigButtonReleased.isChecked());
			data.putExtra("index", this.selectedIndex);
			
			setResult(Activity.RESULT_OK, data);	
		}
		else if(view.equals(btnButtonCancel)) {			
			setResult(Activity.RESULT_CANCELED, data);
		}
		else {
			// you should not be here
			data.putExtra("errorMessage", "weird error");
			setResult(Activity.RESULT_CANCELED, data);
		}
		
		finish();
	}
}
