package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class AllocatedObject implements Serializable{
	Object object;
	String message;
	
	public AllocatedObject(Object object, String message) {
		super();
		this.object = object;
		this.message = message;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
