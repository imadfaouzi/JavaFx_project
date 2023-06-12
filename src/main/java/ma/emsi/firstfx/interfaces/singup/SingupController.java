package ma.emsi.firstfx.interfaces.singup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.emsi.firstfx.interfaces.dashboard.DashboardController;
import ma.emsi.firstfx.logic.service.UserService;

import java.io.IOException;

public class SingupController {
     UserService userService = new UserService();
    @FXML
    private Label alertMessage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean valid = userService.isValidate(username, password);

        if (valid) {
            // Login successful
            alertMessage.setVisible(false);
            // Perform further actions, e.g., navigate to another screen

            // Open the DashboardApplication
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interfaces/dashboard/dashboard-view.fxml"));
                Parent root = fxmlLoader.load();
                DashboardController dashboardController = fxmlLoader.getController();

                // Create a new Stage
                Stage dashboardStage = new Stage();
                dashboardStage.setTitle("Dashboard");
                dashboardStage.setScene(new Scene(root));

                // Close the current window
                closeCurrentWindow();

                // Show the Dashboard window
                dashboardStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Login failed.
            alertMessage.setText("les informations incorrectes !!");
            alertMessage.setVisible(true);
            // Display appropriate error message or perform other actions
        }
    }

    private void closeCurrentWindow() {
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();
    }


}
