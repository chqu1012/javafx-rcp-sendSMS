package de.dc.jfx.freesms.app.model;

import javafx.beans.property.*;

public class SMS{
	
	private StringProperty sender = new SimpleStringProperty();
	private StringProperty receiver = new SimpleStringProperty();
	private StringProperty message = new SimpleStringProperty();
	private StringProperty receiverName = new SimpleStringProperty();
	
	public SMS(){
	}
	
	/**
	* Constructor for Databinding
	*/
	public SMS(StringProperty senderProperty, StringProperty receiverProperty, StringProperty messageProperty, StringProperty name) {
	    sender.bindBidirectional(senderProperty);
	    receiver.bindBidirectional(receiverProperty);
	    message.bindBidirectional(messageProperty);
	    receiverName.bindBidirectional(name);
	}

	public SMS(String sender, String receiver, String message) {
	    setSender(sender);
	    setReceiver(receiver);
	    setMessage(message);
	}
	
 	public String getSender(){
 	    return sender.get();
 	}
 	
 	public String getReceiver(){
 	    return receiver.get();
 	}
 	
 	public String getMessage(){
 	    return message.get();
 	}
 	
 	public void setSender(String sender){
 	    this.sender.set(sender);
 	}
 	
 	public void setReceiver(String receiver){
 	    this.receiver.set(receiver);
 	}
 	
 	public void setMessage(String message){
 	    this.message.set(message);
 	}
 	
	public String getReceiverName() {
		return receiverName.get();
	}

	public void setReceiverName(String name) {
		this.receiverName.set(name);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[sender: "+sender.get()+"] ");
		sb.append("[receiver: "+receiver.get()+"] ");
		sb.append("[message: "+message.get()+"] ");
		return sb.toString();
	}
}
