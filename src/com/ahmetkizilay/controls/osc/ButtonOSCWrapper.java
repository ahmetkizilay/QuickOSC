package com.ahmetkizilay.controls.osc;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;


/**
 * ButtonOSCWrappper
 * This is the wrapper class for Button controls.
 * Stores OSC messages, and manages the touch listener to pass OSC messages to the parent activity.
 * Messages are triggered when button pressed and button released. 
 * With the triggerWhenButtonReleased flag, release action can be discarded. 
 * @author ahmetkizilay
 *
 */
public class ButtonOSCWrapper implements OnTouchListener{

	private Button button;
	private QuickOSCActivity parentActivity;
	private String messageButtonPressed = "";
	private String messageButtonReleased = "";
	private String name;
	private boolean triggerWhenButtonReleased = true;
	
	
	/**
	 * Default constructor publicly available for other classes.
	 * @param name Only used to set the default OSC message.
	 * @param button
	 * @param parentActivity
	 * @return
	 */
	public static ButtonOSCWrapper createInstance(String name, Button button, QuickOSCActivity parentActivity) {
		return new ButtonOSCWrapper(name, button, parentActivity);
	}
	
	public static ButtonOSCWrapper createInstance(String name, String msgButtonPressed, boolean trigWhenButtonReleased, String msgButtonReleased, Button button, QuickOSCActivity parentActivity) {
		return new ButtonOSCWrapper(name, msgButtonPressed, trigWhenButtonReleased, msgButtonReleased, button, parentActivity);
	}
	private ButtonOSCWrapper(String name, Button button, QuickOSCActivity parentActivity) {
		this.button = button;
		this.parentActivity = parentActivity;
		this.name = name;
		this.messageButtonPressed = "/" + name + "/1";
		this.messageButtonReleased = "/" + name + "/0";
		this.button.setOnTouchListener(this);
	}
	
	private ButtonOSCWrapper(String name, String msgButtonPressed, boolean trigWhenButtonReleased, String msgButtonReleased, Button button, QuickOSCActivity parentActivity) {
		this.button = button;
		this.name = name;
		this.parentActivity = parentActivity;		
		this.messageButtonPressed = (msgButtonPressed == null || msgButtonPressed.equals("")) ? "/" + name + "/1" : msgButtonPressed;
		this.messageButtonReleased = (msgButtonReleased == null || msgButtonReleased.equals("")) ? "/" + name + "/1" : msgButtonPressed;
		this.triggerWhenButtonReleased = trigWhenButtonReleased;
		this.button.setOnTouchListener(this);
	}

	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if(!parentActivity.isEditMode())
			{
				parentActivity.sendOSC(messageButtonPressed);
				parentActivity.setDebugMessage(messageButtonPressed);
			}
		}
		else if(event.getAction() == MotionEvent.ACTION_UP) {
			if(parentActivity.isEditMode()) {
				parentActivity.callButtonOSCSetter(this);
			}
			else if(this.triggerWhenButtonReleased) {
				parentActivity.sendOSC(messageButtonReleased);
				parentActivity.setDebugMessage(messageButtonReleased);
			}
		}
		else {
		//	parentActivity.setDebugMessage(thisButton.getText() + " is " + event.getAction());
		}
		return false;
	}
	
	public void setMessageButtonPressed(String messageButtonPressed) {
		this.messageButtonPressed = messageButtonPressed;
	}
	
	public void setMessageButtonReleased(String messageButtonReleased) {
		this.messageButtonReleased = messageButtonReleased;
	}
	
	public void setTriggerWhenButtonReleased(boolean triggerWhenButtonReleased) {
		this.triggerWhenButtonReleased = triggerWhenButtonReleased;
	}
	
	public String getMessageButtonPressed() {
		return this.messageButtonPressed;
	}
	
	public String getMessageButtonReleased() {
		return this.messageButtonReleased;
	}
	
	public boolean getTriggerWhenButtonReleased() {
		return this.triggerWhenButtonReleased;				
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
