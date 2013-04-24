package com.ahmetkizilay.controls.osc;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.ahmetkizilay.controls.osc.R;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
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
    private final static int WIFI_ALERT_DIALOG = 3;
    
    private final static String NETWORK_SETTINGS_FILE = "qosc_network.cfg";
    private final static String OSC_SETTINGS_FILE = "qosc_osc.cfg";
            
    private List<ButtonOSCWrapper> buttonOSCWrapperList = new ArrayList<ButtonOSCWrapper>();
    private List<ToggleOSCWrapper> toggleOSCWrapperList = new ArrayList<ToggleOSCWrapper>();
    private List<SeekBarOSCWrapper> seekBarOSCWrapperList = new ArrayList<SeekBarOSCWrapper>();
    private Hashtable<String, String> oscSettingsHashtable = new Hashtable<String, String>();
    
    private boolean editMode = false;

    private String ipAddress = "127.0.0.1";
    private int port = 8000;
    private OSCPortOut oscPortOut = null;    
    
	TextView debugTextView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
		restoreNetworkSettingsFromFile();
		restoreOSCSettingsFromFile();
        initializeOSC();
        
        debugTextView = (TextView) findViewById(R.id.textView1);
        
        Button button1 = (Button) findViewById(R.id.button1);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(0, "btn1", 
						        		oscSettingsHashtable.get("btn1-butpres"), 
						        		Boolean.parseBoolean(oscSettingsHashtable.get("btn1-trgbutrel")),
						        		oscSettingsHashtable.get("btn1-butrel"),
						        		button1, this));
                
        Button button2 = (Button) findViewById(R.id.button2);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(1, "btn2", 
        		oscSettingsHashtable.get("btn2-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn2-trgbutrel")),
        		oscSettingsHashtable.get("btn2-butrel"),
        		button2, this));
        
        Button button3 = (Button) findViewById(R.id.button3);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(2, "btn3", 
        		oscSettingsHashtable.get("btn3-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn3-trgbutrel")),
        		oscSettingsHashtable.get("btn3-butrel"),
        		button3, this));
        
        Button button4 = (Button) findViewById(R.id.button4);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(3, "btn4", 
        		oscSettingsHashtable.get("btn4-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn4-trgbutrel")),
        		oscSettingsHashtable.get("btn4-butrel"),
        		button4, this));
        
        Button button5 = (Button) findViewById(R.id.button5);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(4, "btn5", 
        		oscSettingsHashtable.get("btn5-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn5-trgbutrel")),
        		oscSettingsHashtable.get("btn5-butrel"),
        		button5, this));
        
        Button button6 = (Button) findViewById(R.id.button6);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(5, "btn6", 
        		oscSettingsHashtable.get("btn6-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn6-trgbutrel")),
        		oscSettingsHashtable.get("btn6-butrel"),
        		button6, this));
        
        Button button7 = (Button) findViewById(R.id.button7);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(6, "btn7", 
        		oscSettingsHashtable.get("btn7-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn7-trgbutrel")),
        		oscSettingsHashtable.get("btn7-butrel"),
        		button7, this));
        
        Button button8 = (Button) findViewById(R.id.button8);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(7, "btn8", 
        		oscSettingsHashtable.get("btn8-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn8-trgbutrel")),
        		oscSettingsHashtable.get("btn8-butrel"),
        		button8, this));
        
        Button button9 = (Button) findViewById(R.id.button9);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(8, "btn9", 
        		oscSettingsHashtable.get("btn9-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn9-trgbutrel")),
        		oscSettingsHashtable.get("btn9-butrel"),
        		button9, this));
        
        Button button10 = (Button) findViewById(R.id.button10);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(9, "btn10", 
        		oscSettingsHashtable.get("btn10-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn10-trgbutrel")),
        		oscSettingsHashtable.get("btn10-butrel"),
        		button10, this));
        
        Button button11 = (Button) findViewById(R.id.button11);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(10, "btn11", 
        		oscSettingsHashtable.get("btn11-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn11-trgbutrel")),
        		oscSettingsHashtable.get("btn11-butrel"),
        		button11, this));
        
        Button button12 = (Button) findViewById(R.id.button12);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(11, "btn12", 
        		oscSettingsHashtable.get("btn12-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn12-trgbutrel")),
        		oscSettingsHashtable.get("btn12-butrel"),
        		button12, this));
        
        Button button13 = (Button) findViewById(R.id.button13);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(12, "btn13", 
        		oscSettingsHashtable.get("btn13-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn13-trgbutrel")),
        		oscSettingsHashtable.get("btn13-butrel"),
        		button13, this));
        
        Button button14 = (Button) findViewById(R.id.button14);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(13, "btn14", 
        		oscSettingsHashtable.get("btn14-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn14-trgbutrel")),
        		oscSettingsHashtable.get("btn14-butrel"),
        		button14, this));
        
        Button button15 = (Button) findViewById(R.id.button15);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(14, "btn15", 
        		oscSettingsHashtable.get("btn15-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn15-trgbutrel")),
        		oscSettingsHashtable.get("btn15-butrel"),
        		button15, this));
        
        Button button16 = (Button) findViewById(R.id.button16);
        buttonOSCWrapperList.add(ButtonOSCWrapper.createInstance(15, "btn16", 
        		oscSettingsHashtable.get("btn16-butpres"), 
        		Boolean.parseBoolean(oscSettingsHashtable.get("btn16-trgbutrel")),
        		oscSettingsHashtable.get("btn16-butrel"),
        		button16, this));
        
        
        
        ToggleButton toggle1 = (ToggleButton) findViewById(R.id.toggleButton1);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(0, "tog1", 
        		oscSettingsHashtable.get("tog1-togon"),
        		oscSettingsHashtable.get("tog1-togoff"),
        		toggle1, this));
        
        ToggleButton toggle2 = (ToggleButton) findViewById(R.id.toggleButton2);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(1, "tog2", 
        		oscSettingsHashtable.get("tog2-togon"),
        		oscSettingsHashtable.get("tog2-togoff"),
        		toggle2, this));
        
        ToggleButton toggle3 = (ToggleButton) findViewById(R.id.toggleButton3);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(2, "tog3", 
        		oscSettingsHashtable.get("tog3-togon"),
        		oscSettingsHashtable.get("tog3-togoff"),
        		toggle3, this));
        
        ToggleButton toggle4 = (ToggleButton) findViewById(R.id.toggleButton4);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(3, "tog4", 
        		oscSettingsHashtable.get("tog4-togon"),
        		oscSettingsHashtable.get("tog4-togoff"),
        		toggle4, this));
        
        ToggleButton toggle5 = (ToggleButton) findViewById(R.id.toggleButton5);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(4, "tog5", 
        		oscSettingsHashtable.get("tog5-togon"),
        		oscSettingsHashtable.get("tog5-togoff"),
        		toggle5, this));
        
        ToggleButton toggle6 = (ToggleButton) findViewById(R.id.toggleButton6);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(5, "tog6", 
        		oscSettingsHashtable.get("tog6-togon"),
        		oscSettingsHashtable.get("tog6-togoff"),
        		toggle6, this));
        
        ToggleButton toggle7 = (ToggleButton) findViewById(R.id.toggleButton7);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(6, "tog7", 
        		oscSettingsHashtable.get("tog7-togon"),
        		oscSettingsHashtable.get("tog7-togoff"),
        		toggle7, this));
        
        ToggleButton toggle8 = (ToggleButton) findViewById(R.id.toggleButton8);
        toggleOSCWrapperList.add(ToggleOSCWrapper.createInstance(7, "tog8", 
        		oscSettingsHashtable.get("tog8-togon"),
        		oscSettingsHashtable.get("tog8-togoff"),
        		toggle8, this));
        
        SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBarOSCWrapperList.add(SeekBarOSCWrapper.createInstance(0, "seekBar1",
        		oscSettingsHashtable.get("seekBar1-valcng"),
        		safeFloatParse(oscSettingsHashtable.get("seekBar1-minval"), 0),
        		safeFloatParse(oscSettingsHashtable.get("seekBar1-maxval"), 100),
        		seekBar1, this));
        
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBarOSCWrapperList.add(SeekBarOSCWrapper.createInstance(1, "seekBar2",
        		oscSettingsHashtable.get("seekBar2-valcng"),
        		safeFloatParse(oscSettingsHashtable.get("seekBar2-minval"), 0),
        		safeFloatParse(oscSettingsHashtable.get("seekBar2-maxval"), 100),
        		seekBar2, this));
        
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBarOSCWrapperList.add(SeekBarOSCWrapper.createInstance(2, "seekBar3",
        		oscSettingsHashtable.get("seekBar3-valcng"),
        		safeFloatParse(oscSettingsHashtable.get("seekBar3-minval"), 0),
        		safeFloatParse(oscSettingsHashtable.get("seekBar3-maxval"), 100),
        		seekBar3, this));
        
        SeekBar seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBarOSCWrapperList.add(SeekBarOSCWrapper.createInstance(3, "seekBar4",
        		oscSettingsHashtable.get("seekBar4-valcng"),
        		safeFloatParse(oscSettingsHashtable.get("seekBar4-minval"), 0),
        		safeFloatParse(oscSettingsHashtable.get("seekBar4-maxval"), 100),
        		seekBar4, this));
        
        checkWifiState();

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
    		//this.selectedButtonOSCWrapper = selectedButton;
    		
			Intent intent = new Intent(this, ButtonOSCSettingActivity.class);
			intent.setAction("com.ahmetkizilay.controls.osc.ButtonOSCSetter");
			intent.putExtra("msgButtonPressed", selectedButton.getMessageButtonPressedRaw());
			intent.putExtra("msgButtonReleased", selectedButton.getMessageButtonReleasedRaw());
			intent.putExtra("trigButtonReleased", selectedButton.getTriggerWhenButtonReleased());
			intent.putExtra("index", selectedButton.getIndex());
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
    		//selectedToggleOSCWrapper = selectedToggle;
    		
			Intent intent = new Intent(this, ToggleOSCSettingActivity.class);
			intent.setAction("com.ahmetkizilay.controls.osc.ToggleOSCSetter");
			intent.putExtra("msgToggleOn", selectedToggle.getMessageToggleOnRaw());
			intent.putExtra("msgToggleOff", selectedToggle.getMessageToggleOffRaw());
			intent.putExtra("index", selectedToggle.getIndex());
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
    		//selectedSeekBarOSCWrapper = selectedSeekBar;
    		
			Intent intent = new Intent(this, SeekBarOSCSettingActivity.class);
			intent.putExtra("msgValueChanged", selectedSeekBar.getMsgValueChanged());
			intent.putExtra("maxValue", selectedSeekBar.getMaxValue());
			intent.putExtra("minValue", selectedSeekBar.getMinValue());
			intent.putExtra("index", selectedSeekBar.getIndex());
			
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
		int indexSelectedButton = Integer.parseInt(intent.getExtras().get("index").toString());
		
		ButtonOSCWrapper selectedButton = this.buttonOSCWrapperList.get(indexSelectedButton);
		selectedButton.setMessageButtonPressed(msgButtonPressed);
		selectedButton.setMessageButtonReleased(msgButtonReleased);
		selectedButton.setTriggerWhenButtonReleased(trigButtonReleased);
		
		saveOSCSettingsIntoFile();
    }

    /**
     * The intent sent from the settings activity is passed into the caller wrapper object. 
     * @param intent
     */
    private void handleToggleOSCSettingResult(Intent intent) {
    	
		String msgToggleOn = intent.getExtras().get("msgToggleOn").toString();
		String msgToggleOff = (String) intent.getExtras().get("msgToggleOff").toString();
		int selectedIndex = Integer.parseInt(intent.getExtras().get("index").toString());
		
		ToggleOSCWrapper selectedToggle = toggleOSCWrapperList.get(selectedIndex);
		selectedToggle.setMessageToggleOn(msgToggleOn);
		selectedToggle.setMessageToggleOff(msgToggleOff);
		
		saveOSCSettingsIntoFile();
    }
    
    /**
     * The intent sent from the settings activity is passed into the caller wrapper object. 
     * @param intent
     */
    private void handleSeekBarOSCSettingResult(Intent intent) {
    	
		String msgValueChanged = intent.getExtras().get("msgValueChanged").toString();
		float fltMaxValue = Float.parseFloat(intent.getExtras().get("maxValue").toString());
		float fltMinValue = Float.parseFloat(intent.getExtras().get("minValue").toString());
		int selectedIndex = Integer.parseInt(intent.getExtras().get("index").toString());
		
		SeekBarOSCWrapper selectedSeekBar = seekBarOSCWrapperList.get(selectedIndex);
		selectedSeekBar.setMsgValueChanged(msgValueChanged);
		selectedSeekBar.setMaxValue(fltMaxValue);
		selectedSeekBar.setMinValue(fltMinValue);
		
		saveOSCSettingsIntoFile();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	MenuItem editMenuItem = menu.getItem(1);
    	editMenuItem.setTitle(editMode ? "play mode" : "edit mode");
    	
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
    	case WIFI_ALERT_DIALOG:
    		return createWifiAlertDialog();
    	default:
    		return null;
    	}
    }
    
    private Dialog createWifiAlertDialog() {
    	AlertDialog alert = new AlertDialog.Builder(this).create();
    	alert.setTitle("Wifi Not Detected");
    	alert.setCancelable(false);
    	alert.setMessage("Enable Wifi For OSC");
    	alert.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    			removeDialog(WIFI_ALERT_DIALOG);
    		}
    	});
    	alert.setButton(Dialog.BUTTON_POSITIVE, "Wifi Settings", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    			removeDialog(WIFI_ALERT_DIALOG);
    			Intent wifiIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
				startActivity(wifiIntent);
    		}
    	});
    	return alert;
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
    	alert.setButton(Dialog.BUTTON_NEUTRAL, "Close", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}			
		});
    	alert.setButton(Dialog.BUTTON_POSITIVE, "Follow Me", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mobile.twitter.com/ahmetkizilay"));				
				startActivity(twitterIntent);				
			}
		});
    	alert.setTitle("QuickOSC");
    	alert.setIcon(R.drawable.qosc);
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
				
				saveNetworkSettinsIntoFile();
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
	    	new AsyncSendOSCTask(this, this.oscPortOut).execute(new OSCMessage(message));	    	
    	}
    	catch(Exception exp) {
    		Toast.makeText(this, "Error Sending Message", Toast.LENGTH_SHORT).show();
    	}
    }
    
    /**
     * Sends the OSC message passed by the Wrappers. Requires a successful initializeOSC() method
     * to be able to access the host.
     * @param message
     */
    
    public void sendOSC(String address, Object[] arguments) {    	
    	try {    		
	    	new AsyncSendOSCTask(this, this.oscPortOut).execute(new OSCMessage(address, arguments));	    	
    	}
    	catch(Exception exp) {
    		Toast.makeText(this, "Error Sending Message", Toast.LENGTH_SHORT).show();
    	}
    }
    
    
    /**
     * Saves network settings to file to be on next startup
     */
    private void saveNetworkSettinsIntoFile() {
    	try {
    		try {
        		FileOutputStream fos = openFileOutput(NETWORK_SETTINGS_FILE, Context.MODE_PRIVATE);
        		
        		String data = ipAddress + "#" + port;
        		fos.write(data.getBytes());
        		fos.close();
        	}
        	catch(Exception exp) {
        		Toast.makeText(this, "Could Not Update SCAuth File", Toast.LENGTH_SHORT).show();
        		exp.printStackTrace();
        	}
    	}
    	catch(Exception exp) {
    		Toast.makeText(this, "Error Saving Network Settings", Toast.LENGTH_SHORT).show();
    	}
    }
    
    /**
     * Restores network settings which were saved in previous sessions
     */
    private void restoreNetworkSettingsFromFile() {
    	try {	
    		FileInputStream fis = openFileInput(NETWORK_SETTINGS_FILE);
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		byte[] buffer = new byte[512];
    		int bytes_read;
    		while((bytes_read = fis.read(buffer)) != -1) {
    			baos.write(buffer, 0, bytes_read);
    		}
    		
    		String data = new String(baos.toByteArray());
    		String[] pieces = data.split("#");
    		
    		ipAddress = pieces[0];
    		port = Integer.parseInt(pieces[1]);
    		
    	}
    	catch(FileNotFoundException fnfe) {}
    	catch(Exception exp) {
    		Toast.makeText(this, "Could Not Read SCAuth File", Toast.LENGTH_SHORT).show();
    		ipAddress = "127.0.0.1"; port = 8000;
    	}
    }
    
    private void restoreOSCSettingsFromFile() {
    	try {
    		oscSettingsHashtable.clear();
    		
    		FileInputStream fis = openFileInput(OSC_SETTINGS_FILE);
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		byte[] buffer = new byte[512];
    		int bytes_read;
    		while((bytes_read = fis.read(buffer)) != -1) {
    			baos.write(buffer, 0, bytes_read);
    		}
    		
    		String data = new String(baos.toByteArray());
    		String[] pieces = data.split("#x#x#");
    		
    		for(int i = 0; i < pieces.length; i+=2) {
    			oscSettingsHashtable.put(pieces[i], pieces[i+1]);
    		}
    	}
    	catch(FileNotFoundException fnfe) {}
    	catch(Exception exp) {
    		Toast.makeText(this, "Could Not Read OSC Settings File", Toast.LENGTH_SHORT).show();
    		oscSettingsHashtable.clear();
       	}
    }
    
    /**
     * Saves network settings to file to be on next startup
     */
    private void saveOSCSettingsIntoFile() {
    	try {
    		try {
        		FileOutputStream fos = openFileOutput(OSC_SETTINGS_FILE, Context.MODE_PRIVATE);
        		
        		StringBuffer dataBuffer = new StringBuffer();
        		for(int i = 0; i < buttonOSCWrapperList.size(); i++) {
        			ButtonOSCWrapper thisButtonWrapper = buttonOSCWrapperList.get(i);
        			dataBuffer.append(thisButtonWrapper.getName() + "-butpres" + "#x#x#" + thisButtonWrapper.getMessageButtonPressedRaw() + "#x#x#");
        			dataBuffer.append(thisButtonWrapper.getName() + "-trgbutrel" + "#x#x#" + thisButtonWrapper.getTriggerWhenButtonReleased() + "#x#x#");
        			dataBuffer.append(thisButtonWrapper.getName() + "-butrel" + "#x#x#" + thisButtonWrapper.getMessageButtonReleasedRaw() + "#x#x#");
        		}
        		
        		for(int i = 0; i < toggleOSCWrapperList.size(); i++) {
        			ToggleOSCWrapper thisToggleOSCWrapper = toggleOSCWrapperList.get(i);
        			dataBuffer.append(thisToggleOSCWrapper.getName() + "-togon" + "#x#x#" + thisToggleOSCWrapper.getMessageToggleOnRaw() + "#x#x#");
        			dataBuffer.append(thisToggleOSCWrapper.getName() + "-togoff" + "#x#x#" + thisToggleOSCWrapper.getMessageToggleOffRaw() + "#x#x#");
        		}
        		
        		for(int i = 0; i < seekBarOSCWrapperList.size(); i++) {
        			SeekBarOSCWrapper thisSeekBarOSCWrapper = seekBarOSCWrapperList.get(i);
        			dataBuffer.append(thisSeekBarOSCWrapper.getName() + "-valcng" + "#x#x#" + thisSeekBarOSCWrapper.getMsgValueChanged() + "#x#x#");
        			dataBuffer.append(thisSeekBarOSCWrapper.getName() + "-minval" + "#x#x#" + thisSeekBarOSCWrapper.getMinValue() + "#x#x#");
        			dataBuffer.append(thisSeekBarOSCWrapper.getName() + "-maxval" + "#x#x#" + thisSeekBarOSCWrapper.getMaxValue() + "#x#x#");
        		}
        		
        		String data = dataBuffer.toString();
        		data = data.substring(0, data.length() - 5);
        		
        		fos.write(data.toString().getBytes());
        		fos.close();
        	}
        	catch(Exception exp) {
        		Toast.makeText(this, "Could Not Update SCAuth File", Toast.LENGTH_SHORT).show();
        		exp.printStackTrace();
        	}
    	}
    	catch(Exception exp) {
    		Toast.makeText(this, "Error Saving Network Settings", Toast.LENGTH_SHORT).show();
    	}
    }
    
    private float safeFloatParse(String val, float defVal) {
    	try {
    		return Float.parseFloat(val);
    	}
    	catch(Exception nfe){
    		return defVal;
    	}
    }
    
    @Override
    public void onBackPressed() {
    	if(this.editMode) {
    		this.editMode = false;
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            Toast.makeText(this, "Edit Mode Disabled", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	super.onBackPressed();
    }
    
    private void checkWifiState() {
    	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    	if(!wifiManager.isWifiEnabled()) {
    		showDialog(WIFI_ALERT_DIALOG);
    	}
    }
}