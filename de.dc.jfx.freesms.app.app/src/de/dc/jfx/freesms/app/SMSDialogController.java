package de.dc.jfx.freesms.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import de.dc.jfx.freesms.app.model.SMS;

public class SMSDialogController implements Initializable{

	private SMS sms;
	
	@FXML TextField receiverText, senderText;
	@FXML TextArea messageText;
	@FXML Label nameLabel, textLengthLabel;
	
	private StringProperty sender;
	private StringProperty receiver;
	private StringProperty message;
	private StringProperty name;
	private StringProperty textLength =new SimpleStringProperty();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sender = senderText.textProperty();
		receiver = receiverText.textProperty();
		message = messageText.textProperty();
		name = nameLabel.textProperty();
		textLengthLabel.textProperty().bindBidirectional(textLength);
		sms=new SMS(sender, receiver, message, name);
	}

	public void setReceiver(String receiver, String receiverName){
		sms.setReceiver(receiver);
		sms.setReceiverName(receiverName);
	}
	
	public void countLettersEvent() {
		textLength.setValue(messageText.getText().length()+"/130");
	}
}
