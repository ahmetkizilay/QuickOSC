package com.ahmetkizilay.controls.osc;


import java.net.InetAddress;

import com.ahmetkizilay.controls.osc.R;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/***
 * Entry point for the application.
 * 
 * Each view (Button, Toggle and SeekBar) has a corresponding Wrapper object which takes care of storing 
 * OSC message strings, managing listeners and passing OSC messages to the parent activity class to be sent to the host.
 * 
 * 
 * @author ahmetkizilay
 *
 */
public class QuickOSCActivity extends Activity {
	private final static int BUTTON_OSC_INTENT_RESULT = 1;
    private final static int TOGGLE_OSC_INTENT_RESULT = 2;
    private final static int SEEKBAR_OSC_INTENT_RESULT = 3;
    
    private final static int ABOUT_DIALOG = 1;
    private final static int NETWORK_DIALOG = 2;
    
    private ButtonOSCWrapper selectedButtonOSCWrapper;
    private ToggleOSCWrapper selectedToggleOSCWrapper;
    private SeekBarOSCWrapper selectedSeekBarOSCWrapper;

    private boolean editMode = false;

    
    private String ipAddress = "127.0.0.1";
    private int port = 8000;
    private OSCPortOut oscPortOut = null;    
    
	TextView debugTextView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        initializeOSC();
        
        debugTextView = (TextView) findViewById(R.id.textView1);
        
        Button button1 = (Button) findViewById(R.id.button1);
        ButtonOSCWrapper.createInstance("btn1", button1, this);
                
        Button button2 = (Button) findViewById(R.id.button2);
        ButtonOSCWrapper.createInstance("btn2", button2, this);
        
        Button button3 = (Button) findViewById(R.id.button3);
        ButtonOSCWrapper.createInstance("btn3", button3, this);
        
        Button button4 = (Button) findViewById(R.id.button4);
        ButtonOSCWrapper.createInstance("btn4", button4, this);
        
        Button button5 = (Button) findViewById(R.id.button5);
        ButtonOSCWrapper.createInstance("btn5", button5, this);
        
        Button button6 = (Button) findViewById(R.id.button6);
        ButtonOSCWrapper.createInstance("btn6", button6, this);
        
        Button button7 = (Button) findViewById(R.id.button7);
        ButtonOSCWrapper.createInstance("btn7", button7, this);
        
        Button button8 = (Button) findViewById(R.id.button8);
        ButtonOSCWrapper.createInstance("btn8", button8, this);
        
        Button button9 = (Button) findViewById(R.id.button9);
        ButtonOSCWrapper.createInstance("btn9", button9, this);
        
        Button button10 = (Button) findViewById(R.id.button10);
        ButtonOSCWrapper.createInstance("btn10", button10, this);
        
        Button button11 = (Button) findViewById(R.id.button11);
        ButtonOSCWrapper.createInstance("btn11", button11, this);
        
        Button button12 = (Button) findViewById(R.id.button12);
        ButtonOSCWrapper.createInstance("btn12", button12, this);
        
        Button button13 = (Button) findViewById(R.id.button13);
        ButtonOSCWrapper.createInstance("btn13", button13, this);
        
        Button button14 = (Button) findViewById(R.id.button14);
        ButtonOSCWrapper.createInstance("btn14", button14, this);
        
        Button button15 = (Button) findViewById(R.id.button15);
        ButtonOSCWrapper.createInstance("btn15", button15, this);
        
        Button button16 = (Button) findViewById(R.id.button16);
        ButtonOSCWrapper.createInstance("btn16", button16, this);
        
        ToggleButton toggle1 = (ToggleButton) findViewById(R.id.toggleButton1);
        ToggleOSCWrapper.createInstance("tog1", toggle1, this);
        
        ToggleButton toggle2 = (ToggleButton) findViewById(R.id.toggleButton2);
        ToggleOSCWrapper.createInstance("tog2", toggle2, this);
        
        ToggleButton toggle3 = (ToggleButton) findViewById(R.id.toggleButton3);
        ToggleOSCWrapper.createInstance("tog3", toggle3, this);
        
        ToggleButton toggle4 = (ToggleButton) findViewById(R.id.toggleButton4);
        ToggleOSCWrapper.createInstance("tog4", toggle4, this);
        
        ToggleButton toggle5 = (ToggleButton) findViewById(R.id.toggleButton5);
        ToggleOSCWrapper.createInstance("tog5", toggle5, this);
        
        ToggleButton toggle6 = (ToggleButton) findViewById(R.id.toggleButton6);
        ToggleOSCWrapper.createInstance("tog6", toggle6, this);
        
        ToggleButton toggle7 = (ToggleButton) findViewById(R.id.toggleButton7);
        ToggleOSCWrapper.createInstance("tog7", toggle7, this);
        
        ToggleButton toggle8 = (ToggleButton) findViewById(R.id.toggleButton8);
        ToggleOSCWrapper.createInstance("tog8", toggle8, this);
        
        SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        SeekBarOSCWrapper.createInstance("seekBar1", seekBar1, this);
        
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        SeekBarOSCWrapper.createInstance("seekBar2", seekBar2, this);
        
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        SeekBarOSCWrapper.createInstance("seekBar3", seekBar3, this);
        
        SeekBar seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        SeekBarOSCWrapper.createInstance("seekBar4", seekBar4, this);

    }
    
    
    public boolean isEditMode() {
    	return this.editMode;
    }
    
    /***
     * Normally used to display the OSC message passed by the wrappers.
     * @param message
     */
    public void setDebugMessage(String message) {
    	this.debugTextView.setText(message);
    }
    
    /***
     * In edit mode, the ButtonOSCWrapper calls this method and passes itself as the argument.
     * An intent object is created with the properties of the wrapper object attached.
     * Upon successful return of the intent result, the new values are passed back to the wrapper object
     * in the handleButtonOSCSettingResult method.
     * @param selectedButton
     */
    public void callButtonOSCSetter(ButtonOSCWrapper selectedButton) {
    	try {
    		selectedButtonOSCWrapper = selectedButton;
    		
			Intent intent = new Intent(this, ButtonOSCSettingActivity.class);
			intent.setAction("com.ahmetkizilay.controls.osc.ButtonOSCSetter");
			intent.putExtra("msgButtonPressed", selectedButton.getMessageButtonPressed());
			intent.putExtra("msgButtonReleased", selectedButton.getMessageButtonReleased());
			intent.putExtra("trigButtonReleased", selectedButton.getTriggerWhenButtonReleased());
			startActivityForResult(intent, BUTTON_OSC_INTENT_RESULT);
		} catch(Throwable t) {
			t.printStackTrace();
			Toast.makeText(this, "Error calling ButtonOSCSetter Intent", Toast.LENGTH_LONG).show();
		}
    }
    
    /***
     * In edit mode, the ToggleOSCWrapper calls this method and passes itself as the argument.
     * An intent object is created with the properties of the wrapper object attached.
     * Upon successful return of the intent result, the new values are passed back to the wrapper object
     * in the handleToggleOSCSettingResult method.
     * @param selectedToggle
     */
    public void callToggleOSCSetter(ToggleOSCWrapper selectedToggle) {
    	try {
    		selectedToggleOSCWrapper = selectedToggle;
    		
			Intent intent = new Intent(this, ToggleOSCSettingActivity.class);
			intent.setAction("com.ahmetkizilay.controls.osc.ToggleOSCSetter");
			intent.putExtra("msgToggleOn", selectedToggle.getMessageToggleOn());
			intent.putExtra("msgToggleOff", selectedToggle.getMessageToggleOff());
			startActivityForResult(intent, TOGGLE_OSC_INTENT_RESULT);
		} catch(Throwable t) {
			t.printStackTrace();
			Toast.makeText(this, "Error calling ToggleOSCSetter Intent", Toast.LENGTH_LONG).show();
		}
    }
    
    /***
     * In edit mode, the SeekBarOSCWrapper calls this method and passes itself as the argument.
     * An intent object is created with the properties of the wrapper object attached.
     * Upon successful return of the intent result, the new values are passed back to the wrapper object
     * in the handleSeekBarOSCSettingResult method.
     * @param selectedSeekBar
     */
    public void callSeekBarOSCSetter(SeekBarOSCWrapper selectedSeekBar) {
    	try {
    		selectedSeekBarOSCWrapper = selectedSeekBar;
    		
			Intent intent = new Intent(this, SeekBarOSCSettingActivity.class);
			intent.putExtra("msgValueChanged", selectedSeekBar.getMsgValueChanged());
			intent.putExtra("maxValue", selectedSeekBar.getMaxValue());
			intent.putExtra("minValue", selectedSeekBar.getMinValue());
			intent.setAction("com.ahmetkizilay.controls.osc.SeekBarOSCSetter");
			startActivityForResult(intent, SEEKBAR_OSC_INTENT_RESULT);
		} catch(Throwable t) {
			t.printStackTrace();
			Toast.makeText(this, "Error calling SeekBarOSCSetter Intent", Toast.LENGTH_LONG).show();
		}
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == Activity.RESULT_OK) {
			switch(requestCode) {
			case BUTTON_OSC_INTENT_RESULT:
				handleButtonOSCSettingResult(data);
				break;
			case TOGGLE_OSC_INTENT_RESULT:
				handleToggleOSCSettingResult(data);
				break;
			case SEEKBAR_OSC_INTENT_RESULT:
				handleSeekBarOSCSettingResult(data);
				break;
			}
		}
	}
        
    /**
     * The intent sent from the settings activity is passed into the caller wrapper object. 
     * @param intent
     */
    private void handleButtonOSCSettingResult(Intent intent) {
    	
		String msgButtonPressed = intent.getExtras().get("msgButtonPressed").toString();
		String msgButtonReleased = (String) intent.getExtras().get("msgButtonReleased").toString();
		boolean trigButtonReleased = Boolean.parseBoolean(intent.getExtras().get("trigButtonReleased").toString());
		
		selectedButtonOSCWrapper.setMessageButtonPressed(msgButtonPressed);
		selectedButtonOSCWrapper.setMessageButtonReleased(msgButtonReleased);
		selectedButtonOSCWrapper.setTriggerWhenButtonReleased(trigButtonReleased);
    }

    /**
     * The intent sent from the settings activity is passed into the caller wrapper object. 
     * @param intent
     */
    private void handleToggleOSCSettingResult(Intent intent) {
    	
		String msgToggleOn = intent.getExtras().get("msgToggleOn").toString();
		String msgToggleOff = (String) intent.getExtras().get("msgToggleOff").toString();
				
		selectedToggleOSCWrapper.setMessageToggleOn(msgToggleOn);
		selectedToggleOSCWrapper.setMessageToggleOff(msgToggleOff);		
    }
    
    /**
     * The intent sent from the settings activity is passed into the caller wrapper object. 
     * @param intent
     */
    private void handleSeekBarOSCSettingResult(Intent intent) {
    	
		String msgValueChanged = intent.getExtras().get("msgValueChanged").toString();
		float fltMaxValue = Float.parseFloat(intent.getExtras().get("maxValue").toString());
		float fltMinValue = Float.parseFloat(intent.getExtras().get("minValue").toString());
				
		selectedSeekBarOSCWrapper.setMsgValueChanged(msgValueChanged);
		selectedSeekBarOSCWrapper.setMaxValue(fltMaxValue);
		selectedSeekBarOSCWrapper.setMinValue(fltMinValue);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	MenuItem editMenuItem = menu.getItem(1);
    	editMenuItem.setTitle(editMode ? "run mode" : "edit mode");
    	
    	return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.about_page:
			showDialog(ABOUT_DIALOG);
			return true;
		case R.id.edit_menu:
			toggleEditMode();
			return true;
		case R.id.network_menu:
			showDialog(NETWORK_DIALOG);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	switch(id) {
    	case ABOUT_DIALOG:
    		return createAboutDialog();
    	case NETWORK_DIALOG:
    		return createNetworkDialog();
    	default:
    		return null;
    	}
    }
    
    
    /**
     * Creates a simple about dialog for self promotion.
     * @return
     */
    private Dialog createAboutDialog() {
    	LayoutInflater inflator = LayoutInflater.from(this);
    	final View aboutView = inflator.inflate(R.layout.dialog_about, null);
    	
    	AlertDialog alert = new AlertDialog.Builder(this).create();
    	alert.setView(aboutView);
    	alert.setCancelable(false);
    	alert.setButton(Dialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		return alert;
    }
    
    /**
     * creates Network Settings Dialog. Gets the layout tamplate from the xml file.
     * Stores ipAddress and port values.
     * @return
     */
    private Dialog createNetworkDialog() {
    	LayoutInflater inflator = LayoutInflater.from(this);
    	final View networkView = inflator.inflate(R.layout.dialog_network_settings, null);
    	EditText etNetworkIP = (EditText) networkView.findViewById(R.id.etNetworkIP);
    	etNetworkIP.setText(ipAddress);
    	
    	EditText etNetworkPort = (EditText) networkView.findViewById(R.id.etNetworkPort);
    	etNetworkPort.setText(Integer.toString(port));
    	
    	final AlertDialog alert = new AlertDialog.Builder(this).create();
    	alert.setView(networkView);
    	alert.setTitle("Network Settings");
    	alert.setButton(Dialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				EditText etNetworkIP = (EditText) alert.findViewById(R.id.etNetworkIP);
				ipAddress = etNetworkIP.getText().toString();
				
				EditText etNetworkPort = (EditText) alert.findViewById(R.id.etNetworkPort);
				port = Integer.parseInt(etNetworkPort.getText().toString());
				
				initializeOSC();
			}
		});
    	return alert;
    }
    
    /**
     * Toggles edit mode triggered by the menu option.
     */
    private void toggleEditMode() {
    	if(isEditMode()) {
    		this.editMode = false;
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            Toast.makeText(this, "Edit Mode Disabled", Toast.LENGTH_SHORT).show();
    	}
    	else {
    		this.editMode = true;
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
            Toast.makeText(this, "Edit Mode Enabled", Toast.LENGTH_SHORT).show();
    	}
    }
    
    /***
     * Initializes the OSCPortOut class with the given ipAddress and port.
     * Called once at the beginning in onCreate() method and at the end of the network settings dialog save action.
     */
    private void initializeOSC() {
    	try {
    		if(oscPortOut != null) {
    			oscPortOut.close();
    		}
    		
    		oscPortOut = new OSCPortOut(InetAddress.getByName(ipAddress), port);
    	}
    	catch(Exception exp) {
    		Toast.makeText(this, "Error Initializing OSC", Toast.LENGTH_SHORT).show();
    		oscPortOut = null;
    	}
    }
    
    /**
     * Sends the OSC message passed by the Wrappers. Requires a successful initializeOSC() method
     * to be able to access the host.
     * @param message
     */
    
    public void sendOSC(String message) {
    	try {
    		OSCMessage msg = new OSCMessage(message);
    		this.oscPortOut.send(msg);
    	}
    	catch(Exception exp) {
    		Toast.makeText(this, "Error Sending Message", Toast.LENGTH_SHORT).show();
    	}
    }
}