import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Login.fxml"));

            loader.setController(new LoginController(primaryStage));

            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Library Management System");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
