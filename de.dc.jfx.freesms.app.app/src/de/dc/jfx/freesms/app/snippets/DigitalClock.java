package de.dc.jfx.freesms.app.snippets;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Calendar;

/**
 * Creates a digital clock display as a simple label. Format of the clock
 * display is hh:mm:ss aa, where: hh Hour in am/pm (1-12) mm Minute in hour ss
 * Second in minute aa Am/pm marker Time is the system time for the local
 * timezone. see: digital-clock.css for css formatting rules for the clock.
 */
public class DigitalClock extends Label {
	public DigitalClock() {
		setId("digitalClock");

		getStylesheets().add(
				ResourceResolver
						.getResourceFor(getClass(), "digital-clock.css"));

		bindToTime();
	}

	// the digital clock updates once a second.
	private void bindToTime() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						Calendar time = Calendar.getInstance();
						String hourString = StringUtilities.pad(
								2,
								' ',
								time.get(Calendar.HOUR) == 0 ? "12" : time
										.get(Calendar.HOUR) + "");
						String minuteString = StringUtilities.pad(2, '0',
								time.get(Calendar.MINUTE) + "");
						String secondString = StringUtilities.pad(2, '0',
								time.get(Calendar.SECOND) + "");
						String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM"
								: "PM";
						setText(hourString + ":" + minuteString + ":"
								+ secondString + " " + ampmString);
					}
				}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
}