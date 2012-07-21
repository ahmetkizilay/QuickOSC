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
	
	
	/**
	 * Default constructor publicly available for other classes.
	 * @param name Only used to set the default OSC message.
	 * @param button
	 * @param parentActivity
	 * @return
	 */
	public static SeekBarOSCWrapper createInstance(String name, SeekBar seekBar, QuickOSCActivity parentActivity) {
		return new SeekBarOSCWrapper(name, seekBar, parentActivity);
	}
	
	private SeekBarOSCWrapper(String name, SeekBar seekBar, QuickOSCActivity parentActivity) {
		this.seekBar = seekBar;
		this.parentActivity = parentActivity;
		this.msgValueChanged = "/" + name + "/$";
		this.seekBar.setOnSeekBarChangeListener(this);
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(!parentActivity.isEditMode()) {
			float val = scaleOutput(progress);
			String message = msgValueChanged.replace("$", Float.toString(val));
			parentActivity.sendOSC(message);
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
	
	
	/**
	 * Scales progress value according to minValue and maxValue
	 * @param progress
	 * @return
	 */
	private float scaleOutput(int progress) {
		return minValue + ((maxValue - minValue) * progress / 100.0f);
	}
	
	
}
