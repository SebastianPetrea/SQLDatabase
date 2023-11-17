package employees.data;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeesDataMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("MySQL App");

        try {
        	mysqlConnect.connectToDatabase();


            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeData.fxml"));
            Parent root = loader.load();

            // Get the controller instance and set it as the controller for the FXML
            EmployeeDataController controller = loader.getController();
            controller.setStage(primaryStage);

            primaryStage.setScene(new Scene(root, 500, 300));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
