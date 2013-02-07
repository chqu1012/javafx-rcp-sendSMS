package de.dc.jfx.freesms.app.snippets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
/** Demonstrates drawing a clock in JavaFX */
public class ClockDemo extends Application {
final String BRAND_NAME = "Splotch";
final double CLOCK_RADIUS = 100;
 
public static void main(String[] args) throws Exception {
launch(args);
}
 
public void start(final Stage stage) throws Exception {
// create the scene elements.
final Pane backdrop = makeBackdrop("backdrop", stage);
 
// layout the scene.
final Scene scene = createClockScene(
StackPaneBuilder.create()
.id("layout")
.children(
backdrop,
VBoxBuilder.create()
.id("clocks")
.pickOnBounds(false)
.children(
makeAnalogueClock(stage),
new DigitalClock())
.alignment(Pos.CENTER)
.build()
)
.build()
);
 
// size the backdrop to the scene.
sizeToScene(backdrop, scene);
 
// show the scene.
stage.initStyle(StageStyle.TRANSPARENT);
stage.setScene(scene);
stage.show();
}
 
private AnalogueClock makeAnalogueClock(Stage stage) {
final AnalogueClock analogueClock = new AnalogueClock(BRAND_NAME, CLOCK_RADIUS);
EffectUtilities.addGlowOnHover(analogueClock);
EffectUtilities.fadeOnClick(analogueClock, closeStageEventHandler(stage));
return analogueClock;
}
 
private void sizeToScene(Pane pane, Scene scene) {
pane.prefWidthProperty().bind(scene.widthProperty());
pane.prefHeightProperty().bind(scene.heightProperty());
}
 
private Scene createClockScene(Parent layout) {
final Scene scene = new Scene(layout, Color.TRANSPARENT);
scene.getStylesheets().add(
ResourceResolver.getResourceFor(
ClockDemo.class,
"clock-demo.css"
)
);
return scene;
}
 
private EventHandler<ActionEvent> closeStageEventHandler(final Stage stage) {
return new EventHandler<ActionEvent>() {
@Override public void handle(ActionEvent actionEvent) {
stage.close();
}
};
}
 
private Pane makeBackdrop(String id, Stage stage) {
Pane backdrop = new Pane();
backdrop.setId(id);
EffectUtilities.makeDraggable(stage, backdrop);
return backdrop;
}
}