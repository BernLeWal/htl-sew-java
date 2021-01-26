package ui.fx.fxml;

import java.io.IOException;
import java.net.URL;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
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
    public Button buttonClear;
    public Button buttonRun;
    public ListView<Pair<String, String>> listView;
    public ScrollPane imageViewPane;
    public ImageView imageView;
    public Label labelStatus;

    public static final String DEFAULT_TEXT = "Start/Ziel eingeben und [Berechnen] klicken.";
    private Image defaultImage;

    @FXML
    public void initialize() {
        //
        // JavaFX Data Binding
        //

        // 1st: define the data sources and bind them to a property
        // (on the properties the automatic binding mechanisms are implemented)

        // 2st: define how the binding-values are calculated
        BooleanBinding isClearButtonDisabled = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return StringUtils.isNullOrEmpty(textFrom.textProperty().get()) && StringUtils.isNullOrEmpty(textTo.textProperty().get());
            }
        };

        BooleanBinding isRunButtonDisabled = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return StringUtils.isNullOrEmpty(textFrom.textProperty().get()) || StringUtils.isNullOrEmpty(textTo.textProperty().get());
            }
        };

        // 3rd: add the change listeners to the properties
        textFrom.textProperty().addListener((o, oldVal, newVal) -> isClearButtonDisabled.invalidate());
        textTo.textProperty().addListener((o, oldVal, newVal) -> isRunButtonDisabled.invalidate());

        // 4th: bind the targets
        buttonClear.disableProperty().bind( isClearButtonDisabled );
        buttonRun.disableProperty().bind( isRunButtonDisabled );

        //
        // ListView and ImageView
        //
        defaultImage = new Image(RoutePlanner.class.getResourceAsStream("/ui/fx/placeholder.jpg"));

        // listen to selection changes
        listView.getItems().add( new Pair<>(DEFAULT_TEXT,""));
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            Image image = null;
            if( newValue!=null ) {
                System.out.println("ListView - Item changed to: " + newValue);
                try {
                    image = new Image(new URL(newValue.getValue()).openConnection().getInputStream());
                } catch (IOException e) {
                    System.err.println("WARNING: Failed to load image from " + newValue.getValue());
                }
            }
            if( image==null ) {
                System.out.println("ListView - No Item selected.");
            }
            imageView.imageProperty().set( (image!=null) ? image : defaultImage );
        });

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.fitWidthProperty().bind(imageViewPane.widthProperty());
        imageView.fitHeightProperty().bind(imageViewPane.heightProperty());

        imageView.imageProperty().set( defaultImage );
    }

    // JavaFX Event Handlers
    public void onButtonClearClicked(ActionEvent actionEvent) {
        textFrom.setText("");
        textTo.setText("");

        listView.getItems().clear();
        listView.getItems().add( new Pair<>(DEFAULT_TEXT,""));

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

        listView.getItems().clear();
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
                listView.getItems().add( new Pair<>(String.format("%s (%.2f km)", m.narrative, m.distance),m.mapUrl) );
            }
        } catch (RESTException e) {
            listView.getItems().add( new Pair<>(DEFAULT_TEXT,""));
            labelStatus.setText( e.getLocalizedMessage() );
        }
    }
}
