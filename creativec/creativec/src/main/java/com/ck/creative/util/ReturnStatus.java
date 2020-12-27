/**
 * 
 */
package com.ck.creative.util;

/**
 * @author moses
 *
 */
public class ReturnStatus {
	private String returnValue;
	private String message;
    private Object obj;

	public ReturnStatus(String returnValue,String message){
		this.returnValue = returnValue;
		this.message = message;
	}
	/**
	 * @return the returnValue
	 */
	public String getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue the returnValue to set
	 */
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
