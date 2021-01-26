package ui.fx.fxml;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import server.rest.MapQuestDirections;
import server.rest.RESTException;
import utils.StringUtils;

/**
 * The RoutePlannerMainWindowController implements the controler for the FXML-File RoutePlannerMainWindow.fxml.
 * It shows how to do layouting with VBox, HBox and SplitPane.
 * Also it demonstrates how to do data-binding to automaticaly enable/disable buttons in regard to textfield values.
 */
public class RoutePlannerMainWindowController {
    public TextField textFrom;
    public TextField textTo;
    public ListView listView;
    public ImageView imageView;
    public Label labelStatus;
    public Button buttonClear;
    public Button buttonRun;

    private final StringProperty fromProperty = new SimpleStringProperty();
    private final StringProperty toProperty = new SimpleStringProperty();

    @FXML
    public void initialize() {
        //
        // JavaFX Data Binding
        //

        // 1st: define the data sources and bind them to a property
        // (on the properties the automatic binding mechanisms are implemented)
        fromProperty.bind( textFrom.textProperty() );
        toProperty.bind( textTo.textProperty() );

        // 2st: define how the binding-values are calculated
        BooleanBinding isClearButtonDisabled = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return StringUtils.isNullOrEmpty(fromProperty.get()) && StringUtils.isNullOrEmpty(toProperty.get());
            }
        };

        BooleanBinding isRunButtonDisabled = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return StringUtils.isNullOrEmpty(fromProperty.get()) || StringUtils.isNullOrEmpty(toProperty.get());
            }
        };

        // 3rd: add the change listeners to the properties
        fromProperty.addListener((o, oldVal, newVal) -> isClearButtonDisabled.invalidate());
        toProperty.addListener((o, oldVal, newVal) -> isRunButtonDisabled.invalidate());

        // 4th: bind the targets
        buttonClear.disableProperty().bind( isClearButtonDisabled );
        buttonRun.disableProperty().bind( isRunButtonDisabled );


        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }

    // JavaFX Event Handlers
    public void onButtonClearClicked(ActionEvent actionEvent) {
        textFrom.setText("");
        textTo.setText("");
        listView.getItems().clear();

        textFrom.requestFocus();
    }

    public void onButtonRunClicked(ActionEvent actionEvent) {
        if( StringUtils.isNullOrEmpty( MapQuestDirections.MAPQUEST_API_KEY) ) {
            TextInputDialog td = new TextInputDialog( MapQuestDirections.MAPQUEST_API_KEY);
            td.setHeaderText("""
                    Für die Benutzung des MapQuest WebServices wird ein KEY benötigt!
                    Man kann diesen kostenlos auf der Seite https://developer.mapquest.com/ erhalten.
                    (Um nur die JavaFX-GUI ohne Routenplanung auszuprobieren einfach leer lassen). 
                     
                    Den Authentication-Key hier eingeben:
                    """);
            MapQuestDirections.MAPQUEST_API_KEY = td.showAndWait().orElse("");
        }

        MapQuestDirections route = new MapQuestDirections();

        try {
            route.query(textFrom.getText(), textTo.getText());

            // output the results
            String status;
            if( route.getStatuscode()!=0 )
                status = String.format("API status: %d = %s", route.getStatuscode(), route.getErrorMessage());
            else
                status = String.format("Reisedauer: %s, Distanz: %.2f km", route.getFormattedTime(), route.getDistance());
            System.out.println(status);
            labelStatus.setText( status);

            System.out.println("============================");
            for( MapQuestDirections.Maneuver m : route.getManeuvers() ) {
                listView.getItems().add( String.format("%s (%.2f km)", m.narrative, m.distance) );
            }
        } catch (RESTException e) {
            e.printStackTrace();
        }
    }
}
