package de.dc.jfx.freesms.app.model;

import javafx.beans.property.*;

public class Contact{
	
	private StringProperty name = new SimpleStringProperty();
	private StringProperty firstname = new SimpleStringProperty();
	private StringProperty mobileNumber = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	
	public Contact(){
	}
	
	/**
	* Constructor for Databinding
	*/
	public Contact(StringProperty nameProperty, StringProperty firstnameProperty, StringProperty mobileNumberProperty, StringProperty emailProperty) {
	    name.bind(nameProperty);
	    firstname.bind(firstnameProperty);
	    mobileNumber.bind(mobileNumberProperty);
	    email.bind(emailProperty);
	}

	public Contact(String name, String firstname, String mobileNumber, String email) {
	    setName(name);
	    setFirstname(firstname);
	    setMobileNumber(mobileNumber);
	    setEmail(email);
	}
	
 	public String getName(){
 	    return name.get();
 	}
 	
 	public String getFirstname(){
 	    return firstname.get();
 	}
 	
 	public String getMobileNumber(){
 	    return mobileNumber.get();
 	}
 	
 	public String getEmail(){
 	    return email.get();
 	}
 	
 	public void setName(String name){
 	    this.name.set(name);
 	}
 	
 	public void setFirstname(String firstname){
 	    this.firstname.set(firstname);
 	}
 	
 	public void setMobileNumber(String mobileNumber){
 	    this.mobileNumber.set(mobileNumber);
 	}
 	
 	public void setEmail(String email){
 	    this.email.set(email);
 	}
 	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[name: "+name.get()+"] ");
		sb.append("[firstname: "+firstname.get()+"] ");
		sb.append("[mobileNumber: "+mobileNumber.get()+"] ");
		sb.append("[email: "+email.get()+"] ");
		return sb.toString();
	}
}
