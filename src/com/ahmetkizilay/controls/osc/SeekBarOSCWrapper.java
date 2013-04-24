package com.ahmetkizilay.controls.osc;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * SeekBarOSCWrappper
 * This is the wrapper class for SeekBar controls.
 * Stores OSC messages, and manages the touch listener to pass OSC messages to the parent activity.
 * Messages are triggered when the Progress Ticker changes. 
 * The progress amount can be scaled according to the minValue and maxValue parameters.
 * The $ sign is used as a wildcard element to replace the scaled progress value in the final OSC message.
 * @author ahmetkizilay
 *
 */
public class SeekBarOSCWrapper implements OnSeekBarChangeListener {
	private SeekBar seekBar;
	private QuickOSCActivity parentActivity;
	private String msgValueChanged;
	private float minValue = 0;
	private float maxValue = 100;
	private String name;
	private int index;
	
	/**
	 * Default constructor publicly available for other classes.
	 * @param name Only used to set the default OSC message.
	 * @param button
	 * @param parentActivity
	 * @return
	 */
	public static SeekBarOSCWrapper createInstance(int index, String name, SeekBar seekBar, QuickOSCActivity parentActivity) {
		return new SeekBarOSCWrapper(index, name, seekBar, parentActivity);
	}
	
	public static SeekBarOSCWrapper createInstance(int index, String name, String msgValueChanged, float minVal, float maxVal, SeekBar seekBar, QuickOSCActivity parentActivity) {		
		return new SeekBarOSCWrapper(index, name, msgValueChanged, minVal, maxVal, seekBar, parentActivity);
	}
	
	private SeekBarOSCWrapper(int index, String name, SeekBar seekBar, QuickOSCActivity parentActivity) {
		this.index = index;
		this.seekBar = seekBar;
		this.parentActivity = parentActivity;
		this.name = name;
		this.msgValueChanged = "/" + name + "/$";
		this.seekBar.setOnSeekBarChangeListener(this);
	}
	
	private SeekBarOSCWrapper(int index, String name, String msgValueChanged, float minVal, float maxVal, SeekBar seekBar, QuickOSCActivity parentActivity) {
		this.index = index;
		this.seekBar = seekBar;
		this.parentActivity = parentActivity;
		this.name = name;
		this.msgValueChanged = (msgValueChanged == null || msgValueChanged.equals("")) ? "/" + name + "/$" : msgValueChanged;
		this.minValue = minVal;
		this.maxValue = maxVal;
		this.seekBar.setOnSeekBarChangeListener(this);
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(!parentActivity.isEditMode()) {
			float val = scaleOutput(progress);
			String message = msgValueChanged.replace("$", Float.toString(val));

			String[] messageParts = message.split(" ");
			Object[] arguments = null;
			if(messageParts.length > 0) {
				arguments = new Object[messageParts.length - 1];
				for(int i = 1; i < messageParts.length; i++) {
					arguments[i - 1] = Utils.simpleParse(messageParts[i]);
				}
			}
			
			parentActivity.sendOSC(messageParts[0], arguments);
			parentActivity.setDebugMessage(message);
		}		
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		if(parentActivity.isEditMode()) {
			parentActivity.callSeekBarOSCSetter(this);
		}
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	public String getMsgValueChanged() {
		return msgValueChanged;
	}

	public void setMsgValueChanged(String msgValueChanged) {
		this.msgValueChanged = msgValueChanged;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	/**
	 * Scales progress value according to minValue and maxValue
	 * @param progress
	 * @return
	 */
	private float scaleOutput(int progress) {
		return minValue + ((maxValue - minValue) * progress / 100.0f);
	}
	
	public int getIndex() {
		return this.index;
	}
	
}
