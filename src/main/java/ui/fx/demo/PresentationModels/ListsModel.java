package ui.fx.demo.PresentationModels;

import java.util.Arrays;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListsModel {

	public ListsModel() {

	}

	private Collection<ListDemoModel> persons = Arrays
			.asList(new ListDemoModel("Peter", 35), new ListDemoModel(
					"Max", 0.75f), new ListDemoModel("Isabella", 32));	

	public ObservableList<ListDemoModel> getPersons() {
		return FXCollections.observableArrayList(persons);
	}	
}
