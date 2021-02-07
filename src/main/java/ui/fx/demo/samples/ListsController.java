package ui.fx.demo.samples;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import ui.fx.demo.AbstractController;
import ui.fx.demo.presentationModels.ListDemoModel;
import ui.fx.demo.presentationModels.ListsModel;

import java.net.URL;
import java.util.ResourceBundle;

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
        list.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
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
