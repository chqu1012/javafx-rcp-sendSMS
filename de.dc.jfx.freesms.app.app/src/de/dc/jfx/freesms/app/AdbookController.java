package de.dc.jfx.freesms.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.internal.events.EventBroker;

import de.dc.jfx.freesms.app.dialog.SMSDialog;
import de.dc.jfx.freesms.app.model.Contact;

public class AdbookController implements Initializable {
	
	@FXML TableView<Contact> adbookTableView;
	@FXML TextField searchText;
	
	// The table's data
    ObservableList<Contact> data;

	private EventBroker broker;
    
    public AdbookController(EventBroker broker) {
		this.broker = broker;
	}

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        data.add(new Contact("Müller", "hans", "28.01.2012", "hans.mueller@yahoo.de"));
        data.add(new Contact("Müller", "hans", "28.01.2012", "hans.mueller@yahoo.de"));
        data.add(new Contact("Müller", "hans", "28.01.2012", "hans.mueller@yahoo.de"));
        data.add(new Contact("Müller", "hans", "28.01.2012", "hans.mueller@yahoo.de"));
        data.add(new Contact("Müller", "hans", "28.01.2012", "hans.mueller@yahoo.de"));
        adbookTableView.setItems(data);
        adbookTableView.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent event) {
        	    if (event.getClickCount()>1) {
        	    	SMSDialog dialog = new SMSDialog();
        	    	dialog.open();
        	    	Contact contact = adbookTableView.getSelectionModel().getSelectedItem();
					dialog.setReceiver(contact.getMobileNumber(), contact.getFirstname()+", "+contact.getName());
        	    }
        	}
        });
    }    
    
    public void addContact(Contact contact){
    	data.add(contact);
    }
    
    public void selectItemEvent(){
    }
}
