package ma.emsi.firstfx.interfaces.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.emsi.firstfx.logic.entities.Film;
import ma.emsi.firstfx.logic.service.FilmService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class DashboardController {
    FilmService filmService = new FilmService();
    @FXML
    private TextField titleField;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField idField;
    @FXML
    private TextField categoryField;

    @FXML
    private TextField minAgeField;

    @FXML
    private TextField maxAgeField;

    @FXML
    private DatePicker  registrationDateField;

    @FXML
    private TableView<Film> filmTable;

    public void initialize() {
        initializeFilmTable();
    }

    private void initializeFilmTable() {
        List<Film> films = filmService.findAll();

        // Populate the table view
        filmTable.getItems().addAll(films);
    }

    @FXML
    private void handleValidateButton() {
        String title = titleField.getText();
        String category = categoryField.getText();
        int minAge = Integer.parseInt(minAgeField.getText());
        int maxAge = Integer.parseInt(maxAgeField.getText());
        LocalDate registrationDate = registrationDateField.getValue();
        Date registrationDate_ = Date.from(registrationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());



        // Create a new film object with the entered data
        Film film = new Film(null, title, category, minAge, maxAge, registrationDate_);

        // Use the FilmService to save the film
        FilmService filmService = new FilmService();
        Film insertedFilm = filmService.save(film);

        if (insertedFilm != null) {
            // Film was inserted successfully
            // Add the film to the TableView
            filmTable.getItems().add(insertedFilm);
        } else {
            // Film insertion failed
            System.out.println("Film not saved.");
        }

        // Clear the fields
        titleField.clear();
        categoryField.clear();
        minAgeField.clear();
        maxAgeField.clear();
        registrationDateField.setValue(null);
    }

    @FXML
    private void fetchFilmdata() {
        // Get the selected film from the TableView
        Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();

        if (selectedFilm != null) {
            // Display the selected film's information in the form fields
            idField.setText(String.valueOf(selectedFilm.getId()));
            titleField.setText(selectedFilm.getTitre());
            categoryField.setText(selectedFilm.getCategory());
            minAgeField.setText(String.valueOf(selectedFilm.getMin_age()));
            maxAgeField.setText(String.valueOf(selectedFilm.getMax_age()));

            LocalDate registrationDate = selectedFilm.getRegistrationDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            registrationDateField.setValue(registrationDate);

        } else {
            // No film selected, clear the form fields
            clearFields();
        }
    }

    @FXML
    private void handleUpdate() {
        // Retrieve the values from the form fields
        int id = Integer.parseInt(idField.getText());
        String title = titleField.getText();
        String category = categoryField.getText();
        int minAge = Integer.parseInt(minAgeField.getText());
        int maxAge = Integer.parseInt(maxAgeField.getText());
        LocalDate registrationDate = registrationDateField.getValue();
        Date registrationDate_ = Date.from(registrationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Create a new film object with the updated data
        Film updatedFilm = new Film(id, title, category, minAge, maxAge, registrationDate_);

        // Use the FilmService to update the film in the database
        FilmService filmService = new FilmService();
        filmService.update(updatedFilm);

        // Update the film in the TableView
        int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
        filmTable.getItems().set(selectedIndex, updatedFilm);

        // Clear the form fields
        clearFields();
    }
    @FXML
    private void handleDelete(){
        // Retrieve the ID of the film to delete
        int id = Integer.parseInt(idField.getText());
        Film film = new Film(id, null, null, 0, 0, null);
        // Use the FilmService to delete the film from the database
        FilmService filmService = new FilmService();
        filmService.remove(film);

        // Remove the film from the TableView
        int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
        filmTable.getItems().remove(selectedIndex);

        // Clear the form fields
        clearFields();
    }
    @FXML
    private void handleLogoutButton() {
        // Get the current stage
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();

        try {
            // Load the login window FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/emsi/firstfx/hello-view.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login window
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root) );

            // Close the current window
            currentStage.close();

            // Show the login window
            loginStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void  exprotToexcel(){
        filmService.exporterVersExcel();
    }

    private void clearFields() {
        titleField.clear();
        idField.clear();
        categoryField.clear();
        minAgeField.clear();
        maxAgeField.clear();
        registrationDateField.setValue(null);
    }

}
