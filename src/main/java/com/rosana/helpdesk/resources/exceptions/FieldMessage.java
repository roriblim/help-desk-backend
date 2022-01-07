package com.rosana.helpdesk.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//nessa classe eu precisei dos getters e setters para ela funcionar... mesmo ela sendo chamada
	//pelo construtor.
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
		super();
	}
	
	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
