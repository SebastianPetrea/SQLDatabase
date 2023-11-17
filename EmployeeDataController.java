package employees.data;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeDataController {

    private Stage stage;
    @FXML private TableView<Users> liste;
    @FXML private TableColumn<Users, Integer> idColumn;
    @FXML private TableColumn<Users, String> usernameColumn, emailColumn;
    @FXML private TextField idField, usernameField, emailField;
    
    

    @FXML
    private void addUsers() {
        try {
            String id = idField.getText();
            // Check if the id is a valid integer
            if (!id.matches("\\d+")) {
                showAlert("Invalid ID", "Please enter a numeric value for ID.");
                return;
            }
            String username = usernameField.getText();
            String email = emailField.getText();

            System.out.println("ID: " + id);
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);

            // Add the new user
            Users newUser = new Users(Integer.parseInt(id), username, email);
            mysqlConnect.addUsers(id, username, email);
            liste.getItems().add(newUser); // Add the new user to the TableView
            idField.clear();
            usernameField.clear();
            emailField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void showList() {
        try {
            // Clear existing items in the TableView
            liste.getItems().clear();

            // Fetch the list of users from the database
            List<Users> userList = mysqlConnect.fetchUsersList();

            // Populate the TableView with the fetched user data
            liste.getItems().addAll(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
