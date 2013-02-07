package de.dc.jfx.freesms.app.part;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.e4.ui.services.internal.events.EventBroker;

import de.dc.jfx.freesms.app.ContactFormularController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ContactPart {

	@Inject
	public ContactPart(BorderPane parent, EventBroker broker) {
		URL location = getClass().getResource("/de/dc/jfx/freesms/app/ContactFormular.fxml");

		FXMLLoader fxmlLoader = new FXMLLoader(location);
		ContactFormularController controller = new ContactFormularController(broker);
		fxmlLoader.setController(controller);

		GridPane root = null;
		try {
			root = (GridPane) fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		parent.setCenter(root);
	}
}
