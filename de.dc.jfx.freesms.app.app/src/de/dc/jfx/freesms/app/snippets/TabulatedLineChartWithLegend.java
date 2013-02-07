package de.dc.jfx.freesms.app.snippets;

import java.util.Random;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

// http://stackoverflow.com/questions/13337062/creating-my-own-connected-chart-table-javafx
public class TabulatedLineChartWithLegend extends Application {
	private final static String MONTHS[] = { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	@Override
	public void start(Stage stage) {
		stage.setTitle("Line Chart With Attached Tabulated Data");

		final CategoryAxis xAxis = new CategoryAxis();
		xAxis.setTickLength(0);
		final NumberAxis yAxis = new NumberAxis();

		final LineChart<String, Number> lineChart = new LineChart(xAxis, yAxis);
		lineChart.setStyle("-fx-padding: 0;");
		lineChart.setLegendVisible(false);
		lineChart.setTitle("Stock Monitoring, 2010");
		lineChart.getData().addAll(createSeries("ACME Explosives"),
				createSeries("DUCK Rubber Ducks"));
		VBox vbox = new VBox(5);
		vbox.getChildren().addAll(lineChart, createTableView(lineChart));
		vbox.setAlignment(Pos.TOP_LEFT);
		vbox.setMinWidth(500);
		VBox.setMargin(lineChart, new Insets(0, 0, 0, 114));
		HBox layout = new HBox();
		layout.getChildren().add(vbox);
		HBox.setHgrow(vbox, Priority.ALWAYS);
		Scene scene = new Scene(layout, 700, 500);
		stage.setScene(scene);
		stage.show();
	}

	private Series createSeries(String name) {
		XYChart.Series series = new XYChart.Series();
		series.setName(name);
		final ObservableList data = series.getData();
		final DataGenerator generator = new DataGenerator();
		for (String month : MONTHS) {
			data.add(generator.createDataItem(month));
		}
		return series;
	}

	private TableView createTableView(final LineChart<String, Number> chart) {
		TableView<XYChart.Series<String, Number>> table = new TableView();
		if (!chart.getData().isEmpty()) {
			TableColumn legendCol = new TableColumn("Legend");
			legendCol.setResizable(false);
			legendCol
					.setCellValueFactory(new Callback<CellDataFeatures<XYChart.Series<String, Number>, String>, ObservableValue<XYChart.Series<String, Number>>>() {
						@Override
						public ObservableValue<Series<String, Number>> call(
								CellDataFeatures<Series<String, Number>, String> param) {
							return new SimpleObjectProperty(param.getValue());
						}
					});
			legendCol
					.setCellFactory(new Callback<TableColumn<XYChart.Series<String, Number>, Series<String, Number>>, TableCell<XYChart.Series<String, Number>, Series<String, Number>>>() {
						@Override
						public TableCell<Series<String, Number>, Series<String, Number>> call(
								TableColumn<Series<String, Number>, Series<String, Number>> param) {
							return new TableCell<Series<String, Number>, Series<String, Number>>() {
								@Override
								protected void updateItem(
										Series<String, Number> series,
										boolean empty) {
									super.updateItem(series, empty);
									if (series != null) {
										setText(series.getName());
										setGraphic(createSymbol(series, chart
												.getData().indexOf(series)));
									}
								}
							};
						}
					});
			table.getColumns().add(legendCol);

			final ObservableList<Data<String, Number>> firstSeriesData = chart
					.getData().get(0).getData();
			for (final Data<String, Number> item : firstSeriesData) {
				TableColumn col = new TableColumn(item.getXValue());
				col.setSortable(false);
				col.setResizable(false);
				col.prefWidthProperty().bind(
						chart.getXAxis().widthProperty()
								.divide(firstSeriesData.size()));
				col.setCellValueFactory(new Callback<CellDataFeatures<XYChart.Series<String, Number>, String>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(
							CellDataFeatures<XYChart.Series<String, Number>, String> param) {
						for (Data<String, Number> curItem : param.getValue()
								.getData()) {
							if (curItem.getXValue().equals(item.getXValue())) {
								return curItem.YValueProperty();
							}
						}
						return null;
					}
				});
				table.getColumns().add(col);
			}
			for (XYChart.Series<String, Number> series : chart.getData()) {
				table.getItems().add(series);
			}

			table.setEditable(false);
			table.setFocusTraversable(false);
		}
		table.setTranslateY(-30);
		table.setPrefHeight(88);
		table.setStyle("-fx-box-border: transparent; -fx-focus-color: transparent; -fx-padding: 0 9 0 9;");
		return table;
	}

	private Node createSymbol(Series<String, Number> series, int seriesIndex) {
		Node symbol = new StackPane();
		symbol.getStyleClass().setAll("chart-line-symbol",
				"series" + seriesIndex, "default-color" + (seriesIndex % 8));
		return symbol;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

// generates random chart data.
class DataGenerator {
	private static final Random random = new Random();
	private int delta = 0;
	private int trend = random.nextInt(4) - 1;

	public Data createDataItem(String month) {
		return new XYChart.Data(month, genDataVal());
	}

	private int genDataVal() {
		delta += trend;
		return random.nextInt(20) + 15 + delta;
	}
}