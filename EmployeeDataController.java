package employees.data;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeDataController {

    private Stage stage;
    @FXML private TableView<Users> liste;
    @FXML private TableColumn<Users, Integer> idColumn;
    @FXML private TableColumn<Users, String> usernameColumn, emailColumn;
    @FXML private TextField idField, usernameField, emailField,deleteField;
    private ObservableList<Users> userList = FXCollections.observableArrayList();
    public void initialize() {
        // Set the ObservableList as the data model for the TableView
        liste.setItems(userList);

        // Set up cell value factories for each column
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
    }


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

            // Add the new user
            Users newUser = new Users(Integer.parseInt(id), username, email);
            mysqlConnect.addUsers(id, username, email);
            liste.getItems().add(newUser); // Add the new user to the TableView
            idField.clear();
            usernameField.clear();
            emailField.clear();
            showList();
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
    @FXML
    private void deleteRow() throws SQLException{
    	try {
    		String input = deleteField.getText();
    		if (input.contains(input)) {
                showAlert("Invalid ID", "ID is not in the list");
                return;
            }
    		
    		mysqlConnect.deleteRow(input);
    		showList();
    		deleteField.clear();
 
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    }
    @FXML
    private void updateList() {
    	
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
