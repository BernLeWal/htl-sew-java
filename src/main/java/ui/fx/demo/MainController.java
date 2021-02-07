package ui.fx.demo;

import java.io.IOException;

import javafx.fxml.FXML;
import ui.fx.demo.models.Person;

public class MainController extends AbstractController {

	@FXML
	public void onLayouts() throws IOException {
		showDialog("Layouts.fxml", "Layouts");
	}

	@FXML
	public void onControls() throws IOException {
		showDialog("Controls.fxml", "Controls");
	}

	@FXML
	public void onDataBinding() throws IOException {
		showDialog("DataBinding.fxml", "DataBinding");
	}

	@FXML
	public void onPresentationModel() throws IOException {
		Person model = getPersonModelFromBusinessLayer();
		showDialog("PresentationModel.fxml", model, "PresentationModel");
	}

	private Person getPersonModelFromBusinessLayer() {
		Person result = new Person();
		result.setVorname("Peter");
		result.setNachname("Mayer");
		return result;
	}

	@FXML
	public void onLowLevelBinding() throws IOException {
		showDialog("LowLevelBinding.fxml", "Low-Level binding");
	}

	@FXML
	public void onEventHandling() throws IOException {
		showDialog("EventHandling.fxml", "EventHandling");
	}

	@FXML
	public void onLists() throws IOException {
		showDialog("Lists.fxml", "Lists");
	}

	@FXML public void onCSS() throws IOException {
		showDialog("CSS.fxml", "CSS");
	}
}
