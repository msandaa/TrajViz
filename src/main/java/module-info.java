module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	requires javafx.base;
    
    opens org.msandaa to javafx.fxml;
    exports org.msandaa;
    exports org.msandaa.util;
}
