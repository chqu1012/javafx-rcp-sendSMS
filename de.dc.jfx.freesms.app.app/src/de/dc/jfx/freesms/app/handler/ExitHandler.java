 
package de.dc.jfx.freesms.app.handler;

import org.eclipse.e4.core.di.annotations.Execute;

public class ExitHandler {
	@Execute
	public void execute() {
		System.exit(0);
	}
		
}