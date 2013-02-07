package de.dc.jfx.freesms.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.osgi.framework.Bundle;

import de.dc.jfx.freesms.app.model.Contact;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ContactFormularController implements Initializable{

	private Contact contact;
	
	@FXML TextField nameText, firstnameText, mobilenumberText, emailText;
	
	@FXML ImageView contactImage;

	private EventBroker broker;
	
	public ContactFormularController(EventBroker broker) {
		this.broker=broker;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		contact=new Contact();
		StringProperty nameProperty=nameText.textProperty();
		StringProperty firstnameProperty=firstnameText.textProperty();
		StringProperty emailProperty=emailText.textProperty();
		StringProperty mobileNumberProperty=mobilenumberText.textProperty();
		contact=new Contact(nameProperty, firstnameProperty, mobileNumberProperty, emailProperty);
		Image image = null;
			Bundle bundle = Platform.getBundle("de.dc.jfx.freesms.app.app");
			try {
				String iconPath = FileLocator.toFileURL(bundle.getEntry("icons/no_image.jpg")).getPath();
				System.out.println(iconPath);
				image = new Image(new FileInputStream(new File(iconPath)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		contactImage.setImage(image);
	}
	
	public void createContactEvent() {
		broker.post(IEventTopics.CONTACTFORM_CREATE_CONTACT, contact);
	}

}
