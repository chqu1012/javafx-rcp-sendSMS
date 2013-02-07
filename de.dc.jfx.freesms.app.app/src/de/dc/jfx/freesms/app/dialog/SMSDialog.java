package de.dc.jfx.freesms.app.dialog;

import java.io.IOException;
import java.net.URL;

import de.dc.jfx.freesms.app.SMSDialogController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SMSDialog extends Stage {

	private SMSDialogController controller;

	public void open(){
		URL location = getClass().getResource("/de/dc/jfx/freesms/app/SMSDialog.fxml");

		FXMLLoader fxmlLoader = new FXMLLoader(location);
		controller = new SMSDialogController();
		fxmlLoader.setController(controller);

		GridPane root = null;
		try {
			root = (GridPane) fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 Scene page2 = new Scene(root);
         setScene(page2);
         show();
	}
	
	public void setReceiver(String receiver, String receiverName){
		controller.setReceiver(receiver, receiverName);
	}
}
