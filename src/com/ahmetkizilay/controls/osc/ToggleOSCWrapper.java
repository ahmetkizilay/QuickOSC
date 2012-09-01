package com.ahmetkizilay.controls.osc;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

/**
 * ToggleOSCWrappper
 * This is the wrapper class for ToggleButton controls.
 * Stores OSC messages, and manages the touch listener to pass OSC messages to the parent activity.
 * @author ahmetkizilay
 *
 */
public class ToggleOSCWrapper implements OnCheckedChangeListener{
	
	private QuickOSCActivity parentActivity;
	private String messageToggleOn;
	private String messageToggleOff;
	private String name;
	
	/**
	 * Default constructor publicly available for other classes.
	 * @param name Only used to set the default OSC message.
	 * @param button
	 * @param parentActivity
	 * @return
	 */
	public static ToggleOSCWrapper createInstance(String name, ToggleButton button, QuickOSCActivity parentActivity) {
		return new ToggleOSCWrapper(name, button, parentActivity);
	}
	
	public static ToggleOSCWrapper createInstance(String name, String msgToggleOn, String msgToggleOff, ToggleButton button, QuickOSCActivity parentActivity) {
		return new ToggleOSCWrapper(name, msgToggleOn, msgToggleOff, button, parentActivity);
	}
	
	private ToggleOSCWrapper(String name, ToggleButton button, QuickOSCActivity parentActivity) {
		this.parentActivity = parentActivity;
		this.name = name;
		this.messageToggleOn = "/" + name + "/1";
		this.messageToggleOff = "/" + name + "/0";
		
		
		button.setOnCheckedChangeListener(this);
		button.setText(name);
		button.setTextOn(name);
		button.setTextOff(name);
	}
	
	private ToggleOSCWrapper(String name, String msgToggleOn, String msgToggleOff, ToggleButton button, QuickOSCActivity parentActivity) {
		this.parentActivity = parentActivity;
		this.name = name;
		this.messageToggleOn = (msgToggleOn == null || msgToggleOn.equals("")) ? "/" + name + "/1" : msgToggleOn;
		this.messageToggleOff = (msgToggleOff == null || msgToggleOff.equals("")) ? "/" + name + "/0" : msgToggleOff;
		
		
		button.setOnCheckedChangeListener(this);
		button.setText(name);
		button.setTextOn(name);
		button.setTextOff(name);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(parentActivity.isEditMode()) {
			parentActivity.callToggleOSCSetter(this);
			return;
		}	
		
		if(isChecked) {
			parentActivity.sendOSC(messageToggleOn);
			parentActivity.setDebugMessage(messageToggleOn);
		}
		else {
			parentActivity.sendOSC(messageToggleOff);
			parentActivity.setDebugMessage(messageToggleOff);
		}

	}

	public String getMessageToggleOn() {
		return messageToggleOn;
	}

	public void setMessageToggleOn(String messageToggleOn) {
		this.messageToggleOn = messageToggleOn;
	}

	public String getMessageToggleOff() {
		return messageToggleOff;
	}

	public void setMessageToggleOff(String messageToggleOff) {
		this.messageToggleOff = messageToggleOff;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
