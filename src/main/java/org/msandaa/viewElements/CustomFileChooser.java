package org.msandaa.viewElements;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class CustomFileChooser {

	static FileChooser filechooser = new FileChooser();

	private CustomFileChooser() {
		filechooser.setTitle("Open Ressource");
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
	}

	public static File showOpenDialog(Window owner) {
		return filechooser.showOpenDialog(owner);
	}

}
