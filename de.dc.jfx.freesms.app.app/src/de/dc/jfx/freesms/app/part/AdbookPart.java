package de.dc.jfx.freesms.app.part;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.internal.events.EventBroker;

import de.dc.jfx.freesms.app.AdbookController;
import de.dc.jfx.freesms.app.IEventTopics;
import de.dc.jfx.freesms.app.model.Contact;

public class AdbookPart {

	private AdbookController controller;

	@Inject
	public AdbookPart(BorderPane parent, EventBroker broker) {
		URL location = getClass().getResource("/de/dc/jfx/freesms/app/Adbook.fxml");

		FXMLLoader fxmlLoader = new FXMLLoader(location);
		controller = new AdbookController(broker);
		fxmlLoader.setController(controller);

		GridPane root = null;
		try {
			root = (GridPane) fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		parent.setCenter(root);
	}
	
    @Inject @Optional
    void addContactEvent(@UIEventTopic(IEventTopics.CONTACTFORM_CREATE_CONTACT) Contact contact) {
    	if(contact!=null)
    		controller.addContact(contact);
    }
}
