package com.ahmetkizilay.controls.osc;

public class Utils {
	/***
	 * Utility method to parse string for specific OSC types.
	 * @param value
	 * @return the value in int, float, string
	 */
	public static Object simpleParse(String value) {
		try {
			return Integer.parseInt(value);
		}
		catch(NumberFormatException nfe) {}
		
		try {
			return Float.parseFloat(value);
		}
		catch(NumberFormatException nfe) {}
		
		return value;
	}
}
