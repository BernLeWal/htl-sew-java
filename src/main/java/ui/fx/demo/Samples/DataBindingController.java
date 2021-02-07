package ui.fx.demo.Samples;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.fx.demo.AbstractController;

public class DataBindingController extends AbstractController {
	private StringProperty text = new SimpleStringProperty("Hello World from Controller");

	public final String getText() {
		return text.get();
	}

	public final void setText(String value) {
		text.set(value);
	}

	public final StringProperty textProperty() {
		return text;
	}

	@FXML
	private TextField ctrlTextField;

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		super.initialize(url, resources);
		
		// http://stackoverflow.com/questions/19936719/how-to-do-binding-in-fxml-in-javafx-2
		ctrlTextField.textProperty().bindBidirectional(this.textProperty());
	}
}
