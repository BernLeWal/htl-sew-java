package ui.fx.fxml;

import javafx.beans.binding.Bindings;
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

public class RoutePlannerMainWindowController {
    public TextField textFrom;
    public TextField textTo;
    public ListView listView;
    public ImageView imageView;
    public Label labelStatus;
    public Button buttonClear;
    public Button buttonRun;

    private RouteFromToPresentationModel routeFromToPresentationModel;

    @FXML
    public void initialize() {
        routeFromToPresentationModel = new RouteFromToPresentationModel();
        routeFromToPresentationModel.fromProperty.bind( textFrom.textProperty() );
        routeFromToPresentationModel.toProperty.bind( textTo.textProperty() );
        buttonClear.disableProperty().bind( routeFromToPresentationModel.isClearDisabled );
        buttonRun.disableProperty().bind(routeFromToPresentationModel.isRunDisabled );

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

    //
    // JavaFX Data Binding
    //
    public class RouteFromToPresentationModel {
        private final StringProperty fromProperty = new SimpleStringProperty();
        private final StringProperty toProperty = new SimpleStringProperty();

        public RouteFromToPresentationModel() {
            fromProperty.addListener((o, oldVal, newVal) -> isClearDisabled.invalidate());
            toProperty.addListener((o, oldVal, newVal) -> isRunDisabled.invalidate());
        }

        public String getFromText() {
            return fromProperty.get();
        }

        public void setFromText(String fromText) {
            this.fromProperty.set(fromText);
        }

        public String getToText() {
            return toProperty.get();
        }

        public void setToText(String toText) {
            this.toProperty.set(toText);
        }

        public BooleanBinding isClearDisabled = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return StringUtils.isNullOrEmpty(getFromText()) && StringUtils.isNullOrEmpty(getToText());
            }
        };

        public BooleanBinding isRunDisabled = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return StringUtils.isNullOrEmpty(getFromText()) || StringUtils.isNullOrEmpty(getToText());
            }
        };
    }
}
