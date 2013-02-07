package de.dc.jfx.freesms.app.snippets;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.logging.*;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
// click in the value column (a couple of times) to edit the value in the column.
// property editors are defined only for String and Boolean properties.
// change focus to something else to commit the edit.

public class TableViewPropertyEditor extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		final Person aPerson = new Person("Fred", true);
		final Label currentObjectValue = new Label(aPerson.toString());
		TableView<NamedProperty> table = new TableView();
		table.setEditable(true);
		table.setItems(createNamedProperties(aPerson));
		TableColumn<NamedProperty, String> nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<NamedProperty, String>(
				"name"));
		TableColumn<NamedProperty, Object> valueCol = new TableColumn("Value");
		valueCol.setCellValueFactory(new PropertyValueFactory<NamedProperty, Object>(
				"value"));
		valueCol.setCellFactory(new Callback<TableColumn<NamedProperty, Object>, TableCell<NamedProperty, Object>>() {
			@Override
			public TableCell<NamedProperty, Object> call(
					TableColumn<NamedProperty, Object> param) {
				return new EditingCell();
			}
		});
		valueCol.setOnEditCommit(new EventHandler<CellEditEvent<NamedProperty, Object>>() {
			@Override
			public void handle(CellEditEvent<NamedProperty, Object> t) {
				int row = t.getTablePosition().getRow();
				NamedProperty property = (NamedProperty) t.getTableView()
						.getItems().get(row);
				property.setValue(t.getNewValue());
				currentObjectValue.setText(aPerson.toString());
			}
		});
		table.getColumns().setAll(nameCol, valueCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox layout = new VBox(10);
		layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
		layout.getChildren().setAll(currentObjectValue, table);
		VBox.setVgrow(table, Priority.ALWAYS);
		stage.setScene(new Scene(layout, 650, 600));
		stage.show();
	}

	private ObservableList<NamedProperty> createNamedProperties(Object object) {
		ObservableList<NamedProperty> properties = FXCollections
				.observableArrayList();
		for (Method method : object.getClass().getMethods()) {
			String name = method.getName();
			Class type = method.getReturnType();
			if (type.getName().endsWith("Property")) {
				try {
					properties.add(new NamedProperty(name, (Property) method
							.invoke(object)));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
					Logger.getLogger(TableViewPropertyEditor.class.getName())
							.log(Level.SEVERE, null, ex);
				}
			}
		}
		return properties;
	}

	public class NamedProperty {
		public NamedProperty(String name, Property value) {
			nameProperty.set(name);
			valueProperty = value;
		}

		private StringProperty nameProperty = new SimpleStringProperty();

		public StringProperty nameProperty() {
			return nameProperty;
		}

		public StringProperty getName() {
			return nameProperty;
		}

		public void setName(String name) {
			nameProperty.set(name);
		}

		private Property valueProperty;

		public Property valueProperty() {
			return valueProperty;
		}

		public Object getValue() {
			return valueProperty.getValue();
		}

		public void setValue(Object value) {
			valueProperty.setValue(value);
		}
	}

	public class Person {
		private final SimpleStringProperty firstName;
		private final SimpleBooleanProperty married;

		private Person(String firstName, Boolean isMarried) {
			this.firstName = new SimpleStringProperty(firstName);
			this.married = new SimpleBooleanProperty(isMarried);
		}

		public SimpleStringProperty firstNameProperty() {
			return firstName;
		}

		public SimpleBooleanProperty marriedProperty() {
			return married;
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public Boolean getMarried() {
			return married.get();
		}

		public void setMarried(Boolean isMarried) {
			married.set(isMarried);
		}

		@Override
		public String toString() {
			return firstName.getValue() + ": " + married.getValue();
		}
	}

	class EditingCell extends TableCell<NamedProperty, Object> {
		private TextField textField;
		private CheckBox checkBox;

		public EditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				if (getItem() instanceof Boolean) {
					createCheckBox();
					setText(null);
					setGraphic(checkBox);
				} else {
					createTextField();
					setText(null);
					setGraphic(textField);
					textField.selectAll();
				}
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			if (getItem() instanceof Boolean) {
				setText(getItem().toString());
			} else {
				setText((String) getItem());
			}
			setGraphic(null);
		}

		@Override
		public void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (getItem() instanceof Boolean) {
						if (checkBox != null) {
							checkBox.setSelected(getBoolean());
						}
						setText(null);
						setGraphic(checkBox);
					} else {
						if (textField != null) {
							textField.setText(getString());
						}
						setText(null);
						setGraphic(textField);
					}
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()
					* 2);
			textField.focusedProperty().addListener(
					new ChangeListener<Boolean>() {
						@Override
						public void changed(
								ObservableValue<? extends Boolean> observable,
								Boolean oldValue, Boolean newValue) {
							if (!newValue) {
								commitEdit(textField.getText());
							}
						}
					});
		}

		private void createCheckBox() {
			checkBox = new CheckBox();
			checkBox.setSelected(getBoolean());
			checkBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			checkBox.focusedProperty().addListener(
					new ChangeListener<Boolean>() {
						@Override
						public void changed(
								ObservableValue<? extends Boolean> observable,
								Boolean oldValue, Boolean newValue) {
							if (!newValue) {
								commitEdit(checkBox.isSelected());
							}
						}
					});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}

		private Boolean getBoolean() {
			return getItem() == null ? false : (Boolean) getItem();
		}
	}
}