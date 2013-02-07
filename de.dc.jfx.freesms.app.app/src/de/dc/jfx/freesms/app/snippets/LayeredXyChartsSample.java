package de.dc.jfx.freesms.app.snippets;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Demonstrates how to draw layers of XYCharts.
 * https://forums.oracle.com/forums/thread.jspa?threadID=2435995
 * "Using StackPane to layer more different type charts"
 */
public class LayeredXyChartsSample extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setScene(new Scene(layerCharts(createBarChart(),
				createLineChart())));
		stage.show();
	}

	private NumberAxis createYaxis() {
		final NumberAxis axis = new NumberAxis(0, 21, 1);
		axis.setPrefWidth(35);
		axis.setMinorTickCount(10);
		axis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axis) {
			@Override
			public String toString(Number object) {
				return String.format("%7.2f", object.floatValue());
			}
		});
		return axis;
	}

	private BarChart<String, Number> createBarChart() {
		final BarChart<String, Number> chart = new BarChart<>(
				new CategoryAxis(), createYaxis());
		setDefaultChartProperties(chart);
		chart.getData().addAll(
				new XYChart.Series(FXCollections.observableArrayList(
						new XYChart.Data("Jan", 2),
						new XYChart.Data("Feb", 10),
						new XYChart.Data("Mar", 8), new XYChart.Data("Apr", 4),
						new XYChart.Data("May", 7), new XYChart.Data("Jun", 5),
						new XYChart.Data("Jul", 4), new XYChart.Data("Aug", 8),
						new XYChart.Data("Sep", 16.5), new XYChart.Data("Oct",
								13.9), new XYChart.Data("Nov", 17),
						new XYChart.Data("Dec", 10))));
		return chart;
	}

	private LineChart<String, Number> createLineChart() {
		final LineChart<String, Number> chart = new LineChart<>(
				new CategoryAxis(), createYaxis());
		setDefaultChartProperties(chart);
		chart.setCreateSymbols(false);
		chart.getData().addAll(
				new XYChart.Series(FXCollections.observableArrayList(
						new XYChart.Data("Jan", 1), new XYChart.Data("Feb", 2),
						new XYChart.Data("Mar", 1.5),
						new XYChart.Data("Apr", 3),
						new XYChart.Data("May", 2.5),
						new XYChart.Data("Jun", 5), new XYChart.Data("Jul", 4),
						new XYChart.Data("Aug", 8),
						new XYChart.Data("Sep", 6.5), new XYChart.Data("Oct",
								13), new XYChart.Data("Nov", 10),
						new XYChart.Data("Dec", 20))));
		return chart;
	}

	private void setDefaultChartProperties(final XYChart<String, Number> chart) {
		chart.setLegendVisible(false);
		chart.setAnimated(false);
	}

	private StackPane layerCharts(final XYChart<String, Number>... charts) {
		for (int i = 1; i < charts.length; i++) {
			configureOverlayChart(charts[i]);
		}

		StackPane stackpane = new StackPane();
		stackpane.getChildren().addAll(charts);

		return stackpane;
	}

	private void configureOverlayChart(final XYChart<String, Number> chart) {
		chart.setAlternativeRowFillVisible(false);
		chart.setAlternativeColumnFillVisible(false);
		chart.setHorizontalGridLinesVisible(false);
		chart.setVerticalGridLinesVisible(false);
		chart.getXAxis().setVisible(false);
		chart.getYAxis().setVisible(false);
		chart.getStylesheets().addAll(
				getClass().getResource("overlay-chart.css").toExternalForm());
	}
}