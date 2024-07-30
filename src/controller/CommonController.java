package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CommonController {

    private Stage primaryStage;

    @FXML
    private ImageView imgBack;

    public CommonController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    protected void loadScene(String controllerName, String fxmlPath, String title) {
        try {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));

            switch(controllerName){
                case "main" : loader.setController(new LoginController(this.primaryStage)); break;
                case "borrow" : loader.setController(new BorrowController(this.primaryStage)); break;
                case "borrow_report": loader.setController(new BorrowReportController(this.primaryStage)); break;
                case "collect" : loader.setController(new CollectController(this.primaryStage)); break;
                case "collect_report": loader.setController(new CollectReportController(this.primaryStage)); break;
                case "book" : loader.setController(new BookController(this.primaryStage)); break;
                case "member": loader.setController(new MemberController(this.primaryStage)); break;
            }
            
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/css/MenuStyle.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.setX(350);
            primaryStage.setY(25);

            if(controllerName == "main"){
                primaryStage.setX(600);
                primaryStage.setY(220);
            }
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnBackOnMouseClicked(MouseEvent event) {
        backToMainScene();
    }

    public void setBackButton(){

        Image imageBack = new Image("/icons/back-icon.png");
        imgBack.setStyle("-fx-background-color: #C0C0C0;  -fx-cursor: hand;");
        imgBack.setPreserveRatio(true);
        imgBack.setFitWidth(45);
        imgBack.setFitHeight(30);
        imgBack.setImage(imageBack);
    }

    protected void backToMainScene() {

        loadScene("borrow","/view/Borrow.fxml", "Library Management System");
    }

    public static void showMessage(String type, String message){

        final Alert alert;

        String title = "";
        if(type == "Error"){
            alert = new Alert(Alert.AlertType.WARNING);
            title = "ERROR";
        }else{
            alert = new Alert(Alert.AlertType.NONE);
            title = "SUCCESS";
        }
        
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeCancel);

        alert.showAndWait();
    }

    protected ButtonController createButton(String type,String idForAction, Consumer<String> action) {

        String image = "";
        if(type == "delete"){
            image = "/icons/delete-icon.png";
        }else if(type == "update"){
            image = "/icons/update-icon.png";
        }
        ButtonController button = new ButtonController(new Image(image), 20, 20);
    
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                action.accept(idForAction);
            }
        });
    
        return button;
    }

    public String getCurrentDate(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime now = LocalDateTime.now();  

        return dtf.format(now); 

    }
    
    public String createNewId(String oldId){

        char letterOfId = oldId.charAt(0);

        String numericId = oldId.substring(1);
        
        return letterOfId + String.format("%03d", (Integer.parseInt(numericId) + 1));
    }

    @FXML
    void menuBookAddOnAction(ActionEvent event) {
        loadScene("book","/view/Book.fxml", "  Library Management System");
    }

    @FXML
    void menuCollectAddOnAction(ActionEvent event) {
        loadScene("collect","/view/Collect.fxml", "  Library Management System");
    }

    @FXML
    void menuCollectReportOnAction(ActionEvent event) {
        loadScene("collect_report","/view/CollectReport.fxml", "  Library Management System");
    }

    @FXML
    void menuLogOutOnAction(ActionEvent event) {
        loadScene("main","/view/Login.fxml", "  Library Management System");
    }

    @FXML
    void menuMemberAddOnAction(ActionEvent event) {
        loadScene("member","/view/Member.fxml", "  Library Management System");
    }

    @FXML
    void menuBorrowAddOnAction(ActionEvent event) {
        loadScene("borrow","/view/Borrow.fxml", "  Library Management System");
    }

    @FXML
    void menuBorrowReportOnAction(ActionEvent event) {
        loadScene("borrow_report","/view/BorrowReport.fxml", "  Library Management System");
    }

}
