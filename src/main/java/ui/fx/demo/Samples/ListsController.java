package ui.fx.demo.Samples;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import ui.fx.demo.AbstractController;
import ui.fx.demo.PresentationModels.ListDemoModel;
import ui.fx.demo.PresentationModels.ListsModel;

public class ListsController extends AbstractController {
	private ListsModel model;
	@FXML
	ListView<ListDemoModel> list;
	@FXML
	TableView<ListDemoModel> table;

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		super.initialize(url, resources);

		model = new ListsModel();
		
		list.setItems(model.getPersons());
		list.setCellFactory(TextFieldListCell.forListView(new StringConverter<ListDemoModel>() {
			@Override
			public String toString(ListDemoModel object) {
				return String.format("%s (%.1f)", object.getName(), object.getAge());
			}

			@Override
			public ListDemoModel fromString(String string) {
				return null;
			}
		}));
		
		table.setItems(model.getPersons());
	}
}
