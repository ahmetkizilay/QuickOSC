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
	
	private String messageToggleOnAddr;
	private Object[] messageToggleOnArgs;
	private String messageToggleOnRaw;
	
	private String messageToggleOffAddr;
	private Object[] messageToggleOffArgs;
	private String messageToggleOffRaw;
	private String name;
	
	private int index;
	
	/**
	 * Default constructor publicly available for other classes.
	 * @param name Only used to set the default OSC message.
	 * @param button
	 * @param parentActivity
	 * @return
	 */
	public static ToggleOSCWrapper createInstance(int index, String name, ToggleButton button, QuickOSCActivity parentActivity) {
		return new ToggleOSCWrapper(index, name, button, parentActivity);
	}
	
	public static ToggleOSCWrapper createInstance(int index, String name, String msgToggleOn, String msgToggleOff, ToggleButton button, QuickOSCActivity parentActivity) {
		return new ToggleOSCWrapper(index, name, msgToggleOn, msgToggleOff, button, parentActivity);
	}
	
	private ToggleOSCWrapper(int index, String name, ToggleButton button, QuickOSCActivity parentActivity) {
		this.index = index;
		this.parentActivity = parentActivity;
		this.name = name;
		
		this.messageToggleOnAddr = "/" + name + "/1";
		this.messageToggleOnArgs = null;
		this.messageToggleOnRaw = this.messageToggleOnAddr;
		
		this.messageToggleOffAddr = "/" + name + "/0";
		this.messageToggleOffArgs = null;
		this.messageToggleOffRaw = this.messageToggleOffAddr;
		
		
		button.setOnCheckedChangeListener(this);
		button.setText(name);
		button.setTextOn(name);
		button.setTextOff(name);
	}
	
	private ToggleOSCWrapper(int index, String name, String msgToggleOn, String msgToggleOff, ToggleButton button, QuickOSCActivity parentActivity) {
		this.index = index;
		this.parentActivity = parentActivity;
		this.name = name;

		this.setMessageToggleOn(msgToggleOn);
		this.setMessageToggleOff(msgToggleOff);
		
		
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
			parentActivity.sendOSC(this.messageToggleOnAddr, this.messageToggleOnArgs);
			parentActivity.setDebugMessage(this.messageToggleOnRaw);
		}
		else {
			parentActivity.sendOSC(this.messageToggleOffAddr, this.messageToggleOffArgs);
			parentActivity.setDebugMessage(this.messageToggleOffRaw);
		}

	}

	public String getMessageToggleOnRaw() {
		return this.messageToggleOnRaw;
	}

	public void setMessageToggleOn(String messageToggleOn) {
		if(messageToggleOn == null || messageToggleOn.equals("")) {
			this.messageToggleOnAddr = "/" + name + "/1";
			this.messageToggleOnArgs = null;
			this.messageToggleOnRaw = this.messageToggleOnAddr;
		}
		else {
			this.messageToggleOnRaw = messageToggleOn;
			
			String[] messageToggleOnParts = messageToggleOn.split(" ");
			this.messageToggleOnAddr = messageToggleOnParts[0];
			if(messageToggleOnParts.length > 0) {
				this.messageToggleOnArgs = new Object[messageToggleOnParts.length - 1];
				for(int i = 1; i < messageToggleOnParts.length; i++) {
					this.messageToggleOnArgs[i - 1] = Utils.simpleParse(messageToggleOnParts[i]);
				}
			}
		}
	}

	public String getMessageToggleOffRaw() {
		return messageToggleOffRaw;
	}

	public void setMessageToggleOff(String messageToggleOff) {
		if(messageToggleOff == null || messageToggleOff.equals("")) {
			this.messageToggleOffAddr = "/" + name + "/0";
			this.messageToggleOffArgs = null;
			this.messageToggleOffRaw = this.messageToggleOffAddr;
		}
		else {
			this.messageToggleOffRaw = messageToggleOff;
			
			String[] messageToggleOffParts = messageToggleOff.split(" ");
			this.messageToggleOffAddr = messageToggleOffParts[0];
			if(messageToggleOffParts.length > 0) {
				this.messageToggleOffArgs = new Object[messageToggleOffParts.length - 1];
				for(int i = 1; i < messageToggleOffParts.length; i++) {
					this.messageToggleOffArgs[i - 1] = Utils.simpleParse(messageToggleOffParts[i]);
				}
			}
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex() {
		return this.index;
	}
	
}
