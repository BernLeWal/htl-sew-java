package ui.fx.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML controller class for /ui/fx/LoginDialog.fxml
 */
public class LoginDialogController implements Initializable {

    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onOkClicked(ActionEvent event) {
        String user = username.getText();
        String pwd = password.getText();
        if (user.equals("java") && pwd.equals("class")) {
            Stage stage = (Stage) buttonOK.getScene().getWindow();
            stage.close();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/ui/fx/fxml/HelloWorldDialog.fxml"));

                Scene scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(LoginDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void onCancelClicked(ActionEvent event) {
        Platform.exit();
    }

}
